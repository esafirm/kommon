package com.nolambda.kommonsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CompoundViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view: SampleCompoundView = layoutInflater.inflate(R.layout.activity_compound, null) as SampleCompoundView
        intent.extras?.getString("test").let { view.text = it }
        setContentView(view)
    }
}
