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
void startTracking() | start hmd Tracking
void stopTracking() | stop hmd Tracking
void getEulerAnglesInDegree(float[] eulerAngles) | get euler angles in degrees with the order of pitch, yaw, roll
