package com.esafirm.kommon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast

inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id) as T

fun Activity.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Unit
        = Toast.makeText(this, text, duration).show()

inline fun <reified T : Any> Activity.start(extras: Intent.() -> Unit) {
    val intent = IntentFor<T>(this)
    intent.apply(extras)
    startActivity(intent)
}

inline fun <reified T : Any> Activity.startActivityForResult(requestCode: Int, options: Bundle? = null, action: String? = null) {
    startActivityForResult(IntentFor<T>(this).setAction(action), requestCode, options)
}
