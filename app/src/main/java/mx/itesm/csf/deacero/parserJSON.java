package mx.itesm.csf.deacero;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class parserJSON {

    public static ArrayList<Trituradora> Datos = new ArrayList<>();
    public static ArrayList<Integer> Tons = new ArrayList<>();


    // paseaObjeto toma in objeto JSON como un parÃ¡metro y establece
    // todos los atributos del objeto Auto.
    public static Trituradora parseaObjeto(JSONObject obj) {

        try {
            Trituradora trituradora = new Trituradora();

            trituradora.setFecha(obj.getString("Fecha"));
            trituradora.setToneladas(obj.getInt("Toneladas"));
            return trituradora;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }


    // parseaArreglo toma un objetoArray como parametro y regersa un
    // ArrayList de Auto
    public static ArrayList<Trituradora> parseaArreglo(JSONArray arr) {

        JSONObject obj=null;
        Trituradora trituradora = null;
        Datos.clear();

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                trituradora = new Trituradora();

                trituradora.setToneladas(obj.getInt("Toneladas"));
                trituradora.setFecha(obj.getString("Fecha"));

                Datos.add(trituradora);
            }
            return Datos;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Integer> regresaTons(JSONArray arr) {

        JSONObject obj=null;
        //Auto auto = null;
        Tons.clear();
        Integer tons;

        try {
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                //auto = new Auto();

                //auto.setMarca(obj.getString("Marca"));
                //auto.setAuto(obj.getString("Auto"));
                //auto.setimage(obj.getString("image"));
                tons = obj.getInt("Toneladas");
                Tons.add(tons);
            }
            return Tons;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }




}