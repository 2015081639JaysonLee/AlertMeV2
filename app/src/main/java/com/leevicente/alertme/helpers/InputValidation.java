package com.leevicente.alertme.helpers;

import android.content.Context;

import android.widget.EditText;

public class InputValidation {
    private Context context;

    public InputValidation(Context context){
        this.context = context;
    }

    public boolean isInputEditTextField(EditText textInputEditText, String message){
        String value = textInputEditText.getText().toString().trim();
        if(value.isEmpty()){
            textInputEditText.setError("Input in " + message + " is missing");
            return false;
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText textInputEditText1, EditText textInputEditText2) {
        String value1 = textInputEditText1.getText().toString().trim();
        String value2 = textInputEditText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            textInputEditText2.setError("Password mismatch");
            return false;
        }
        return true;
    }

}
