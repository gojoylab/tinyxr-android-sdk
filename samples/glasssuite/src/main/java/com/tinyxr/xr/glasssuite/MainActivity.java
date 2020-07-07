/*
 * Copyright (c) 2020. GojoyLab Inc. All Rights Reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tinyxr.xr.glasssuite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tinyxr.xr.glassboard.Glassboard;
import com.tinyxr.xr.glassboard.GlassboardEventListener;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private String TAG = "GLASS_SUITE";
    private Glassboard mGlassDevice = null;
    private Context mContext;

    private static final int MSG_SENSOR_AVAILABLE = 0;
    private static final int MSG_VERSION_AVAILABLE = 2;
    private static final int MSG_MAKE_TOAST = 4;

    private class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;
        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();

            if (activity != null) {
                String[] data = (String[])(msg.obj);
                switch (msg.what){
                    case MSG_SENSOR_AVAILABLE:
                        ((TextView)(findViewById(R.id.acc_data))).setText(data[0]);
                        ((TextView)(findViewById(R.id.gyro_data))).setText(data[1]);
                        ((TextView)(findViewById(R.id.mag_data))).setText(data[2]);
                        break;

                    case MSG_VERSION_AVAILABLE:
                        ((TextView)(findViewById(R.id.response))).setText(data[0]);
                        mGlassDevice.openSensor();
                        break;

                    case MSG_MAKE_TOAST:
                        Toast.makeText(mContext, data[0], Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private final MyHandler mHandler = new MyHandler(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // This must be done to initialize Glass device
        mGlassDevice = new Glassboard(this);
        mGlassDevice.start();

        // Sensor data is get via GlassboardEventListener.
        mGlassDevice.registerGlassboardListener(new GlassboardEventListener() {
            @SuppressLint({"SetTextI18n", "DefaultLocale"})
            @Override
            public void onSensorChanged(float[] accSensorData, float[] gyroSensorData, float[] magSensorData, long time) {
                String[] msgData = new String[5];
                msgData[0] = String.format("%.2f", accSensorData[0]) + ", " + String.format("%.2f", accSensorData[1]) + ", " + String.format("%.2f", accSensorData[2]);
                msgData[1] = String.format("%.2f", gyroSensorData[0]) + ", " + String.format("%.2f", gyroSensorData[1]) + ", " + String.format("%.2f", gyroSensorData[2]);
                msgData[2] = String.format("%.2f", magSensorData[0]) + ", " + String.format("%.2f", magSensorData[1]) + ", " + String.format("%.2f", magSensorData[2]);

                Message msg = new Message();
                msg.obj = msgData;
                msg.what = MSG_SENSOR_AVAILABLE;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onCommandResp(byte cmd, int result, String extra) {
                String[] msgData = new String[1];
                Message msg = new Message();

                switch (cmd) {
                    case Glassboard.COMMAND_DEVICE_INFO:
                        msgData[0] = extra;
                        msg.what = MSG_VERSION_AVAILABLE;
                        break;

                    case Glassboard.COMMAND_SENSOR_DATA_ON:
                    case Glassboard.COMMAND_SENSOR_DATA_OFF:
                        msg.what = -1;
                        break;
                    case Glassboard.COMMAND_NOT_AVAILABLE:
                        msgData[0] = "Not Available";
                        msg.what = MSG_VERSION_AVAILABLE;
                        break;
                    default:
                        msgData[0] = "Receive RSP from command " + cmd;
                        msg.what = MSG_MAKE_TOAST;
                        break;
                }

                msg.obj = msgData;
                mHandler.sendMessage(msg);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGlassDevice.stop();
        mGlassDevice.unRegisterGlassboardListener();
    }

    public void onLumButtonClick(View view) {
        byte luminanceMode;

        switch (view.getId()) {
            case R.id.lum120:
                luminanceMode = 1;
                break;
            case R.id.lum300:
                luminanceMode = 2;
                break;
            case R.id.lum500:
                luminanceMode = 0;
                break;
            case R.id.lum1500:
                luminanceMode = 3;
                break;
            case R.id.lum3000:
                luminanceMode = 4;
                break;

            default:
                return;
        }

        mGlassDevice.setLuminanceMode(luminanceMode);
    }

    public void onMirrorModeButtonClick(View view) {
        mGlassDevice.disableSideBySideMode();
    }

    public void onSideBySideButtonClick(View view) {
        mGlassDevice.enableSideBySideMode();
    }

    public void onScreenOnButtonClick(View view) {
        mGlassDevice.turnOnGlassScreen();
    }

    public void onScreenOffButtonClick(View view) {
        mGlassDevice.turnOffGlassScreen();
    }

    public void onEnableUSBSensorsClick(View view) {
        mGlassDevice.openSensor();
    }

    public void onDisableUSBSensorsClick(View view) {
        mGlassDevice.closeSensor();
    }

    public void onSaveSettingsButtonClick(View view) {
        mGlassDevice.saveSettings();
    }

    public void onGetVersionClick(View view) {
        mGlassDevice.getFirmwareVersion();
    }
}
