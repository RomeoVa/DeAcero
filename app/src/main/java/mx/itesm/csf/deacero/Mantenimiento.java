package mx.itesm.csf.deacero;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Mantenimiento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Mantenimiento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Mantenimiento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private long tiempodeEspera = 3000; //milisegundos
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = "ListaDatos";
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.entrada.php";
    private BarChart barChart;
    private int[] datos;
    //private RequestQueue mQueue;
    public static ArrayList<Integer> x = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Mantenimiento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Mantenimiento.
     */
    // TODO: Rename and change types and number of parameters
    public static Mantenimiento newInstance(String param1, String param2) {
        Mantenimiento fragment = new Mantenimiento();
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
        View rootView = inflater.inflate(R.layout.fragment_mantenimiento, container, false);

        // Inflate the layout for this fragment
        barChart = (BarChart) rootView.findViewById(R.id.barchart);





        AlertDialog.Builder AlertaSimulada = new AlertDialog.Builder(getActivity());
        AlertaSimulada.setTitle("Alerta").setMessage("Banda 3 requiere mantenimieno por alta temperatura.")
                .setNeutralButton("Entendido",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }});
        AlertDialog Alerta = AlertaSimulada.create();
        Alerta.show();
        TimerTask tarea = new TimerTask() {

            public void run() {


                //Intent intentoPrincipal = new Intent().setClass(MainActivity.this, parseaJSON.class);
                //startActivity(intentoPrincipal);
            }
        };
        Timer timer = new Timer();
        timer.schedule(tarea, tiempodeEspera);



        //Log.d("CREATION","AGAP");

        //final ProgressDialog barraDeProgreso = new ProgressDialog(getActivity());
        //barraDeProgreso.setMessage("Cargando datos...");
        //barraDeProgreso.show();
       /*
        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("CREATION","AGAP");

                        Log.d(TAG,response.toString());
                        Log.d(TAG,"Tamano "+response.length());
                        x = parserJSON.regresaTons(response);
                        barraDeProgreso.hide();
                        final List<BarEntry> entries = new ArrayList<>();
                        for(int i = 0;i < x.size();i++){
                            entries.add(new BarEntry(i, x.get(i)));

                        }
                        // creamos un adaptador personalizado para la lista de vehiculos
                        BarDataSet set = new BarDataSet(entries, "BarDataSet");


                        BarData data = new BarData(set);
                        data.setBarWidth(0.9f); // set custom bar width
                        barChart.setData(data);
                        barChart.setFitBars(true); // make the x-axis fit exactly all bars
                        barChart.invalidate(); // refresh

                        barChart.setData(data);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error en: " + error.getMessage());
                // oculta la barra de progreso
                barraDeProgreso.hide();
            }
        });
        /*entries.add(new BarEntry(0, 30));
        entries.add(new BarEntry(1, 80));
        entries.add(new BarEntry(2, 60));
        entries.add(new BarEntry(3, 50));
        // gap of 2f
        entries.add(new BarEntry(5, 70));
        entries.add(new BarEntry(6, 60));
        */

        //mQueue.add(request);
        return rootView;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
