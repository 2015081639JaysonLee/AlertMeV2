package com.leevicente.alertme;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Main extends AppCompatActivity {

    SeekBar seekBar;
    boolean ended = false;
    CountDownTimer countDownTimer;
    boolean isCounting = false;
    TextView time;
    Button go;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        time = (TextView) findViewById(R.id.time);
        go = (Button) findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tick(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startTimer(View v){

        Log.i("Pressed", "pressed");
        if (!isCounting) {
            seekBar.setEnabled(false);
            go.setText("Stop");
            isCounting = true;
            countDownTimer =  new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    tick((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.start();

                    resetTimer(true);

                }
            }.start();
        }else{

            resetTimer(true);

        }
    }

    public void resetTimer(boolean isended){
        if (isended) {
            countDownTimer.cancel();
            isCounting = false;
            seekBar.setEnabled(true);
            seekBar.setProgress(30);
            time.setText("0:30");
            go.setText("Start");
        }else{
            countDownTimer.cancel();
            isCounting = false;
            seekBar.setEnabled(true);
            go.setText("Start");

        }

    }

    public void tick(int totalSec){

        int minutes = totalSec / 60;
        int seconds = totalSec - minutes * 60;


        String secondsString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondsString = "0" + secondsString;
        }
        seekBar.setProgress(totalSec);

        time.setText(String.valueOf(minutes) + ":" + secondsString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                Intent intent = new Intent(this, About.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                Intent intent2 = new Intent(this, Settings.class);
                startActivity(intent2);
                return true;
            case R.id.logout:
                Intent intent3 = new Intent(this, Login.class);
                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
