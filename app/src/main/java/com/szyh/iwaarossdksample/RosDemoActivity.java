package com.szyh.iwaarossdksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.szyh.lotterysdk.coreservice.smt.bean.BatteryInfo;
import com.szyh.lotterysdk.coreservice.smt.bean.BreathLightInfo;
import com.szyh.lotterysdk.coreservice.smt.bean.DeviceVersion;
import com.szyh.lotterysdk.coreservice.smt.bean.Head;
import com.szyh.lotterysdk.coreservice.smt.bean.MainBroadStatus;
import com.szyh.lotterysdk.coreservice.smt.bean.SensorInfo;
import com.szyh.lotterysdk.sdk.ros.RobotRosApi;
import com.szyh.lotterysdk.sdk.ros.interfaces.listener.BatteryInfoListener;
import com.szyh.lotterysdk.sdk.ros.interfaces.listener.BreathLightInfoListener;
import com.szyh.lotterysdk.sdk.ros.interfaces.listener.HeadInfoListener;
import com.szyh.lotterysdk.sdk.ros.interfaces.listener.MainBroadStatusListener;
import com.szyh.lotterysdk.sdk.ros.interfaces.listener.SensorStatusListener;

public class RosDemoActivity extends AppCompatActivity implements BatteryInfoListener, MainBroadStatusListener,
        SeekBar.OnSeekBarChangeListener, BreathLightInfoListener, SensorStatusListener, HeadInfoListener{

    private static final String TAG = "RosDemoActivity";

    private SeekBar mLightRedValueSb;
    private SeekBar mLightBlueValueSb;
    private SeekBar mLightGreenValueSb;

    private TextView headAngle;
    private TextView headSpeed;

    private TextView batteryStatusTxt;
    private TextView batteryCurrentTxt;
    private TextView batteryVoltageTxt;
    private TextView batteryGasGaugeTxt;
    private TextView batteryTemperatureTxt;
    private TextView batteryGasGaugePercentTxt;

    private TextView redStatusTxt;
    private TextView sharkStatusTxt;
    private TextView doorStatusTxt;

    private BreathLightInfo mBreathLightInfo = new BreathLightInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ros_demo);

        headAngle = (TextView) findViewById(R.id.id_head_angle);
        headSpeed = (TextView) findViewById(R.id.id_head_speed);
        batteryStatusTxt =  findViewById(R.id.id_BatteryStatus);
        batteryCurrentTxt = findViewById(R.id.id_BatteryCurrent);
        batteryVoltageTxt = findViewById(R.id.id_BatteryVoltage);
        batteryGasGaugeTxt =  findViewById(R.id.id_BatteryGasGauge);
        batteryTemperatureTxt =  findViewById(R.id.id_BatteryTemperature);
        batteryGasGaugePercentTxt = findViewById(R.id.id_BatteryGasGaugePercent);
        redStatusTxt = findViewById(R.id.id_red_status);
        sharkStatusTxt = findViewById(R.id.id_shark_status);
        doorStatusTxt = findViewById(R.id.id_door_status);

        mLightRedValueSb = findViewById(R.id.id_mLightRedValueSb);
        mLightGreenValueSb = findViewById(R.id.id_mLightGreenValueSb);
        mLightBlueValueSb = findViewById(R.id.id_mLightBlueValueSb);

        mLightRedValueSb.setOnSeekBarChangeListener(this);
        mLightBlueValueSb.setOnSeekBarChangeListener(this);
        mLightGreenValueSb.setOnSeekBarChangeListener(this);
        RobotRosApi.get().addBreathLightInfoListener(this);
        RobotRosApi.get().addHeadInfoListener(this);
        RobotRosApi.get().addBatteryStatusListener(this);
        RobotRosApi.get().addSensorStatusListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            RobotRosApi.get().removeBreathLightInfoListener(this);
            RobotRosApi.get().removeHeadInfoListener(this);
            RobotRosApi.get().removeBatteryStatusListener(this);
            RobotRosApi.get().removeSensorStatusListener(this);
        }
    }

    public void queryMainVersion(View view) {
        DeviceVersion deviceVersion = RobotRosApi.get().queryMainBroadVersion(true);
        if (deviceVersion != null) {
            Toast.makeText(this, deviceVersion.toString(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "主控板版本号为空", Toast.LENGTH_SHORT).show();
        }
    }

    public void firstExpress(View view) {
        RobotRosApi.get().showExpressionById(0);
    }

    public void secondExpress(View view) {
        RobotRosApi.get().showExpressionById(1);
    }

    public void thirdExpress(View view) {
        RobotRosApi.get().showExpressionById(2);
    }

    public void fourthExpress(View view) {
        RobotRosApi.get().showExpressionById(3);
    }

    public void fifthExpress(View view) {
        RobotRosApi.get().showExpressionById(4);
    }

    public void sixthExpress(View view) {
        RobotRosApi.get().showExpressionById(5);
    }

    public void switchBreathLight(View view) {
        RobotRosApi.get().switchBreathingLightCtrl(!mBreathLightInfo.ismIsOpen());
    }

    public void headTurnLeft(View view){
        RobotRosApi.get().turnHeadTo(0);
    }

    public void headTurnRight(View view){
        RobotRosApi.get().turnHeadTo(180);
    }

    public void headTurnForward(View view){
        RobotRosApi.get().turnHeadTo(90);
    }

    public void openDoor(View view){
        RobotRosApi.get().switchElectLockCtrl(true);
    }

    @Override
    public void onBatteryInfoResult(BatteryInfo batteryInfo) {
        batteryStatusTxt.setText((1 == batteryInfo.getChargeStatus()) ?  "正在充电" :"正在放电");
        batteryCurrentTxt.setText("电池电流（mA）: " + batteryInfo.getmBatteryCurrent());
        batteryVoltageTxt.setText("电池电压（mV）: " + batteryInfo.getmBatteryVoltage());
        batteryGasGaugeTxt.setText("电池电量(mAH): " + batteryInfo.getmBatteryGasGauge());
        batteryTemperatureTxt.setText("电池温度（℃）: " + batteryInfo.getmBatteryTemperature());
        batteryGasGaugePercentTxt.setText("电池电量（%）: " + batteryInfo.getmBatteryGasGaugePercent());
    }

    @Override
    public void onMainBroadStatusResult(MainBroadStatus mainBroadStatus) {
        Log.i(TAG, "onMainBroadStatusResult: " + mainBroadStatus.toString());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int viewId = seekBar.getId();
        int value = seekBar.getProgress();

        switch (viewId){
            case R.id.id_mLightRedValueSb:
                RobotRosApi.get().setBreathingLightRGB(value, mBreathLightInfo.getmGreenValue(), mBreathLightInfo.getmBlueValue());
                break;
            case R.id.id_mLightGreenValueSb:
                RobotRosApi.get().setBreathingLightRGB(mBreathLightInfo.getmRedValue(), value, mBreathLightInfo.getmBlueValue());
                break;
            case R.id.id_mLightBlueValueSb:
                RobotRosApi.get().setBreathingLightRGB(mBreathLightInfo.getmRedValue(),mBreathLightInfo.getmGreenValue(), value);
                break;

             default:
                 break;
        }
    }

    public void closePage(View view){
        finish();
    }

    @Override
    public void onBreathLightInfoResult(BreathLightInfo breathLightInfo) {
        mBreathLightInfo.setmIsOpen(breathLightInfo.ismIsOpen());
        mBreathLightInfo.setmRedValue(breathLightInfo.getmRedValue());
        mBreathLightInfo.setmBlueValue(breathLightInfo.getmBlueValue());
        mBreathLightInfo.setmGreenValue(breathLightInfo.getmGreenValue());

        mLightRedValueSb.setProgress(breathLightInfo.getmRedValue());
        mLightGreenValueSb.setProgress(breathLightInfo.getmGreenValue());
        mLightBlueValueSb.setProgress(breathLightInfo.getmBlueValue());
    }

    @Override
    public void onSensorInfoResult(SensorInfo sensorInfo) {
        redStatusTxt.setText(1 == sensorInfo.getRedStatus() ? "检测到人体" : "未检测到人体");
        sharkStatusTxt.setText(1 == sensorInfo.getSharkStatus() ? "机器震动" : "机器未震动");
        doorStatusTxt.setText( 0 == sensorInfo.getDoorStatus() ? "仓门打开" : "仓门未打开");
    }

    @Override
    public void onHeadInfoResult(Head headInfo) {
        headAngle.setText("当前头部角度: " +  headInfo.getAngle());
        headSpeed.setText("头部运动速度:rpm: " +  headInfo.getSpeed());
    }

}
