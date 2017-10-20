package com.esafirm.kommon

import android.os.Handler
import android.os.Looper

private val handler by lazy { Handler() }
private val mainThreadHandler by lazy { Handler(Looper.getMainLooper()) }

fun runAsync(action: () -> Unit) {
    Thread(Runnable(action)).start()
}

fun runOnUiThread(action: () -> Unit) {
    mainThreadHandler.post(action)
}

fun runDelayed(delayMillis: Long, action: () -> Unit) {
    handler.postDelayed(action, delayMillis)
}

fun runDelayedOnUiThread(delayMillis: Long, action: () -> Unit) {
    mainThreadHandler.postDelayed(action, delayMillis)
}
