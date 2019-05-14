package mx.itesm.csf.deacero;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.views.ButtonRectangle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Entrada extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;
    private BarChart barChart;
    public ArrayList<String> x = new ArrayList<>();
    private int[] numeros;
    int  dia,colord;
    String tabla,titulo;
    private Spinner spinner1, spinner2;
    private ButtonRectangle btnSubmit;
    String f,mes,anio;
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.mes-anio.php?tabla=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        setContentView(R.layout.activity_test);

        tabla = intent.getStringExtra("tabla");
        setTitle(tabla);
        colord = intent.getIntExtra("color",-256);
        url+=tabla;
        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);
        barChart = (BarChart) findViewById(R.id.barchart);
        //mQueue = Volley.newRequestQueue(this);
        jsonParse(url,barChart);
        addItemsOnSpinner2();
        addListenerOnButton();
        addListenerOnSpinnerItemSelection();
        //Log.d("primero",String.valueOf(numeros[0]));

        //mTextViewResult.append((x.get(0)).toString() + "\n\n");

    }


    public void jsonParse(String uri,BarChart graf){
        final List<BarEntry> entries = new ArrayList<>();
        final ArrayList<Integer> agap = new ArrayList<>();
        barChart = graf;


        String url = uri;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Datos");
                            numeros = new int[jsonArray.length()];
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject dato = jsonArray.getJSONObject(i);
                                int toneladas = dato.getInt("Toneladas");
                                String fecha = dato.getString("Fecha");
                                //f = fecha.substring(fecha.indexOf("-") + 1);


                                f = fecha.substring(fecha.lastIndexOf("-") + 1);
                                Log.d("num",f);
                                dia = Integer.parseInt(f);
                                numeros[i] = toneladas;
                                entries.add(new BarEntry(dia, toneladas));
                                //agap.add(toneladas);
                                //entries.add(new BarEntry(i, toneladas));
                                //mTextViewResult.append(fecha + "," +String.valueOf(numeros[i]) + "\n\n");
                            }

                            BarDataSet set = new BarDataSet(entries, "Toneladas");

                            BarData data = new BarData(set);
                            set.setColor(colord);

                            data.setBarWidth(0.9f); // set custom bar width
                            barChart.setData(data);
                            barChart.setFitBars(true); // make the x-axis fit exactly all bars
                            barChart.invalidate(); // refresh

                            barChart.setData(data);
                            barChart.animateY(2000);
                            barChart.setDrawBarShadow(true);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("2018");
        list.add("2019");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        btnSubmit = (ButtonRectangle) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mes = String.valueOf(spinner1.getSelectedItemPosition()+1);
                anio =  String.valueOf(spinner2.getSelectedItem());
                url+="&mes="+mes+"&anio="+anio;

                jsonParse(url,barChart);
            }

        });
    }
}
