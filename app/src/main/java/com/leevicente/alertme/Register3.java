package com.leevicente.alertme;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leevicente.alertme.helpers.InputValidation;
import com.leevicente.alertme.sql.DatabaseHelper;
import com.leevicente.alertme.modal.User;

public class Register3 extends AppCompatActivity {
    public final AppCompatActivity activity = Register3.this;

    private EditText username;
    private EditText password;
    private EditText confirm;

    private Button register;
    private Button backReg2;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    private final int SPLASH_DISPLAY_LENGTH = 550;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        initReg3();
        initObjects();
    }

    private void initReg3(){
        username = findViewById(R.id.reguser);
        password =  findViewById(R.id.regpass);
        confirm = findViewById(R.id.regconfpass);

        register = findViewById(R.id.registerDone);
        backReg2 = findViewById(R.id.backPage2);
        user = new User();
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }


    public void buttonClickRegister3(View v){
        Intent i;
        if (v.getId() == R.id.backPage2) {
            i = new Intent(this, Register2.class);
            startActivity(i);
        } else if (v.getId() == R.id.registerDone) {
            insertUser();
        }
    }

    public void insertUser(){
        if(!inputValidation.isInputEditTextField(username, "username")){
            return;
        }
        if(!inputValidation.isInputEditTextField(password, "password")){
            return;
        }
        if(!inputValidation.isInputEditTextField(confirm, "confirm")){
            return;
        }
        if(!inputValidation.isInputEditTextMatches(password, confirm)){
            return;
        }
        if(!databaseHelper.checkUser(username.getText().toString().trim())){
            Intent in = getIntent();
            Bundle b = in.getExtras();
            String firstname = b.getString("Firstname");
            String lastname = b.getString("Lastname");
            String contact = b.getString("Contact");
            String emerContant = b.getString("EmerContact");
            String emerName = b.getString("EmerName");
            String uname = username.getText().toString().trim();
            String pass = password.getText().toString().trim();

            user.setFirstName(firstname);
            user.setLastName(lastname);
            user.setContact(contact);
            user.setEmerContact(emerContant);
            user.setEmerName(emerName);
            user.setUserName(uname);
            user.setPassword(pass);
            databaseHelper.addUser(user);
            Toast.makeText(Register3.this,"Username " + username.getText().toString().trim() +" added", Toast.LENGTH_SHORT).show();
            Intent toLogin = new Intent(activity, Login.class);
            startActivity(toLogin);
        }
    }
}
