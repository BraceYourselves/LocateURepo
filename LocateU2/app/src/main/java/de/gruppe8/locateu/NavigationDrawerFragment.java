package de.gruppe8.locateu;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Use the {@link NavigationDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String PREF_FILE_NAME ="testpref" ;
    public static final String KEY_USER_LEARNED_DRAWER="user_learned_drawer";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private  DrawerLayout mDrawerLayout;
    private View containerView;
    private  VivzAdapter adapter;

    private  boolean mUserLearnedDrawer; // Sichbarkeit des Drawers
    private boolean mFromSavedInstanceState; // Rotation oder erstmaliger Aufruf
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationDrawerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationDrawerFragment newInstance(String param1, String param2) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
                    if(savedInstanceState!=null){               // Wenn nicht null, dann kommt es von einer Rotation,
                                                                // wenn null wird es zum ersten Mal ausgefuehrt
                            mFromSavedInstanceState=true;       //
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
                recyclerView= (RecyclerView) layout.findViewById(R.id.drawerList);
               adapter = new VivzAdapter(getActivity(),getData());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                return layout;
    }

    public static List<Information>  getData(){
        List<Information> data = new ArrayList<>();
        String[] titles = {"Rutenberechnung","HHN Standorte",""};
        for (int i=0; i<titles.length;i++){

            Information current = new Information();
            current.title = titles[i];
            data.add(current);
        }
        return data;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mDrawerLayout=drawerLayout;
        containerView=getActivity().findViewById(fragmentId);
        mDrawerToggle= new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

        @Override
        public void onDrawerOpened (View drawerView)
        {
            getActivity().invalidateOptionsMenu();          // Zeichne nochmals den Drawer (refresh)

        }

        @Override
        public void onDrawerClosed (View drawerView)
        {
            super.onDrawerClosed(drawerView);
            getActivity().invalidateOptionsMenu();           // Zeichne nochmals den Drawer (refresh)

        }

    };
                mDrawerLayout.setDrawerListener(mDrawerToggle);

                mDrawerLayout.post(new Runnable() {
                        @Override
                        public void run() {
                                mDrawerToggle.syncState();
                            }
                    });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            if (item.getItemId() ==R.id.homeMenue) {
//                Toast.makeText(this,""+ .getString(R.string.item)).show();
//                Toast bread = Toast.makeText(this, R.string.item, Toast.LENGTH_LONG);
                //Toast.makeText(this,getString(R.string.item), Toast.LENGTH_LONG).show();
//                bread.show();

                // startActivity(new Intent(this, MainActivity.class));



                Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();

            }
            return true;
        }

        if (item.getItemId() == R.id.menueFragment) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static void  saveToPreferences(Context context, String preferenceName, String preverenceValue){

        SharedPreferences sharedPreferences = context.getSharedPreferences (PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preverenceValue);
        editor.apply();                                         //asynchroner commit

    }
    public static String readFromPreferences(Context context, String preferenceName, String defaultValue){

                SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                return sharedPreferences.getString(preferenceName,defaultValue);
            }
}
