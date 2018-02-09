package com.nolambda.kommonsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import nolambda.kommon.start
import nolambda.kommon.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getString("test")?.let {
            toast("Extra Value $it")
        }

        setContentView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addButton("Compound View Sample", onClick = {
                start<CompoundViewActivity> {
                    putExtra("test", "This is a value")
                }
            })

            addButton("Adapter Sample") {
                start<AdapterSampleActivity>()
            }
        })
    }

    private fun LinearLayout.addButton(name: String, onClick: () -> Unit) {
        Button(context).apply {
            text = name
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setOnClickListener {
                onClick()
            }
        }.also { addView(it) }
    }
}
