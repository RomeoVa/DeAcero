package mx.itesm.csf.deacero;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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

public class NuevoUsuario extends AppCompatActivity {

    private EditText nombreText;
    private EditText appaternoText;
    private EditText amaternaText;
    private EditText usuarioText;
    private EditText passwordText;
    private CheckBox privilegios;

    public static String SERVICIO_Create;

    public static final String NOMBRE = "nombre";
    public static final String APPATERNO = "appaterno";
    public static final String APMATERNO = "apmaterno";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";
    public static final String ROL = "rol";

    private String nombre;
    private String appaterno;
    private String apmaterno;
    private String usuario2;
    private String password;
    private String privilegiosText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);
        setTitle("Usuario");
        nombreText = (EditText) findViewById(R.id.NombreText);
        appaternoText = (EditText) findViewById(R.id.AppaternoText);
        amaternaText = (EditText) findViewById(R.id.ApmaternoText);
        usuarioText = (EditText) findViewById(R.id.UsuarioText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        privilegios = (CheckBox) findViewById(R.id.privilegiosBox);
    }

    public void crearUsuario(View v){


        if(TextUtils.isEmpty(nombreText.getText().toString()) ||
                TextUtils.isEmpty(appaternoText.getText().toString()) ||
                TextUtils.isEmpty(amaternaText.getText().toString()) ||
                TextUtils.isEmpty(usuarioText.getText().toString()) ||
                TextUtils.isEmpty(passwordText.getText().toString()))
        {
            Toast.makeText(this, "Favor de llenar todos los campos ", Toast.LENGTH_SHORT).show();

        }else {

            final ProgressDialog barraDeProgreso = new ProgressDialog(NuevoUsuario.this);
            barraDeProgreso.setMessage("Añadiendo...");
            barraDeProgreso.show();

            SERVICIO_Create = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.crear.usuario.php?";

            nombre = nombreText.getText().toString().trim();
            appaterno = appaternoText.getText().toString().trim();
            apmaterno = amaternaText.getText().toString().trim();
            usuario2 = usuarioText.getText().toString().trim();
            password = passwordText.getText().toString().trim();

            if (privilegios.isChecked()) {
                privilegiosText = "Administrador";
            } else privilegiosText = "Empleado";


            SERVICIO_Create = SERVICIO_Create +

                    NOMBRE + "=" + nombre + "&" +
                    APPATERNO + "=" + appaterno + "&" +
                    APMATERNO + "=" + apmaterno + "&" +
                    USUARIO + "=" + usuario2 + "&" +
                    PASSWORD + "=" + password + "&" +
                    ROL + "=" + privilegiosText.trim();

            JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_Create, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    barraDeProgreso.hide();
                    try {
                        JSONObject autenticacion = (JSONObject) response.get(0);
                        String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                        //Log.d(TAG,response.toString());

                        if (codigo_autenticacion.equals("01")) {

                            Toast.makeText(NuevoUsuario.this,
                                    "Usuario añadido", Toast.LENGTH_LONG).show();

                        }else if(codigo_autenticacion.equals("08")){
                            Toast.makeText(NuevoUsuario.this,
                                    "Ya existe este usuario", Toast.LENGTH_LONG).show();

                        }
                    } catch (JSONException e) {
                        Toast.makeText(NuevoUsuario.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    barraDeProgreso.hide();
                    Toast.makeText(NuevoUsuario.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(NOMBRE, nombre);
                    map.put(APPATERNO, appaterno);
                    map.put(APMATERNO, apmaterno);
                    map.put(USUARIO, usuario2);
                    map.put(PASSWORD, password);
                    map.put(ROL, privilegiosText.trim());
                    return map;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    String credentiales = usuario2 + ":" + password;
                    String autenticacion = "Basic " + Base64.encodeToString(credentiales.getBytes(), Base64.NO_WRAP);
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", autenticacion);
                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(peticion);
        }


    }
}
