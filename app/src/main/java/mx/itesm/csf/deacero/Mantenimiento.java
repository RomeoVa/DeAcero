package mx.itesm.csf.deacero;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


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
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = "ListaDatos";
    private final String EXTRA_JSON_OBJECT = "objetoAuto";
    String url = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.entrada.php";
    private BarChart barChart;
    private int[] datos;
    private RequestQueue mQueue;
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

        Log.d("CREATION","AGAP");

        final ProgressDialog barraDeProgreso = new ProgressDialog(getActivity());
        //barraDeProgreso.setMessage("Cargando datos...");
        //barraDeProgreso.show();

        final List<BarEntry> entries = new ArrayList<>();


        // Anexamos el request a la cola



        /*entries.add(new BarEntry(0, 30));
        entries.add(new BarEntry(1, 80));
        entries.add(new BarEntry(2, 60));
        entries.add(new BarEntry(3, 50));
        // gap of 2f
        entries.add(new BarEntry(5, 70));
        entries.add(new BarEntry(6, 60));
        */

        BarDataSet set = new BarDataSet(entries, "BarDataSet");


        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

        barChart.setData(data);


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
    public ArrayList<Integer> jsonParse(){
        String url = "http://ubiquitous.csf.itesm.mx/~pddm-1020736/content/Deacero/Servicios/servicio.entrada.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Datos");

                            for(int i = 0;i < jsonArray.length();i++){
                                JSONObject dato = jsonArray.getJSONObject(i);
                                int toneladas = dato.getInt("Toneladas");
                                String fecha = dato.getString("Fecha");
                                x.add(toneladas);
                                //mTextViewResult.append(fecha + "," +String.valueOf(toneladas) + "\n\n");
                            }

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
        return x;
        //mQueue.add(request);
    }


}
