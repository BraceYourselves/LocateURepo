package de.gruppe8.locateu;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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






//----------------------------------------------------------------------------------------------------

public class LocateU extends FragmentActivity implements  GoogleMap.OnMarkerClickListener,AdapterView.OnItemSelectedListener{

//    public LocateU(){}



    GoogleMap mMap;
    GMapV2Direction md;
    private Marker myMarker;
    private ImageView img;
    private DrawerLayout mDrawerLayout;
    private ImageView btn;
    private Spinner spinner;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_u);
       // setContentView(R.layout.activity_main);
        setUpMapIfNeeded();

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Standorte,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);







        img = (ImageButton)findViewById(R.id.homeMenue);

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d("VivZ", " MainActivity  onClick");
                mDrawerLayout.openDrawer(GravityCompat.START);

            }
        });

        btn = (ImageView) findViewById(R.id.close_menue);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeDrawer(v);
            }


        });


    }

    public void closeDrawer(View v) {

        final DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate,9);
        mMap.animateCamera(yourLocation);
        //------------------------------------------------------------------------------------------

        //Markers KK

            mMap.addMarker(new MarkerOptions().position(new LatLng(49.12265595842556, 9.206105768680573)).title("Campus Heilbronn Sontheim Y-Bau" + "74081 Heilbronn"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(49.148336, 9.216587)).title("Campus Heilbronn Am Europaplatz" + "74081 Heilbronn"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(49.275533, 9.712188)).title("Campus Kuenzelsau ReinholdWuerthHochschule" + "74653 Kuenzelsau"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(49.112532, 9.743714)).title("Campus Schweabisch Hall" + "74523 Schweabisch Hall"));

        //------------------------------------------------------------------------------------------


        //Label KK
        final LatLng Heilbronn = new LatLng(49.148336, 9.216587);
        Marker heilbronn_m = mMap.addMarker(new MarkerOptions()
                .position(Heilbronn)
                .title("Heilbronn"));

        heilbronn_m.showInfoWindow();

        //------------------------------------------------------------------------------------------




    }
    // get curent Position KK
    public LatLng get_curent_position() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double current_longitude = location.getLongitude();
        double current_latitude = location.getLatitude();
        LatLng cposi = new LatLng(current_latitude,current_longitude);
        return cposi;
        //------------------------------------------------------------------------------------------
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
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

        TextView myText = (TextView) view;
        Toast.makeText(this, "You Select" + myText.getText()+"Id = "+id+"long id = ", Toast.LENGTH_SHORT).show();
        Log.d("VivZ", " LocateU  onItemSelected");

//        LatLng fromPosition = get_curent_position();
//        LatLng toPosition = marker.getPosition();
//        GMapV2Direction.routeberechnen(fromPosition, toPosition, mMap); // routeberechnen in GMapV2Direction KK

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}




