package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by danderson on 11/24/17.
 */

public class WalkFragment extends Fragment implements SensorEventListener{

    private TextView mTextViewSteps;
    private ToggleButton mButtonStartStop;
    private Button mButtonSubmit;

    private int mCurrentSteps;

    private StepHistory mStepHistory;

    private SensorManager mSensorManager;
    private Sensor mCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        mStepHistory = StepHistory.get(getActivity());

        View v = inflater.inflate(R.layout.fragment_walk,container,false);
        mTextViewSteps = v.findViewById(R.id.steps_text_view);
        mButtonStartStop = v.findViewById(R.id.walk_start_stop);
        mButtonStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    mButtonSubmit.setEnabled(false);
                }
                else{
                    mButtonSubmit.setEnabled(true);
                }
            }
        });

        mButtonSubmit = v.findViewById(R.id.submit_walk);
        mButtonSubmit.setEnabled(false);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSensorManager.unregisterListener(WalkFragment.this);
                Steps steps = new Steps(mCurrentSteps);
                mStepHistory.addSteps(steps);
                Intent intent = StepsListActivity.newIntent(getActivity());
                startActivity(intent);
                getActivity().finish();
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(mCounter != null){
            mSensorManager.registerListener(this, mCounter, SensorManager.SENSOR_DELAY_UI);
        }
        else{
            Toast.makeText(getActivity(), "Step Counter Is Not Available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(mButtonStartStop.isChecked()){
            mCurrentSteps += 1;
            mTextViewSteps.setText(mCurrentSteps + " Steps");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
