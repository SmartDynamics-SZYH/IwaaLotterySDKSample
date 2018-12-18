package com.szyh.iwaarossdksample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.szyh.lotterysdk.sdk.ticket.RobotTicketApi;
import com.szyh.lotterysdk.sdk.ticket.interfaces.TicketOutResult;


public class TicketDemoActivity extends AppCompatActivity {

    private static final String TAG = "TicketDemoActivity";

    TextView versionTxt;
    TextView outTicketTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_demo);

        versionTxt = (TextView) findViewById(R.id.version_txt);
        outTicketTxt = (TextView) findViewById(R.id.out_ticket_txt);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            RobotTicketApi.get().unRegisterTicketOutCallback();
        }
    }

    public void outTickets(View view) {

        RobotTicketApi.get().triggerTicketOut(3 , new TicketOutResult(){

            @Override
            public void onTicketNum(int index){
                outTicketTxt.setText("正在出第 " + index + " 张票");
            }

            @Override
            public void onTicketSuccess(){
                outTicketTxt.setText("出票完成");
            }

            @Override
            public void onTicketError(int leftTickets, String errDes){
                outTicketTxt.setText("出票失败， 还剩 " + leftTickets + " 张票没出");
            }
        });
    }

    public void getVersion(View view){
        versionTxt.setText("正在查询分票器固件版本");
        String version = RobotTicketApi.get().queryVersion();

        if (!TextUtils.isEmpty(version)){
            versionTxt.setText(version);
        }
    }

    public void closePage(View view){
        finish();
    }

}
