package com.flagcamp.donationcollector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText userTypeText;
    Button loginButton;
    TextView loginMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        userTypeText = findViewById(R.id.user_type);
        loginButton = findViewById(R.id.login_button);
        loginMessage = findViewById(R.id.login_message);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userType = userTypeText.getText().toString();
                if(!userType.equals("user") && !userType.equals("NGO")) {
                    loginMessage.setText("You have to type in user\nor NGO in the edit Text \nabove");
                } else {
                    Class classType = userType.equals("user") ? MainActivityUser.class : MainActivity.class;
                    loginMessage.setText("You are a " + userType);

                    Intent startMainIntent = new Intent(LoginActivity.this, classType);
                    startActivity(startMainIntent);

                    userTypeText.setText("Type in user or NGO");
                    loginMessage.setText("welcome");

//                    LoginActivity.this.startActivity(startMainIntent);
                }

            }
        });
    }


}
