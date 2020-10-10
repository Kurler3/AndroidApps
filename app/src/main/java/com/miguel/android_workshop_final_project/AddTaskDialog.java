package com.miguel.android_workshop_final_project;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AddTaskDialog extends AppCompatDialogFragment {
    private EditText newTaskNameInput;
    private AddTaskListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.insert_task_dialog,null);

        builder.setView(view)
                .setTitle("Add New Task")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setNeutralButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String newTaskName = newTaskNameInput.getText().toString();

                        if(!newTaskName.isEmpty()){
                            listener.ApplyTaskName(newTaskName);
                        }
                    }
                });

        newTaskNameInput = (EditText) view.findViewById(R.id.newTaskNameInput);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (AddTaskListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement AddTaskListener");
        }
    }

    public interface AddTaskListener{
        void ApplyTaskName(String newTaskName);
    }
}
