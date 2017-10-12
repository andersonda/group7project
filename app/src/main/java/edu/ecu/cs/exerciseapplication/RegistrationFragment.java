package edu.ecu.cs.exerciseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by danderson on 9/12/17.
 */

public class RegistrationFragment extends Fragment {

    private EditText mEditFname, mEditLname, mEditAge, mEditWeight, mEditHeight;
    private Switch mSwitchWeight, mSwitchHeight, mSwitchSex;
    private Button mSubmitButton;
    private TextView mWeightTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        mWeightTextView = v.findViewById(R.id.lb_kg_textview);

        mEditFname = v.findViewById(R.id.register_fname);
        mEditLname = v.findViewById(R.id.register_lname);
        mEditAge = v.findViewById(R.id.register_age);
        mEditWeight = v.findViewById(R.id.register_weight);
        mEditHeight = v.findViewById(R.id.register_height);
        mSwitchHeight = v.findViewById(R.id.register_height_switch);
        mSwitchWeight = v.findViewById(R.id.register_weight_switch);
        mSwitchWeight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mWeightTextView.setText(isChecked ? "kg" : "lb");
            }
        });
        mSwitchSex = v.findViewById(R.id.register_sex_switch);
        mSubmitButton = v.findViewById(R.id.register_submit);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(mEditFname) || isEmpty(mEditLname) || isEmpty(mEditAge) || isEmpty(mEditHeight)
                    || isEmpty(mEditWeight)){
                    Toast.makeText(getActivity(), "A required field was left blank!", Toast.LENGTH_SHORT).show();
                }
                else{
                    double weight = Double.parseDouble(mEditWeight.getText().toString());
                    if(mSwitchWeight.isChecked()){
                        weight = User.convertKGtoLB(weight);
                    }
                    User mUser = new User(
                            getActivity(),
                            mEditFname.getText().toString(),
                            mEditLname.getText().toString(),
                            weight,
                            Double.parseDouble(mEditHeight.getText().toString()),
                            Integer.parseInt(mEditAge.getText().toString()),
                            mSwitchSex.isActivated()
                    );

                    Intent intent = HomeActivity.newIntent(getActivity());
                    startActivity(intent);
                }

            }
        });
        return v;
    }

    private static boolean isEmpty(EditText editText){
        return editText.getText().toString().matches("");
    }

}
