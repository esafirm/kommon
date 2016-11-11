package com.esafirm.kommon

import android.app.Activity
import android.app.Fragment
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Toast
import android.support.v4.app.Fragment as SupportFragment

/* --------------------------------------------------- */
/* > Fragment */
/* --------------------------------------------------- */

fun Fragment.getDefaultSharedPreferences() = defaultSharePreferences(activity)

fun Fragment.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) = activity.toast(text, duration)

fun Fragment.finish() {
    finisActivity(activity)
}

/* --------------------------------------------------- */
/* > SupportFragment */
/* --------------------------------------------------- */

fun SupportFragment.getDefaultSharedPreferences() = defaultSharePreferences(activity)

fun SupportFragment.toast(text: CharSequence) = activity.toast(text)

fun SupportFragment.finish() {
    finisActivity(activity)
}

/* --------------------------------------------------- */
/* > Common */
/* --------------------------------------------------- */

fun finisActivity(activity: Activity) {
    activity.finish()
}

fun defaultSharePreferences(activity: Activity): SharedPreferences
        = PreferenceManager.getDefaultSharedPreferences(activity)
