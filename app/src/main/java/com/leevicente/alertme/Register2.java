package com.leevicente.alertme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leevicente.alertme.helpers.InputValidation;
import com.leevicente.alertme.sql.DatabaseHelper;

public class Register2 extends AppCompatActivity {
    private final AppCompatActivity activity = Register2.this;
    private EditText emerContact;
    private EditText emerName;

    private Button backReg1;
    private Button nextReg2;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initReg2();
        initObjects();
    }

    private void initReg2(){
        emerContact = findViewById(R.id.emergencycontact);
        emerName =  findViewById(R.id.emergencyname);

        nextReg2 = findViewById(R.id.nextpage2);
        backReg1 = findViewById(R.id.backPage1);

    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    public void buttonClickRegister2(View v){
        Intent i;
        if (v.getId() == R.id.backPage1) {
            i = new Intent(this, Register.class);
            startActivity(i);
        } else if (v.getId() == R.id.nextpage2) {
            if(!inputValidation.isInputEditTextField(emerContact, "emergency contact num")){
                return;
            }
            if(!inputValidation.isInputEditTextField(emerName, "emergency contact name")){
                return;
            }

            i = new Intent(this, Register3.class);
            Bundle b = new Bundle();

            b.putString("Firstname", getIntent().getStringExtra(("Firstname")));
            b.putString("Lastname", getIntent().getStringExtra(("Lastname")));
            b.putString("Contact", getIntent().getStringExtra(("Contact")));
            b.putString("EmerContact", emerContact.getText().toString().trim());
            b.putString("EmerName", emerName.getText().toString().trim());
            i.putExtras(b);
            startActivity(i);
        }
    }
}
