package com.szyh.iwaarossdksample;

import android.app.Application;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.szyh.iwaarossdksample.util.UiUtil;
import com.szyh.lotterysdk.sdk.mic.RobotMicApi;
import com.szyh.lotterysdk.sdk.ros.RobotRosApi;
import com.szyh.lotterysdk.sdk.ros.interfaces.InitListener;
import com.szyh.lotterysdk.sdk.scan.RobotScanCodeApi;
import com.szyh.lotterysdk.sdk.ticket.RobotTicketApi;

/**
 * author  ruanhouli
 * email   ruanhouli@szyh-smart.com
 * created 2018/11/13 17:31
 * remark  TODO
 */

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    public static boolean isInitSuccess = false;
    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        UiUtil.init(this);
        //初始化内存泄漏检测
        LeakCanary.install(App.getInstance());
        //初始化过度绘制检测
        BlockCanary.install(App.getInstance(), new BlockCanaryContext())
                .start();

        Logger.init("IwaaRosSDKSample");
        RobotRosApi.get().init(getApplicationContext(), new InitListener() {
            @Override
            public void onInit() {
                isInitSuccess = true;
                Log.i(TAG, "onInit: ");
            }

            @Override
            public void onReConnect() {
                isInitSuccess = true;
                Log.i(TAG, "onReConnect: ");
            }
        });


        RobotMicApi.get().init(getApplicationContext(), new InitListener() {
            @Override
            public void onInit() {
                Log.i(TAG, "RobotMicApi onInit: ");
            }

            @Override
            public void onReConnect() {
                Log.i(TAG, "RobotMicApi onReConnect: ");
            }
        });

        RobotTicketApi.get().init(getApplicationContext(), new InitListener() {
            @Override
            public void onInit() {
                Log.i(TAG, "RobotTicketApi onInit: ");
            }

            @Override
            public void onReConnect() {
                Log.i(TAG, "RobotTicketApi onReConnect: ");
            }
        });

        RobotScanCodeApi.get().init(getApplicationContext(), new InitListener() {
            @Override
            public void onInit() {
                Log.i(TAG, "RobotScanCodeApi onInit: ");
            }

            @Override
            public void onReConnect() {
                Log.i(TAG, "RobotScanCodeApi onReConnect: ");
            }
        });

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        RobotRosApi.get().destroy();
        RobotMicApi.get().destroy();
        RobotTicketApi.get().destroy();
        RobotScanCodeApi.get().destroy();
        isInitSuccess = false;
    }
}
