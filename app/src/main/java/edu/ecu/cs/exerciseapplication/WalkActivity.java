package edu.ecu.cs.exerciseapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WalkActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new WalkFragment();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, WalkActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
