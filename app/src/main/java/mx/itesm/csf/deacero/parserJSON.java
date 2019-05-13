package mx.itesm.csf.deacero;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class parserJSON {

    public static ArrayList<Trituradora> Datos = new ArrayList<>();

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

    public static int[] regresaTons(JSONArray arr) {

        JSONObject obj=null;
        Trituradora trituradora = null;
        Datos.clear();
        int tons;

        try {
            int[] x = new int[arr.length()];
            for(int i = 0;i<arr.length();i++) {

                obj = arr.getJSONObject(i);
                trituradora = new Trituradora();

                tons = (obj.getInt("Toneladas"));
                trituradora.setFecha(obj.getString("Fecha"));

                x[i] = tons;
                //Datos.add(trituradora);
            }
            return x;

        } catch (JSONException e1) {
            e1.printStackTrace();
            return null;
        }
    }
}