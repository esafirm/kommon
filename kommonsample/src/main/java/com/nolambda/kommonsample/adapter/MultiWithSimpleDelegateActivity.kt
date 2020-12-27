package com.nolambda.kommonsample.adapter

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.nolambda.kommonsample.databinding.ItemHeaderBinding
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.multi.MultiListAdapter
import nolambda.kommonadapter.viewbinding.map

class MultiWithSimpleDelegateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recycler = androidx.recyclerview.widget.RecyclerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        setContentView(recycler)

        val adapter = MultiListAdapter<Any>(this) {
            map(ItemHeaderBinding::inflate, String::class) { vh, data ->
                itemTxtHeader.text = "This is header ${vh.adapterPosition} with Data ${data}"
            }
        }

        recycler.attach(adapter = adapter)

        adapter.pushData(listOf("Test", "Test 1", "Test 2"))
    }
}