package de.gruppe8.locateu;
/**
 Gruppe 8
 Daniel Kramer 187239& Kirill Kramer 187240
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
Gruppe 8
 Daniel Kramer 187239& Kirill Kramer 187240
 */
public class NavigationDrawerFragment extends Fragment implements VivzAdapter.ClickListener {
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
    private ImageButton img;
    private Context context;


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



                Log.d("VivZ", " NavigationDraweFragment  onCreate");



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
                recyclerView= (RecyclerView) layout.findViewById(R.id.drawerList);
               adapter = new VivzAdapter(getActivity(),getData());
                adapter.setClickListener(this);
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


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();



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

    @Override
    public void itemClicked(View view, int position) {




            if (position == 0){

                startActivity(new Intent(getActivity(), MainActivity.class));


            }
            else if (position == 1){


            }




    }
}
