package com.leevicente.alertme;

import android.os.Bundle;
import com.leevicente.alertme.helpers.SessionManager;
import com.leevicente.alertme.helpers.SessionManager;
import com.leevicente.alertme.modal.User;
import com.leevicente.alertme.sql.DatabaseHelper;

import java.sql.Array;
import java.util.HashMap;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Settings extends AppCompatActivity {
    private final AppCompatActivity activity = Settings.this;
    private DatabaseHelper databaseHelper;
    private SessionManager session;

    private TextView fullname;
    private TextView contact;
    private TextView username;
    private TextView password;
    private TextView emerNum;
    private TextView emerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initSettings();
        initObjects();
        session.checkLogin();

        HashMap<String, String> user = session.getUserDetails();

        String name = user.get(SessionManager.KEY_NAME);

        Object userInfo =  databaseHelper.getUser(name).get(0);

        String fullname_to_put = ((User) userInfo).getFirstName() + " " + ((User) userInfo).getLastName();
        fullname.setText(fullname_to_put);

        String contact_to_put = ((User) userInfo).getEmerContact();
        contact.setText(contact_to_put);

        String username_to_put = ((User) userInfo).getUserName();
        username.setText(username_to_put);

        String password_to_put = ((User) userInfo).getPassword();
        password.setText(password_to_put);

        String emerName_to_put = ((User) userInfo).getEmerName();
        emerName.setText(emerName_to_put);

        String emerNum_to_put = ((User) userInfo).getEmerContact();
        emerNum.setText(emerNum_to_put);
    }

    private void initSettings(){
        fullname = findViewById(R.id.Fullname);
        contact = findViewById(R.id.Contact);
        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);
        emerName = findViewById(R.id.EmerName);
        emerNum = findViewById(R.id.EmerNum);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        session = new SessionManager(getApplicationContext());
    }
}
