package com.kulak.locateu_dk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

/*















    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.



    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.


*/




import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import java.util.ArrayList;

import org.w3c.dom.Document;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * Created by Warburg on 18.06.2015.
 */






//----------------------------------------------------------------------------------------------------

public class LocateU extends FragmentActivity {
    GoogleMap mMap;
    GMapV2Direction md;


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
        //Routing KK
        LatLng fromPosition = new LatLng(49.148336, 9.216587);
        LatLng toPosition = new LatLng(49.112532, 9.743714);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        md = new GMapV2Direction();
        mMap = ((SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        mMap.addMarker(new MarkerOptions().position(fromPosition).title("Start"));
        mMap.addMarker(new MarkerOptions().position(toPosition).title("End"));

        GMapV2Direction.routeberechnen(fromPosition,toPosition,mMap); // routeberechnen in GMapV2Direction KK

        //------------------------------------------------------------------------------------------


    }


    }




