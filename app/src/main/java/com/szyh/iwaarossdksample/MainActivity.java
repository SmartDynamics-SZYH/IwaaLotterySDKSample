package com.szyh.iwaarossdksample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void startRosActivity(View view) {
        gotoActivity(RosDemoActivity.class);
    }

    public void startTicketActivity(View view) {
        gotoActivity(TicketDemoActivity.class);
    }

    public void startMicActivity(View view) {
        gotoActivity(MicDemoActivity.class);
    }

    public void startScanActivity(View view) {
        gotoActivity(ScanDemoActivity.class);
    }

    private void gotoActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
