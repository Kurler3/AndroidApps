package com.miguel.android_workshop_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    Context context;
    ArrayList<Event> eventArrayList;

    public EventAdapter(Context context, ArrayList<Event> eventArrayList) {
        this.context = context;
        this.eventArrayList = eventArrayList;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        TextView eventDateText;
        TextView eventTitleText;
        CheckBox eventCheckBox;
        public EventViewHolder(@NonNull final View itemView) {
            super(itemView);
            eventDateText = itemView.findViewById(R.id.eventDate);
            eventTitleText = itemView.findViewById(R.id.eventTitle);
            eventCheckBox = itemView.findViewById(R.id.eventCheckBox);

            eventCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(compoundButton.isChecked()){
                        EventsList.eventCheckedList.set(getAdapterPosition(),true);
                    }else{
                        EventsList.eventCheckedList.set(getAdapterPosition(),false);
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.example_event_item,parent,false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
         Event currentEvent = eventArrayList.get(position);

         holder.eventDateText.setText(currentEvent.getDate());
         holder.eventTitleText.setText(currentEvent.getTitle());
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

}
