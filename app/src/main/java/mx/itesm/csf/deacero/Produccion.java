package mx.itesm.csf.deacero;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Produccion.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Produccion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Produccion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button botonEntrada,botonFerrosaTramp,botonHeavy3_4,botonHeavy4,botonMas2Heavy,
            botonMenos2Heavy,botonNoFerrosaLight,botonPrincipal;
    //private LineChart mChart;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Produccion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Produccion.
     */
    // TODO: Rename and change types and number of parameters
    public static Produccion newInstance(String param1, String param2) {
        Produccion fragment = new Produccion();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_produccion, container, false);
        botonEntrada = (Button) rootView.findViewById(R.id.Entrada);
        botonFerrosaTramp = (Button) rootView.findViewById(R.id.FerrosaTramp);
        botonHeavy3_4 = (Button) rootView.findViewById(R.id.Heavy_3_4);
        botonHeavy4 = (Button) rootView.findViewById(R.id.Heavy4);
        botonMas2Heavy = (Button) rootView.findViewById(R.id.Mas2Heavy);
        botonMenos2Heavy = (Button) rootView.findViewById(R.id.Menos2Heavy);
        botonNoFerrosaLight = (Button) rootView.findViewById(R.id.NoFerrosaLight);
        botonPrincipal = (Button) rootView.findViewById(R.id.Principal);

        botonEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Entrada");
                i.putExtra("color", -1);
                startActivity(i);
            }
        });

        botonPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Principal");
                i.putExtra("color", -167711681);
                startActivity(i);
            }
        });

        botonNoFerrosaLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Non_Ferrous_Light");
                i.putExtra("color", -16776961);
                startActivity(i);
            }
        });

        botonFerrosaTramp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Ferrous_Tramp");
                i.putExtra("color", -16777216);
                startActivity(i);
            }
        });

        botonHeavy3_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Heavy_3_4");
                i.putExtra("color", -65281);
                startActivity(i);
            }
        });

        botonHeavy4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Heavy_4");
                i.putExtra("color", -256);
                startActivity(i);
            }
        });

        botonMas2Heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Plus2_Heavy");
                i.putExtra("color", -65536);
                startActivity(i);
            }
        });

        botonMenos2Heavy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), Entrada.class);
                i.putExtra("tabla", "Heavy_2");
                i.putExtra("color", -256);
                startActivity(i);
            }
        });




/*
        mChart = (LineChart) rootView.findViewById(R.id.chart);
        //mChart.setOnChartValueSelectedListener(Menu_Graficas.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0,60));
        yValues.add(new Entry(1,50));
        yValues.add(new Entry(2,70));
        yValues.add(new Entry(3,30));
        yValues.add(new Entry(4,50));
        yValues.add(new Entry(5,60));
        yValues.add(new Entry(6,650));

        LineDataSet set1 = new LineDataSet(yValues,"Data Set 1");

        set1.setFillAlpha(110);

        set1.setColor(Color.RED);
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);


        LineData data = new LineData(dataSets);

        mChart.setData(data);
        */
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
