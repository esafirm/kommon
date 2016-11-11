package com.esafirm.kommon

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns

inline fun <reified T : Any> IntentFor(context: Context): Intent = Intent(context, T::class.java)

fun Intent.start(context: Context) = context.startActivity(this)

fun Intent.startForResult(activity: Activity, requestCode: Int) = activity.startActivityForResult(this, requestCode)

fun Intent.startForResult(fragment: Fragment, requestCode: Int) = fragment.startActivityForResult(this, requestCode)

fun WebIntent(url: String): Intent {
    return if (Patterns.WEB_URL.matcher(url).matches()) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
    } else throw IllegalArgumentException("Passed url: $url does not match URL pattern.")
}
