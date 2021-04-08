package com.nolambda.kommonsample.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nolambda.kommonsample.button
import com.nolambda.kommonsample.createRecycler
import com.nolambda.kommonsample.databinding.ItemTextBinding
import com.nolambda.kommonsample.row
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.simple.SimpleAdapter
import nolambda.kommonadapter.viewbinding.map

class CallbackSample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recycler = createRecycler()
        val adapter = SimpleAdapter(this).create {
            map(ItemTextBinding::inflate, String::class) { _, item ->
                itemTxt.text = item
            }
        }

        recycler.attach(adapter = adapter)

        row {
            button("Randomize") {
                val items = (0..1000).toList().map { "Item no: $it" }.shuffled()
                adapter.pushData(items) {
                    recycler.scrollToPosition(0)
                }
            }
            addView(recycler)
        }

    }
}