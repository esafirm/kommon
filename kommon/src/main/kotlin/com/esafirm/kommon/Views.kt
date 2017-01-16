package com.esafirm.kommon

import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

inline fun <reified T : View> View.find(id: Int): T = findViewById(id) as T

fun <T : View> T.afterMeasured(f: T.() -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            f()
            return false
        }
    })
}

fun View.removeFromParent() {
    if (parent is ViewGroup) {
        (parent as ViewGroup).removeView(this)
    }
}
