package edu.ecu.cs.exerciseapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class TestUserDataActivity extends AppCompatActivity {
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_user_data);

        mTextView = (TextView) findViewById(R.id.test_user);
        List<User> users = User.listAll(User.class);
        if(users.size() == 0){
          mTextView.setText("No user information is stored");
        }
        else{
          mTextView.setText(users.get(0).toString());
        }
    }
}
