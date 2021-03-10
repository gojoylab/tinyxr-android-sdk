# Gojoylab TinyXR SDK for Android

Use Android SDK build apps work with gojoylab glass.

Copyright (c) 2020 Gojoylab Inc. All rights reserved.

## Downloads

```
git clone git@github.com:gojoylab/tinyxr-android-sdk.git
```

## Getting Started

Open Android Unity and import an Android project.

## Demos

In this SDK, we include one demo

- glasssuite (for glass device control)

Function | Description
---|---
void enableSideBySideMode() | set device into 3D mode. In 2D mode, left/right eye show the same content. Display resolution is 1920x1080. In 3D mode (i.e. side by side mode), the display resolution is 3840x1080, left eye show left part of framebuffer, right eye show right part of framebuffer. Left eye resolution is 1920x1080, right eye resolution is 1920x1080
void disableSideBySideMode() | close 3D mode, the device back to 2D mode.
void turnOnGlassScreen() | turn on glass display
void turnOffGlassScreen() | turn off glass display
void openSensor() | enable glass sensor
void closeSensor() | disable glass sensor
void saveSettings() | save current config to glass
void getFirmwareVersion() | get glass firmware version
void setLuminanceMode(byte luminanceMode) | set the glass display luminance 1 - lum120, 2 - lum300, 0 - lum500, 3 - lum1500, 4 - lum3000

# **IMU data orders**
Host device like windows PC can use raw data of imu to do sensor fusion(6-axis or 9-axis). Or just use quaternion provided by ST fusion algorithm running on STM32.

The directions of accelerometer sensor coordinates are as below, 
- acc_data[0] - X 
- acc_data[1] - Y 
- acc_data[2] - Z
```
                            
                                                   ^  Z
                                                  /
                                                 / 
                                       <--------/
                                     X          |
                                                |
                                                v
                                                  Y
                                    Left-handed coordinate
```


For gyroscope, the data order is 
- gyro_data[0] - pitch
- gyro_data[1] - yaw
- gyro_data[2] - roll

For magnetometer, the data order is
- mag_data[0] - north and south strength
- mag_data[1] - up and down strength
- mag_data[2] - east and west strength

For pose, the data order is
- quaternion_6X[0] - x
- quaternion_6X[1] - y
- quaternion_6X[2] - z
- quaternion_6X[3] - w

And the quaternion has a 90-degree rotation about X-axis when HY-PCBA-02 is placed horizontally.
```
                                    Z     
                                    ^  ^ Y
                                    | /
                                    |/ 
                                    /--------> X
                                    
                                    Right-handed coordinate
```

## Firmware
To make SDK work well, please update lastest firmware to GP01 board: https://github.com/gojoylab/tinyxr-firmware-download
