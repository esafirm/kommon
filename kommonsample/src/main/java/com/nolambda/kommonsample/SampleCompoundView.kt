package com.nolambda.kommonsample

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import nolambda.kommon.compoundview.LinearCompoundView
import nolambda.kommon.compoundview.bindView

class SampleCompoundView(context: Context, attrs: AttributeSet) : LinearCompoundView(context, attrs) {

    private val firstButton by bindView<View>(R.id.first)
    private val secondButton by bindView<View>(R.id.second)
    private val someButton by bindView<Button>(R.id.some)

    var text: String? = null
        set(value) {
            someButton.text = value
        }

    override fun getLayoutResId(): Int = R.layout.compound_view_sample

    init {
        orientation = LinearLayout.VERTICAL

        firstButton.setOnClickListener {
            Toast.makeText(context, "This is the first button", Toast.LENGTH_SHORT).show()
        }

        secondButton.setOnClickListener {
            Toast.makeText(context, "This is the second button", Toast.LENGTH_SHORT).show()
        }
    }
}
