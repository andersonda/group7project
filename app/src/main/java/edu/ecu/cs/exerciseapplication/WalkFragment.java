package edu.ecu.cs.exerciseapplication;

import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by danderson on 11/24/17.
 */

public class WalkFragment extends Fragment implements SensorEventListener{

    private TextView mTextViewSteps;
    private ToggleButton mButtonStartStop;

    private int mCurrentSteps;

    private SensorManager mSensorManager;
    private Sensor mCounter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        View v = inflater.inflate(R.layout.fragment_walk,container,false);
        mTextViewSteps = v.findViewById(R.id.steps_text_view);
        mButtonStartStop = v.findViewById(R.id.walk_start_stop);
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
            mTextViewSteps.setText(String.valueOf(sensorEvent.values[0]) + " Steps");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
