package com.leevicente.alertme;

import android.content.Intent;
import com.leevicente.alertme.helpers.InputValidation;
import com.leevicente.alertme.sql.DatabaseHelper;
import com.leevicente.alertme.helpers.SessionManager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private final AppCompatActivity activity = Login.this;
    private EditText username;
    private EditText password;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private SessionManager session;

    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initLogin();
        initObjects();
    }

    private void initLogin(){
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
        session = new SessionManager(getApplicationContext());
    }

    public void buttonClickLogin(View v){
        Intent i;
        if (v.getId() == R.id.registerBtn) {
            i = new Intent(activity, Register.class);
            startActivity(i);
        } else if (v.getId() == R.id.loginBtn) {
            verifyFromSQLite();
        }
}

    private void verifyFromSQLite(){
        if(!inputValidation.isInputEditTextField(username, "username")){
            return;
        }
        if(!inputValidation.isInputEditTextField(password, "password")){
            return;
        }
        if(databaseHelper.checkUser(username.getText().toString().trim(), password.getText().toString().trim())){
            Intent toMain = new Intent(activity, Main.class);
            toMain.putExtra("Username", username.getText().toString().trim());
            session.createLoginSession(username.getText().toString().trim());
            emptyInputEditText();
            startActivity(toMain);
        }
        else{
            Toast.makeText(Login.this,"No username is found", Toast.LENGTH_SHORT).show();
        }
    }

    private void emptyInputEditText() {
        username.setText(null);
        password.setText(null);
    }
}
