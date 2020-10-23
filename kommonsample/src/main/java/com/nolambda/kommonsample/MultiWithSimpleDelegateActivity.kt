package com.nolambda.kommonsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_header.*
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.multi.MultiListAdapter

class MultiWithSimpleDelegateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recycler = androidx.recyclerview.widget.RecyclerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        setContentView(recycler)

        val adapter = MultiListAdapter<Any>(this) {
            map<String>(R.layout.item_header) { vh, data ->
                vh.item_txt_header.text = "This is header ${vh.adapterPosition} with Data ${data}"
            }
        }

        recycler.attach(adapter = adapter)

        adapter.pushData(listOf("Test", "Test 1", "Test 2"))
    }
}
