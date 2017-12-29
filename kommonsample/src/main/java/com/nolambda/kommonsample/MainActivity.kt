package com.nolambda.kommonsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import nolambda.kommon.start
import nolambda.kommon.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.extras?.getString("test")?.let {
            toast("Extra Value $it")
        }

        setContentView(Button(this).apply {
            text = "Press Me"
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setOnClickListener {
                start<CompoundViewActivity> {
                    putExtra("test", "This is a value")
                }
            }
        })
    }
}
