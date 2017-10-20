package com.esafirm.kommon

import android.view.View
import android.view.ViewGroup

val ViewGroup.views: List<View>
    get() = (0 until childCount).map { getChildAt(it) }
