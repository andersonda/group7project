package edu.ecu.cs.exerciseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Created by danderson on 9/12/17.
 */

public class RegistrationFragment extends Fragment {

    private User mUser;

    private EditText mEditFname, mEditLname, mEditAge, mEditWeight, mEditHeight;
    private Switch mSwitchWeight, mSwitchHeight, mSwitchSex;
    private Button mSubmitButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mEditFname = v.findViewById(R.id.register_fname);
        mEditLname = v.findViewById(R.id.register_lname);
        mEditAge = v.findViewById(R.id.register_age);
        mEditWeight = v.findViewById(R.id.register_weight);
        mEditHeight = v.findViewById(R.id.register_height);

        mSwitchHeight = v.findViewById(R.id.register_height_switch);
        mSwitchWeight = v.findViewById(R.id.register_weight_switch);
        mSwitchSex = v.findViewById(R.id.register_sex_switch);
        mSubmitButton = v.findViewById(R.id.register_submit);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = HomeActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });
        return v;
    }

}
