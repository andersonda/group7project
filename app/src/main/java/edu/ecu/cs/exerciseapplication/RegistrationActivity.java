package edu.ecu.cs.exerciseapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.registration_container);

        if (fragment == null) {
            fragment = new RegistrationFragment();
            fm.beginTransaction()
                    .add(R.id.registration_container, fragment)
                    .commit();
        }
    }
}
