package com.yewanlong.ui.service.running;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.yewanlong.common.Constants;
import com.yewanlong.utils.SPUtils;

public class StepCounterService extends Service implements Runnable {

    public static Boolean FLAG = false;// 服务运行标志

    private SensorManager mSensorManager;// 传感器服务
    private StepDetector detector;// 传感器监听对象

    private PowerManager mPowerManager;// 电源管理服务
    private WakeLock mWakeLock;// 屏幕灯

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        StepDetector.CURRENT_SETP = (int)SPUtils.get(Constants.CURRENT_STEP,0);
        uploadPOIInfo();
        return super.onStartCommand(intent, flags, startId);
    }

    private void uploadPOIInfo() {
        new Thread(this).start();
    }

    @Override
    public void onDestroy() {
        FLAG = false;// 服务停止
        if (detector != null) {
            mSensorManager.unregisterListener(detector);
        }
        if (mWakeLock != null) {
            mWakeLock.release();
        }
        stopForeground(true);
        Intent intent = new Intent(Constants.SERVICE_TEMP);
        sendBroadcast(intent);
        super.onDestroy();
    }

    @Override
    public void run() {

        FLAG = true;// 标记为服务正在运行

        // 创建监听器类，实例化监听对象
        detector = new StepDetector(this);

        // 获取传感器的服务，初始化传感器
        mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        // 注册传感器，注册监听器
        mSensorManager.registerListener(detector,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);

        // 电源管理服务
        mPowerManager = (PowerManager) this
                .getSystemService(Context.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
        mWakeLock.acquire();
    }
}
