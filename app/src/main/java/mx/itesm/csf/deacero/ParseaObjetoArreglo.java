package mx.itesm.csf.deacero;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class ParseaObjetoArreglo extends AppCompatActivity {

    private static final String TAG = "ParseaObjetoJSON";

    private Usuario usuario;
    private EditText nombreText;
    private EditText appaternoText;
    private EditText amaternaText;
    private EditText usuarioText;
    private EditText passwordText;
    private CheckBox privilegios;


    private final String OBJETO_JSON_EXTRA = "objetoUsuario";

    public static String SERVICIO_Update;
    public static String SERVICIO_Borrar;

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String APPATERNO = "appaterno";
    public static final String APMATERNO = "apmaterno";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";
    public static final String ROL = "rol";

    private String id;
    private String nombre;
    private String appaterno;
    private String apmaterno;
    private String usuario2;
    private String password;
    private String privilegiosText;
    private final String priv = "Administrador";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsea_objeto_arreglo);

        usuario = (Usuario) getIntent().getSerializableExtra(OBJETO_JSON_EXTRA);

        nombreText = (EditText) findViewById(R.id.NombreText);
        appaternoText = (EditText) findViewById(R.id.AppaternoText);
        amaternaText = (EditText) findViewById(R.id.ApmaternoText);
        usuarioText = (EditText) findViewById(R.id.UsuarioText);
        passwordText = (EditText) findViewById(R.id.PasswordText);
        privilegios = (CheckBox) findViewById(R.id.privilegiosBox);

        final ProgressDialog barraDeProgreso = new ProgressDialog(ParseaObjetoArreglo.this);
        barraDeProgreso.setMessage("Cargando datos...");
        barraDeProgreso.show();

        // y luego definimos los valores que nos regresa a un editText

        nombreText.setText(usuario.getNombre());
        appaternoText.setText(usuario.getAppaterno());
        amaternaText.setText(usuario.getApmaterno());
        usuarioText.setText(usuario.getusuario());
        passwordText.setText(usuario.getPassword());


        if(usuario.getRol().equals(priv)){
            privilegios.setChecked(true);

        }else privilegios.setChecked(false);


        barraDeProgreso.hide();
    }

    public void editarUsuario(View view){

        final ProgressDialog barraDeProgreso = new ProgressDialog(ParseaObjetoArreglo.this);
        barraDeProgreso.setMessage("Editando...");
        barraDeProgreso.show();

        SERVICIO_Update = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.usuario.update.php?";

        id = usuario.getId().trim();
        nombre = nombreText.getText().toString().trim();
        appaterno = appaternoText.getText().toString().trim();
        apmaterno = amaternaText.getText().toString().trim();
        usuario2 = usuarioText.getText().toString().trim();
        password = passwordText.getText().toString().trim();

        if(privilegios.isChecked()){
            privilegiosText = "Administrador";
        }else privilegiosText = "Empleado";

        SERVICIO_Update = SERVICIO_Update +

                ID + "=" + id + "&" +
                NOMBRE + "=" + nombre + "&" +
                APPATERNO + "=" + appaterno + "&" +
                APMATERNO + "=" + apmaterno + "&" +
                USUARIO + "=" + usuario2 + "&" +
                PASSWORD + "=" + password + "&" +
                ROL + "=" + privilegiosText.trim();

        JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_Update, new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                barraDeProgreso.hide();
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    //Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {

                        Toast.makeText(ParseaObjetoArreglo.this,
                                "Usuario editado", Toast.LENGTH_LONG).show();
                        Log.d(TAG,response.toString());

                    }
                } catch (JSONException e) {
                    Toast.makeText(ParseaObjetoArreglo.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                barraDeProgreso.hide();
                Toast.makeText(ParseaObjetoArreglo.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(ID,id);
                map.put(NOMBRE, nombre);
                map.put(APPATERNO, appaterno);
                map.put(APMATERNO, apmaterno);
                map.put(USUARIO, usuario2);
                map.put(PASSWORD, password);
                map.put(ROL, privilegiosText.trim());
                return map;
            }

            @Override public Map<String, String> getHeaders() throws AuthFailureError {
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

    public void eliminarUsuario(View v){

        final ProgressDialog barraDeProgreso = new ProgressDialog(ParseaObjetoArreglo.this);
        barraDeProgreso.setMessage("Eliminando...");
        barraDeProgreso.show();

        SERVICIO_Borrar = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.borrar.usuario.php?";

        id = usuario.getId().trim();


        SERVICIO_Borrar = SERVICIO_Borrar + ID + "=" + id;

        JsonArrayRequest peticion = new JsonArrayRequest(SERVICIO_Borrar, new Response.Listener<JSONArray>() {
            @Override public void onResponse(JSONArray response) {
                barraDeProgreso.hide();
                try {
                    JSONObject autenticacion = (JSONObject) response.get(0);
                    String codigo_autenticacion = autenticacion.getString("Codigo").toString();
                    //Log.d(TAG,response.toString());

                    if (codigo_autenticacion.equals("01")) {

                        Toast.makeText(ParseaObjetoArreglo.this,
                                "Usuario eliminado", Toast.LENGTH_LONG).show();
                        Log.d(TAG,response.toString());

                    }
                } catch (JSONException e) {
                    Toast.makeText(ParseaObjetoArreglo.this, "Problema en: " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override public void onErrorResponse(VolleyError error) {
                barraDeProgreso.hide();
                Toast.makeText(ParseaObjetoArreglo.this, "Error en: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(ID,id);

                return map;
            }

            @Override public Map<String, String> getHeaders() throws AuthFailureError {
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
