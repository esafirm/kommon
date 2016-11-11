package com.esafirm.kommon

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager


fun Context.toggleComponent(componentClass: Class<*>, enable: Boolean) {
    val componentName = ComponentName(this, componentClass)
    packageManager.setComponentEnabledSetting(componentName, if (enable) PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    else PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
}

fun Context.isServiceRunning(serviceClass: Class<*>): Boolean {
    val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}
