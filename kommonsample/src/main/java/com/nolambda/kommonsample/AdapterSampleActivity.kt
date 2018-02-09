package com.nolambda.kommonsample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_text.*
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.simple.SimpleAdapter

class AdapterSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recycler = RecyclerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        setContentView(recycler)

        SimpleAdapter(this).attach {
            map<Any>(
                    typePredicate = { pos, _ -> pos == 0 },
                    layout = R.layout.item_header,
                    binder = { vh, _ -> vh.item_txt_header.text = "Ini Header" }
            )
            map<String>(R.layout.item_text) { vh, item ->
                vh.item_txt.text = item
                vh.item_img.setImageDrawable(ColorDrawable(Color.BLACK))
            }
            map<Int>(R.layout.item_text) { vh, nomor ->
                vh.item_txt.text = "Nomor $nomor"
                vh.item_img.setImageDrawable(ColorDrawable(Color.RED))
            }
        }.also { bind(recycler, it) }
    }

    private fun bind(recycler: RecyclerView, adapter: SimpleAdapter.SimpleListAdapter) {
        recycler.attach(adapter = adapter)
        adapter.pushData(listOf("Something in the way", "You Moveeee", "You asking me, IDK", 1, "333"))
    }
}
