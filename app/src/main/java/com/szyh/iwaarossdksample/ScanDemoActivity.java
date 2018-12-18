package com.szyh.iwaarossdksample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.szyh.lotterysdk.sdk.ros.RobotRosApi;
import com.szyh.lotterysdk.sdk.scan.RobotScanCodeApi;
import com.szyh.lotterysdk.sdk.scan.interfaces.QRCoderListener;

public class ScanDemoActivity extends AppCompatActivity implements QRCoderListener{

    private static final String TAG = "ScanDemoActivity";

    private TextView versionTxt;

    private TextView codeTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_demo);

        versionTxt = (TextView) findViewById(R.id.version_txt);
        codeTxt = (TextView) findViewById(R.id.code);
        addQRCoderListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            RobotScanCodeApi.get().removeQRCoderListener(this);
        }
    }

    public void getVersion(View view) {
        versionTxt.setText(RobotScanCodeApi.get().queryVersion());
    }

    private void addQRCoderListener(){
        codeTxt.setText("开始扫码");
        RobotScanCodeApi.get().addQRCoderListener(this);
    }

    @Override
    public void onQRReceived(String code) {
        Log.i(TAG, " ScanDemoActivity onQRReceived code: " + code);
        RobotRosApi.get().sendTickEncodeReadFlag();
        codeTxt.setText(code);
    }

    public void closePage(View view){
        finish();
    }

}
