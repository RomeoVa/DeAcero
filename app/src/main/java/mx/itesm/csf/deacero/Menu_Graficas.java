package mx.itesm.csf.deacero;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.EntryXComparator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Menu_Graficas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Produccion.OnFragmentInteractionListener,Mantenimiento.OnFragmentInteractionListener,
        Energia.OnFragmentInteractionListener,Inicio.OnFragmentInteractionListener,Simulacion.OnFragmentInteractionListener {

    private static final String TAG = "menu_graficas";
    private MenuItem itemToHide;
    private MenuItem itemToShow;
    String user,nombre;

    NavigationView navigationView;
    private Usuario datosUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu__graficas);
        final Intent intent = getIntent();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.Contenedor, new Inicio())
                .commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        invalidateOptionsMenu();
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);


        nombre = preferences.getString("nombre","No hay información");
        user = preferences.getString("user","No hay información");
        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navNombre = (TextView) headerView.findViewById(R.id.Nombre);
        TextView navUsername = (TextView) headerView.findViewById(R.id.Usuario);
        navUsername.setText(user);
        navNombre.setText(nombre);


        if(!datosUsuario.getInstance().getRol().equals("Administrador"))
        {
            hideItem();
        }


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__graficas, menu);
        //getMenuInflater().inflate(R.menu.activity_menu__graficas_drawer, menu);
        //itemToHide = menu.findItem(R.id.Produccion);
        //itemToHide.setVisible(false);
        //itemToShow = menu.findItem(R.id.item_to_show);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;
        Boolean FragmentoSeleccionado  = false;

        if(id == R.id.Inicio){
            fragment = new Inicio();
            FragmentoSeleccionado = true;
        }
        else if (id == R.id.Produccion) {
            fragment = new Produccion();
            FragmentoSeleccionado = true;
            // Handle the camera action
            //Toast toast1 = Toast.makeText(getApplicationContext(), "Prueba ASF", Toast.LENGTH_SHORT);
            //toast1.setGravity(Gravity.CENTER, 0,0 );

            //toast1.show();
        } else if (id == R.id.Mantenimiento) {
            fragment = new Mantenimiento();
            FragmentoSeleccionado = true;



        } else if (id == R.id.Energia) {
            fragment = new Energia();
            FragmentoSeleccionado = true;

        }else if (id == R.id.Simulacion) {
            fragment = new Simulacion();
            FragmentoSeleccionado = true;
        }else if (id == R.id.Usuarios){
            Intent intent = new Intent(getBaseContext(), ParseaArregloJSON.class);
            startActivity(intent);

        }

        if(FragmentoSeleccionado){
            getSupportFragmentManager().beginTransaction().replace(R.id.Contenedor,fragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void hideItem()
    {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.Usuarios).setVisible(false);
    }
}
