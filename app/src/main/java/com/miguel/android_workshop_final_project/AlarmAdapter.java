package com.miguel.android_workshop_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {
    Context context;
    ArrayList<Alarm> alarmTimeList;
    Boolean alarmSwitchBool=true;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmTime) {
        this.context = context;
        this.alarmTimeList = alarmTime;
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder{
        TextView alarmTime;
        Switch alarmSwitch;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarmTime);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);

            alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(!compoundButton.isChecked()){
                        alarmSwitchBool=false;
                    }else{
                        alarmSwitchBool=true;
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.example_alarm_listview,parent,false);
        return new AlarmViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        holder.alarmTime.setText(alarmTimeList.get(position).getTime());
    }
    @Override
    public int getItemCount() {
        return alarmTimeList.size();
    }

}
