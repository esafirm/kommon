package com.esafirm.kommon

import android.view.View

inline fun <reified T : View> View.find(id: Int): T = findViewById(id) as T
