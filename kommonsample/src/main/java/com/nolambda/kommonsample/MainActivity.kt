package com.nolambda.kommonsample

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
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

            addButton("Multi Adapter with Delegate Builder ") {
                start<MultiWithSimpleDelegateActivity>()
            }

            addView(ImageView(context).apply {
//                setImageDrawable(TickerDrawable())
                layoutParams = ViewGroup.LayoutParams(200, 200)
            })
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

    class TickerDrawable : Drawable() {

        override fun draw(canvas: Canvas) {
            canvas.drawColor(Color.RED)
        }

        override fun setAlpha(p0: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getOpacity(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setColorFilter(p0: ColorFilter?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
