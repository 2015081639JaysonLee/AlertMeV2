package com.leevicente.alertme;

import android.app.Activity;

import com.leevicente.alertme.BroadcastReceiver.MyBroadcastReceiver;
import com.leevicente.alertme.helpers.SessionManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    SessionManager session;
    TextView time;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        time = (TextView) findViewById(R.id.time);
        go = (Button) findViewById(R.id.button);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        seekBar.setMax(600);
        seekBar.setProgress(30);
        createNotificationChannel();

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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Sample_Channel", "Channel_1", importance);
            channel.setDescription("This is Channel 1");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void startTimer(View v){
        if(!isCounting){
        Log.i("Pressed", "pressed");
            seekBar.setEnabled(false);
            go.setText("Stop");
            isCounting = true;
            final int progress_holder = seekBar.getProgress();
            countDownTimer =  new CountDownTimer( progress_holder * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    tick((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    notif();

                    final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.horn);
                    mediaPlayer.setVolume(1000, 1000);
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        int count = 0;
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if(count < 10){
                                count++;
                                mp.seekTo(0);
                                mediaPlayer.setVolume(1000, 1000);
                                mp.start();
                            }
                            if(count == 9){
                                mp.release();
                                startTimer(null);
                            }
                        }

                    });
                    resetTimer(true, progress_holder);
                }
            }.start();
        }else{

            resetTimer(true, 30);

        }
    }

    public void notif(){
        Intent snoozeIntent = new Intent(this, MyBroadcastReceiver.class);
//                    snoozeIntent.setAction(Intent.ACTION_VIEW);
//
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "Sample_Channel")
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("AlertMe")
                .setContentText("Hey are you okay?")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
//                .addAction(R.drawable.ic_launcher_foreground, "Start again", snoozePendingIntent);

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, mBuilder.build());
    }


    public void resetTimer(boolean isended, int progress_holder){
        if (isended) {
            countDownTimer.cancel();
            isCounting = false;
            seekBar.setEnabled(true);
            seekBar.setProgress(progress_holder);
            tick(progress_holder);
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
        seekBar.setProgress(Integer.parseInt(secondsString));

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
                session.logoutUser();
//                Intent intent3 = new Intent(this, Login.class);
//                startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
