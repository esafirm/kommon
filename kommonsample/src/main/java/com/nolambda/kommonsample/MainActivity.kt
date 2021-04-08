package com.nolambda.kommonsample

import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.nolambda.kommonsample.adapter.AdapterSampleActivity
import com.nolambda.kommonsample.adapter.CallbackSample
import com.nolambda.kommonsample.adapter.MultiWithSimpleDelegateActivity
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

            addButton("Adapter Sample") {
                start<AdapterSampleActivity>()
            }

            addButton("Multi Adapter with Delegate Builder ") {
                start<MultiWithSimpleDelegateActivity>()
            }

            addButton("Callback Sample") {
                start<CallbackSample>()
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
