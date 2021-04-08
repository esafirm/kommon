package com.nolambda.kommonsample

import android.app.Activity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

fun Activity.createRecycler(param: ViewGroup.LayoutParams = createLayoutParams()): RecyclerView {
    return RecyclerView(this).apply {
        layoutParams = param
    }
}

fun createLayoutParams(matchWidth: Boolean = true, matchHeight: Boolean = true) =
    ViewGroup.LayoutParams(
        when (matchWidth) {
            true -> ViewGroup.LayoutParams.MATCH_PARENT
            else -> ViewGroup.LayoutParams.WRAP_CONTENT
        },
        when (matchHeight) {
            true -> ViewGroup.LayoutParams.MATCH_PARENT
            else -> ViewGroup.LayoutParams.WRAP_CONTENT
        }
    )

fun Activity.row(
    params: ViewGroup.LayoutParams = createLayoutParams(),
    child: LinearLayout.() -> Unit
) {
    val container = LinearLayout(this).apply {
        orientation = LinearLayout.VERTICAL
        layoutParams = params
        child()
    }
    setContentView(container)
}

fun ViewGroup.button(
    text: String,
    params: ViewGroup.LayoutParams = createLayoutParams(matchHeight = false),
    onClick: () -> Unit
) {
    addView(Button(context).apply {
        layoutParams = params
        setText(text)
        setOnClickListener {
            onClick()
        }
    })
}