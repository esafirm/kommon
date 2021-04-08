package com.nolambda.kommonsample.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nolambda.kommonsample.createRecycler
import com.nolambda.kommonsample.databinding.ItemHeaderBinding
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.multi.MultiListAdapter
import nolambda.kommonadapter.viewbinding.map

class MultiWithSimpleDelegateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recycler = createRecycler()

        val adapter = MultiListAdapter<Any>(this) {
            map(ItemHeaderBinding::inflate, String::class) { vh, data ->
                itemTxtHeader.text = "This is header ${vh.adapterPosition} with Data ${data}"
            }
        }

        recycler.attach(adapter = adapter)

        adapter.pushData(listOf("Test", "Test 1", "Test 2"))
    }
}