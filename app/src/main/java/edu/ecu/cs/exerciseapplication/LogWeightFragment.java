package edu.ecu.cs.exerciseapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by danderson on 10/25/17.
 */

public class LogWeightFragment extends DialogFragment {

    public static final String EXTRA_WEIGHT
            = "edu.ecu.cs.exerciseapplication.weight";

    private EditText mEditText;

    public static LogWeightFragment newInstance(){
        LogWeightFragment fragment = new LogWeightFragment();
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_weight, null);

        mEditText = v.findViewById(R.id.dialog_enter_weight);
        
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.weight_dialog_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Double weight = Double.parseDouble(mEditText.getText().toString());
                        sendResult(Activity.RESULT_OK, weight);
                    }
                })
                .create();
    }

    private void sendResult(int resultCode, Double weight){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_WEIGHT, weight);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
