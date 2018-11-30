package com.leevicente.alertme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leevicente.alertme.helpers.InputValidation;
import com.leevicente.alertme.sql.DatabaseHelper;

public class Register extends AppCompatActivity {
    private final AppCompatActivity activity = Register.this;
    private EditText firstname;
    private EditText lastname;
    private EditText contactnum;

    private DatabaseHelper databaseHelper;
    private InputValidation inputValidation;

    private Button nextReg;
    private Button backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initReg1();
        initObjects();
    }

    private void initReg1(){
        firstname = findViewById(R.id.firstname);
        lastname =  findViewById(R.id.lastname);
        contactnum = findViewById(R.id.contactnum);

        nextReg = findViewById(R.id.nextPage);
        backLogin = findViewById(R.id.backLogin);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    public void buttonClickRegister(View v){
        Intent i;
        if (v.getId() == R.id.backLogin) {
            i = new Intent(activity, Login.class);
            startActivity(i);
        } else if (v.getId() == R.id.nextPage) {
            if(!inputValidation.isInputEditTextField(firstname, "firstname")){
                return;
            }
            if(!inputValidation.isInputEditTextField(lastname, "lastname")){
                return;
            }
            if(!inputValidation.isInputEditTextField(contactnum, "contactnum")){
                return;
            }
            i = new Intent(activity, Register2.class);
            i.putExtra("Firstname", firstname.getText().toString().trim());
            i.putExtra("Lastname", lastname.getText().toString().trim());
            i.putExtra("Contact", contactnum.getText().toString().trim());
            startActivity(i);
        }
    }
}
