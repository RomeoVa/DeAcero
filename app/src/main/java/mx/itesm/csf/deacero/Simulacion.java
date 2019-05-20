package mx.itesm.csf.deacero;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gc.materialdesign.views.ButtonRectangle;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Simulacion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Simulacion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Simulacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Entry> entries = new ArrayList<>();
    ArrayList<BarEntry> entries1 = new ArrayList<>();
    private Spinner spinnerSimulacion;
    private CombinedChart chart;
    private ButtonRectangle btnSubmit;
    String f,anio;
    int  mes,colord;
    private final int count = 12;
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.simulacion.php?tabla=Simulacion";
    String url2 = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.simulacion.php?tabla=Merma&anio=2019";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Simulacion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Simulacion.
     */
    // TODO: Rename and change types and number of parameters
    public static Simulacion newInstance(String param1, String param2) {
        Simulacion fragment = new Simulacion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_simulacion, container, false);
        spinnerSimulacion = rootView.findViewById(R.id.spinnerSim);
        btnSubmit = rootView.findViewById(R.id.btnSubmit2);

        // Inflate the layout for this fragment
        chart = rootView.findViewById(R.id.chart1);
        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(false);
        chart.setDrawBarShadow(false);
        chart.setHighlightFullBarEnabled(false);

        // draw bars behind lines
        chart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.BUBBLE, CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE, CombinedChart.DrawOrder.SCATTER
        });

        Legend l = chart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        Simulacion(url);
        addItemsOnSpinner2(spinnerSimulacion);
        addListenerOnButton(spinnerSimulacion,btnSubmit);




        return rootView;
    }


    public void Simulacion(String uri){

        final XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new ValueFormatter() {

        });


        final CombinedData data = new CombinedData();

        final LineData d = new LineData();
        final BarData b = new BarData();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            entries1.clear();
                            entries.clear();
                            JSONArray jsonArray = response.getJSONArray("Datos");
                            //numeros = new int[jsonArray.length()];
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject dato = jsonArray.getJSONObject(i);
                                int produccion = dato.getInt("Produccion");
                                int merma = dato.getInt("Merma");
                                String fecha = dato.getString("Fecha");
                                //f = fecha.substring(fecha.indexOf("-") + 1);
                                f = fecha.substring(fecha.indexOf("-") + 1, fecha.lastIndexOf("-"));

                                //f = fecha.substring(fecha.lastIndexOf("-") + 1);

                                mes = Integer.parseInt(f);
                                Log.d("Numerote",String.valueOf(mes));
                                //numeros[i] = toneladas;
                                entries.add(new Entry(mes, merma));
                                entries1.add(new BarEntry(mes,produccion));

                            }
                            //Log.d("Listaantes",entries.toString());
                            //Log.d("Lista",entries.toString());

                            BarDataSet set1 = new BarDataSet(entries1, "Producción");
                            set1.setColor(Color.rgb(60, 220, 78));
                            set1.setValueTextColor(Color.rgb(60, 220, 78));
                            set1.setValueTextSize(10f);
                            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
                            final BarData b = new BarData(set1);


                            LineDataSet set = new LineDataSet(entries, "Merma");
                            set.setColor(Color.rgb(240, 238, 70));
                            set.setLineWidth(2.5f);
                            set.setCircleColor(Color.rgb(240, 238, 70));
                            set.setCircleRadius(5f);
                            set.setFillColor(Color.rgb(240, 238, 70));
                            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                            set.setDrawValues(true);
                            set.setValueTextSize(10f);
                            set.setValueTextColor(Color.rgb(240, 238, 70));

                            set.setAxisDependency(YAxis.AxisDependency.LEFT);
                            d.addDataSet(set);
                            data.setData(d);
                            data.setData(b);
                            xAxis.setAxisMaximum(data.getXMax() + 0.25f);

                            chart.setData(data);
                            chart.animateY(2000);
                            chart.invalidate();


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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

    public void addItemsOnSpinner2(Spinner s) {
        //View myFragmentView = inflater.inflate(R.layout.frag_cityhall, container, false);
        //spinnerSimulacion = (Spinner) this.getView().findViewById(R.id.spinnerSim);
        List<String> list = new ArrayList<String>();
        list.add("2019");
        list.add("2020");
        list.add("2021");
        list.add("2022");
        list.add("2023");
        list.add("2024");
        list.add("2025");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(dataAdapter);
    }


    // get the selected dropdown list value
    public void addListenerOnButton(Spinner s,ButtonRectangle b) {


        spinnerSimulacion = s;
        btnSubmit = b;

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //mes = String.valueOf(spinner1.getSelectedItemPosition()+1);
                anio =  String.valueOf(spinnerSimulacion.getSelectedItem());
                url+="&anio="+anio;

                Simulacion(url);
            }

        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    private LineData generateLineData() {
        LineData d = new LineData();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Datos");
                            //numeros = new int[jsonArray.length()];
                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject dato = jsonArray.getJSONObject(i);
                                int toneladas = dato.getInt("Toneladas");
                                String fecha = dato.getString("Fecha");
                                //f = fecha.substring(fecha.indexOf("-") + 1);
                                //f = fecha.substring(fecha.indexOf("-") + 1, fecha.indexOf("-"));

                                f = fecha.substring(fecha.lastIndexOf("-") + 1);
                                Log.d("Numerote",String.valueOf(toneladas));
                                mes = Integer.parseInt(f);
                                //numeros[i] = toneladas;
                                entries.add(new Entry(i, toneladas));

                            }
                            Log.d("Listaantes",entries.toString());


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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

       // for (int index = 0; index < count; index++)
         //   entries.add(new Entry(index + 0.5f, 15));
        Log.d("Lista",entries.toString());


        LineDataSet set = new LineDataSet(entries, "Merma");
        set.setColor(Color.rgb(240, 238, 70));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        set.setFillColor(Color.rgb(240, 238, 70));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(Color.rgb(240, 238, 70));

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);


        return d;


    }

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        ArrayList<BarEntry> entries2 = new ArrayList<>();

        for (int index = 0; index < count; index++) {
            entries1.add(new BarEntry(index, index));

            // stacked
            entries2.add(new BarEntry(index+1, index +1));
        }

        BarDataSet set1 = new BarDataSet(entries1, "Bar 1");
        set1.setColor(Color.rgb(60, 220, 78));
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);

        BarDataSet set2 = new BarDataSet(entries2, "");
        set2.setStackLabels(new String[]{"Stack 1", "Stack 2"});
        set2.setColors(Color.rgb(61, 165, 255), Color.rgb(23, 197, 255));
        set2.setValueTextColor(Color.rgb(61, 165, 255));
        set2.setValueTextSize(10f);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

        // make this BarData object grouped
        //d.groupBars(0, groupSpace, barSpace); // start at x = 0

        return d;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
