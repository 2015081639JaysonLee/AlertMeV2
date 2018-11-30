package com.leevicente.alertme;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 550;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            
        @Override
                public void run() {
            Intent intent = new Intent(Splash.this, Login.class);
            Splash.this.startActivity(intent);
            Splash.this.finish();
        }
    }, SPLASH_DISPLAY_LENGTH);
}
}