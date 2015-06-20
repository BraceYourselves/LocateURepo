package com.kulak.locateu_dk;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.StrictMode;






//----------------------------------------------------------------------------------------------------

public class LocateU extends FragmentActivity implements  GoogleMap.OnMarkerClickListener{
    GoogleMap mMap;
    GMapV2Direction md;
    private Marker myMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate_u);
        setUpMapIfNeeded();
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
            GMapV2Direction.routeberechnen(fromPosition,toPosition,mMap); // routeberechnen in GMapV2Direction KK

            //------------------------------------------------------------------------------------------

        return true;
    }
}




