package edu.ecu.cs.exerciseapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.Date;

import edu.ecu.cs.exerciseapplication.R;
import edu.ecu.cs.exerciseapplication.SingleFragmentActivity;

public class StepSummaryActivity extends SingleFragmentActivity {

    public static final String EXTRA_DATE =
            "edu.ecu.cs.exerciseapplication.date";

    @Override
    protected Fragment createFragment() {
        return new StepSummaryFragment();
    }

    public static Intent newIntent(Context context, Date date){
        Intent intent = new Intent(context, StepSummaryActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}
