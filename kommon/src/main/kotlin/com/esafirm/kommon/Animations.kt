package com.esafirm.kommon

import android.content.Context
import android.view.animation.AnimationUtils

fun Context.loadAnimation(id: Int) = AnimationUtils.loadAnimation(applicationContext, id)
