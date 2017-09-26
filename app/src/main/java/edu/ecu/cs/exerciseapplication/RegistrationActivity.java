package edu.ecu.cs.exerciseapplication;

import android.support.v4.app.Fragment;

public class RegistrationActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment(){
        return new RegistrationFragment();
    }
}
