package de.gruppe8.locateu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by ExxE2 on 19.06.2015.
 */
public class VivzAdapter extends RecyclerView.Adapter<VivzAdapter.MyViewHolder> {

    private  LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    private Context context;

    public VivzAdapter(Context context, List<Information> data){
        this.context = context;
       inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


       int position = getPosition();
            if (position == 0){
                Log.d("VivZ", " position 0");
                context.startActivity(new Intent(context, MainActivity.class));


            }
            if (position == 1){
                Log.d("VivZ"," position 1");

            }
        }
    }
}
