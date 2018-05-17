package com.example.diogo.clima;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ClimaAdapter extends ArrayAdapter<Clima> {
    public ClimaAdapter(@NonNull Context context, @NonNull List<Clima> objects){
        super(context, 0,objects);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        Clima clima = getItem(position);

        View listClima= LayoutInflater.from(getContext()).inflate(R.layout.clima_item,null);


        TextView date= listClima.findViewById(R.id.txtDate);
        TextView weekDay= listClima.findViewById(R.id.txtWeekDay);
        ImageView condition= listClima.findViewById(R.id.imgCondition);
        TextView max= listClima.findViewById(R.id.txtMax);
        TextView min= listClima.findViewById(R.id.txtMin);
        TextView description = listClima.findViewById(R.id.txtDescription);

        date.setText(clima.getDate());
        weekDay.setText(clima.getWeekday());

        max.setText(clima.getMax().concat("°"));
        min.setText(clima.getMin().concat("°"));
        description.setText(clima.getDescription());

        if(clima.getCondition().equals("cloud")){
            condition.setImageResource(R.drawable.cloud);
        }
        if(clima.getCondition().equals("cloudly_day")){
            condition.setImageResource(R.drawable.cloudsun);
        }
        if(clima.getCondition().equals("storm")){
            condition.setImageResource(R.drawable.thunderstorm);
        }
        if(clima.getCondition().equals("clear_day")){
            condition.setImageResource(R.drawable.sun);
        }
        if(clima.getCondition().equals("rain")){
            condition.setImageResource(R.drawable.rain);
        }




        return listClima;
    }


}
