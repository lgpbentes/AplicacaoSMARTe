package com.example.lgpbentes.aplicacaosmarte;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Console;

public class AlarmeActivity extends AppCompatActivity implements View.OnClickListener {
    final static private long ONE_SECOND = 1000;
    final static private long TWENTY_SECONDS = ONE_SECOND * 10;
    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarme);
        setup();
        findViewById(R.id.the_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Clicado!", Toast.LENGTH_LONG).show();
        am.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() +
                TWENTY_SECONDS, pi );
    }

    private void setup() {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent i) {
                //Toast.makeText(c, "Rise and Shine!", Toast.LENGTH_LONG).show();
                // Get instance of Vibrator from current Context
                Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 300 milliseconds
                mVibrator.vibrate(300);
            }
        };
        registerReceiver(br, new IntentFilter("com.example.lgpbentes.aplicacaosmarte.TelaNotificacao") );
        pi = PendingIntent.getBroadcast( this, 0, new Intent("com.example.lgpbentes.aplicacaosmarte.TelaNotificacao"),
                0 );
        am = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
    }

    @Override
    protected void onDestroy() {
        am.cancel(pi);
        unregisterReceiver(br);
        super.onDestroy();
    }
}

