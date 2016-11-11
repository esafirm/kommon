package com.esafirm.kommon

import android.app.Fragment
import android.content.Context
import android.view.View
import android.support.v4.app.Fragment as SupportFragment

fun Context.dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.sp(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()

fun Fragment.dp(value: Int): Int = activity.dp(value)

fun Fragment.sp(value: Int): Int = activity.sp(value)

fun SupportFragment.dp(value: Int): Int = activity.dp(value)

fun SupportFragment.sp(value: Int): Int = activity.sp(value)

fun View.dp(value: Int): Int = context.dp(value)

fun View.sp(value: Int): Int = context.sp(value)
