package de.gruppe8.locateu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends ActionBarActivity  implements AdapterView.OnItemSelectedListener,GoogleMap.OnMarkerClickListener{

    private Toolbar toolbar;
    private ImageButton img;
    private DrawerLayout mDrawerLayout;
    private ImageView btn;
    private Spinner spinner;
    GoogleMap mMap;
    GMapV2Direction md;
    private Marker myMarker;
    Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpMapIfNeeded();


        toolbar = (Toolbar) findViewById(R.id.appbar);
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Standorte,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        gps_wifi_enabled();


        setSupportActionBar(toolbar);                   // hier wird unsere Toolbar benutzt


        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.main_layout), toolbar);



        //Generierung eines klickbaren homeButton

        img = (ImageButton)findViewById(R.id.homeMenue);

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);

        // Beim klicken öffnet sich der NavigationDrawe
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("VivZ", " MainActivity  onClick");
                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });

        ////Generierung eines klickbaren closeButon
         btn = (ImageView) findViewById(R.id.close_menue);

        // Beim klicken schließt sich der NavigationDrawe
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(v);
            }


        });

        btnClick();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.homeMenue) {
            showMsg("Ok");
            openOptionsMenu();
            return true;
        }
        if (id == R.id.appbar) {

            openOptionsMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMsg(String msg) {
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, toast.getXOffset() / 2, toast.getYOffset() / 2);
        toast.show();
    }


    public void closeDrawer(View v) {

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.main_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {


        mMap.setOnMarkerClickListener(this);
        mMap.setMyLocationEnabled(true); //MyLocation KK

        //First View KK

        LatLng coordinate = new LatLng(49.186646, 9.497189);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 9);
        mMap.animateCamera(yourLocation);
        //------------------------------------------------------------------------------------------

        //Markers KK
        getMarkers();
        //------------------------------------------------------------------------------------------


        //Label KK
        final LatLng Heilbronn = new LatLng(49.148336, 9.216587);
        Marker heilbronn_m = mMap.addMarker(new MarkerOptions()
                .position(Heilbronn)
                .title("Heilbronn"));

        heilbronn_m.showInfoWindow();

        //------------------------------------------------------------------------------------------

    }
    // ini Button
    public void btnClick() {
        Toast.makeText(this, "You have Button Enabled", Toast.LENGTH_LONG).show();
        button = (Button) findViewById(R.id.s_exit);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
            System.exit(0);
            }
        });
    }


//    show u if gps or wifi is not enabled KK
    public void gps_wifi_enabled(){
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

        }else{
            Toast.makeText(this, "You have GPS not Enabled", Toast.LENGTH_LONG).show();
        }

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            // Do whatever
        }else{
            Toast.makeText(this, "You have Wifi not Enabled", Toast.LENGTH_LONG).show();
        }

    }
    //------------------------------------------------------------------------------------------
    // get curent Position KK
    public LatLng get_curent_position() {
        gps_wifi_enabled();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double current_longitude = location.getLongitude();
        double current_latitude = location.getLatitude();
        LatLng cposi = new LatLng(current_latitude,current_longitude);
        return cposi;
        //------------------------------------------------------------------------------------------
    }
    //Markers KK
    public void getMarkers(){
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.12265595842556, 9.206105768680573)).title("Campus Heilbronn Sontheim Y-Bau" + "74081 Heilbronn"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.148336, 9.216587)).title("Campus Heilbronn Am Europaplatz" + "74081 Heilbronn"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.275533, 9.712188)).title("Campus Kuenzelsau ReinholdWuerthHochschule" + "74653 Kuenzelsau"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.112532, 9.743714)).title("Campus Schweabisch Hall" + "74523 Schweabisch Hall"));
    }
    //------------------------------------------------------------------------------------------


    @Override
    public boolean onMarkerClick(Marker marker) {
        gps_wifi_enabled();
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.12265595842556, 9.206105768680573)).title("Campus Heilbronn Sontheim Y-Bau" + "74081 Heilbronn"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.148336, 9.216587)).title("Campus Heilbronn Am Europaplatz" + "74081 Heilbronn"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.275533, 9.712188)).title("Campus Kuenzelsau ReinholdWuerthHochschule" + "74653 Kuenzelsau"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(49.112532, 9.743714)).title("Campus Schweabisch Hall" + "74523 Schweabisch Hall"));

        //Routing KK


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        LatLng fromPosition = get_curent_position();
        LatLng toPosition = marker.getPosition();
        GMapV2Direction.routeberechnen(fromPosition, toPosition, mMap); // routeberechnen in GMapV2Direction KK

        //------------------------------------------------------------------------------------------

        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            mMap = ((SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map)).getMap();


            if (position == 4) {
                LatLng coordinate = new LatLng(49.186646, 9.497189);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 9);
                mMap.animateCamera(yourLocation);
                mMap.clear();
                LatLng currentPosition = get_curent_position();
                LatLng target = new LatLng(49.112532, 9.743714);
                GMapV2Direction.routeberechnen(currentPosition, target, mMap); // routeberechnen in GMapV2Direction KK
            }else if (position == 3) {
                LatLng coordinate = new LatLng(49.186646, 9.497189);
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 9);
                mMap.animateCamera(yourLocation);
                mMap.clear();
                LatLng currentPosition = get_curent_position();
                LatLng target = new LatLng(49.275533, 9.712188);
                GMapV2Direction.routeberechnen(currentPosition, target, mMap); // routeberechnen in GMapV2Direction KK
                TextView myText = (TextView) view;
            }else if (position == 2) {

                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(get_curent_position(), 13);
                mMap.animateCamera(yourLocation);

                mMap.clear();
                LatLng currentPosition = get_curent_position();
                LatLng target = new LatLng(49.148336, 9.216587);
                GMapV2Direction.routeberechnen(currentPosition, target, mMap); // routeberechnen in GMapV2Direction KK

            } else if (position == 1) {
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(get_curent_position(), 12);
                mMap.animateCamera(yourLocation);
            mMap.clear();
            LatLng currentPosition = get_curent_position();
            LatLng target = new LatLng(49.12265595842556, 9.206105768680573);
            GMapV2Direction.routeberechnen(currentPosition, target, mMap); // routeberechnen in GMapV2Direction KK

        }else if (position == 0) {
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(get_curent_position(), 8);
                mMap.animateCamera(yourLocation);
                mMap.clear();
                getMarkers();

            }


            else{
                TextView myText = (TextView) view;
                Toast.makeText(this, "You Select " + myText.getText() + " Id = " + position + " long id = " + id, Toast.LENGTH_SHORT).show();
                Log.d("VivZ", " LocateU  onItemSelected " + id);
            }
        } catch (Exception e) {
            Log.d("Fehler", e.getMessage());


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
