package mx.itesm.csf.deacero;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    public static String SERVICIO_LOGIN;

    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";

    private final String TAG = "Datos";
    private EditText editTextUsuario;
    private EditText editTextPassword;

    private Button botonAcceder;

    private Usuario datosUsuario;

    private String usuario;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsuario = (EditText) findViewById(R.id.userText);
        editTextPassword = (EditText) findViewById(R.id.passwordText);

        botonAcceder = (Button) findViewById(R.id.boton_login);
        botonAcceder.setOnClickListener((View.OnClickListener) this);
    }

    private void usuarioLogin() {
        final ProgressDialog barraDeProgreso = new ProgressDialog(Login.this);
        barraDeProgreso.setMessage("Iniciando sesion...");
        barraDeProgreso.show();

        SERVICIO_LOGIN = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.login.php?";
        usuario = editTextUsuario.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        SERVICIO_LOGIN = SERVICIO_LOGIN + USUARIO + "=" + usuario + "&" + PASSWORD + "=" + password;
        Log.d(TAG,SERVICIO_LOGIN.toString());

        JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_LOGIN, new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                barraDeProgreso.hide();
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    //Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {
                        JSONObject username = (JSONObject) response.get(2);

                        datosUsuario.getInstance().setNombre(username.getString("Nombre").toString());
                        datosUsuario.getInstance().setAppaterno(username.getString("Appaterno").toString());
                        datosUsuario.getInstance().setApmaterno(username.getString("Apmaterno").toString());
                        datosUsuario.getInstance().setusuario(usuario);
                        datosUsuario.getInstance().setPassword(password);

                        Toast.makeText(Login.this,
                                "Bienvenido " + username.getString("Nombre").toString(), Toast.LENGTH_LONG).show();
                        Log.d(TAG,response.toString());
                        Intent intent = new Intent(getBaseContext(), Menu_Graficas.class);
                        startActivity(intent);
                    } else if (codigo_autenticacion.equals("04")) {
                        Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_LONG).show();
                        Log.d(TAG,"Usuario o password incorrecto");
                    }
                } catch (JSONException e) {
                    Toast.makeText(Login.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                barraDeProgreso.hide();
                Toast.makeText(Login.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(USUARIO, usuario);
                map.put(PASSWORD, password);
                return map;
            }

            @Override public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentiales = usuario + ":" + password;
                String autenticacion = "Basic " + Base64.encodeToString(credentiales.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", autenticacion);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(peticion);
    }

    public void onClick(View v) {
        usuarioLogin();
    }

}
