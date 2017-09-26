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
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.util.List;

/**
 * Created by danderson on 9/12/17.
 */

public class RegistrationFragment extends Fragment {

    private User mUser;

    private EditText mEditFname, mEditLname, mEditAge, mEditWeight, mEditHeight;
    private Switch mSwitchWeight, mSwitchHeight, mSwitchSex;
    private Button mSubmitButton;

    private DatabaseHelper mDBHelper = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDBHelper != null) {
            OpenHelperManager.releaseHelper();
            mDBHelper = null;
        }
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
                if(isEmpty(mEditFname) || isEmpty(mEditLname) || isEmpty(mEditAge) || isEmpty(mEditHeight)
                    || isEmpty(mEditWeight)){
                    Toast.makeText(getActivity(), "A required field was left blank!", Toast.LENGTH_SHORT).show();
                }
                else{
                    User mUser = new User(
                            mEditFname.getText().toString(),
                            mEditLname.getText().toString(),
                            Double.parseDouble(mEditWeight.getText().toString()),
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

    private DatabaseHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(getActivity(),DatabaseHelper.class);
        }
        return mDBHelper;
    }

}
