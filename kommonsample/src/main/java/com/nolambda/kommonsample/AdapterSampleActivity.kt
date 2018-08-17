package com.nolambda.kommonsample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_text.*
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.simple.SimpleAdapter

data class SampleItem(
        val name: String,
        val value: Int
)

class AdapterSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recycler = RecyclerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }

        setContentView(recycler)

        SimpleAdapter(this).create {
            map(
                    typePredicate = { pos, _ -> pos == 0 },
                    layout = R.layout.item_header,
                    binder = { vh, _ -> vh.item_txt_header.text = "Ini Header" }
            )
            map<String>(R.layout.item_text) { vh, item ->
                vh.item_txt.text = item
                vh.item_img.setImageDrawable(ColorDrawable(Color.BLACK))
            }
            map<Int>(R.layout.item_text) { vh, number ->
                vh.item_txt.text = "Nomor $number"
                vh.item_img.setImageDrawable(ColorDrawable(Color.RED))
            }

            // In real app the post cycle should be cleaned up
            map<SampleItem>(R.layout.item_text) { binderContainer ->
                val handler = Handler(Looper.getMainLooper())
                val mapOfUnbind = mutableMapOf<Int, Runnable>()

                binderContainer.binder = { vh, item ->
                    if (mapOfUnbind.containsKey(vh.adapterPosition)) {
                        binderContainer.unBinder?.invoke(vh)
                        vh.itemView.background = ColorDrawable(Color.BLUE)
                    }

                    var counter = 0
                    vh.item_txt.text = "Initial value: ${item.value}"
                    val text = vh.item_txt
                    var runnable: Runnable? = null
                    runnable = Runnable {
                        counter += 1
                        text.text = "Counter $counter"
                        handler.postDelayed(runnable, 1000)
                    }
                    vh.item_img.setImageDrawable(ColorDrawable(Color.GRAY))
                    handler.postDelayed(runnable, 1000)
                    log("Bind ${vh.adapterPosition}")

                    mapOfUnbind[vh.adapterPosition] = runnable
                }

                binderContainer.unBinder = {
                    handler.removeCallbacks(mapOfUnbind[it.adapterPosition])
                    mapOfUnbind.remove(it.adapterPosition)
                    log("Unbind ${it.adapterPosition}")
                }
            }

        }.also { bind(recycler, it) }
    }

    private fun log(message: String) {
        Log.d("ADAPTERSAMPLE", message)
    }

    private fun bind(recycler: RecyclerView, adapter: SimpleAdapter.SimpleListAdapter) {
        recycler.attach(adapter = adapter)
        adapter.pushData(listOf(
                "Something in the way", "You Moveeee", "You asking me, IDK", 1, "333",
                "Something", "You Moeeee", "TEst Again, IDK", 2, "1333",
                SampleItem(
                        name = "First Item",
                        value = 1_000
                ),
                SampleItem(
                        name = "Second Item",
                        value = 1
                ),
                SampleItem(
                        name = "Third Item",
                        value = 2
                ),
                SampleItem(
                        name = "Third Item",
                        value = 3
                ),
                SampleItem(
                        name = "Third Item",
                        value = 4
                ),
                SampleItem(
                        name = "Third Item",
                        value = 5
                ),
                SampleItem(
                        name = "Third Item",
                        value = 6
                ),
                SampleItem(
                        name = "Third Item",
                        value = 7
                ),
                SampleItem(
                        name = "Third Item",
                        value = 8
                ),
                SampleItem(
                        name = "Third Item",
                        value = 9
                ),
                SampleItem(
                        name = "Third Item",
                        value = 10
                ),
                SampleItem(
                        name = "Third Item",
                        value = 11
                ),
                SampleItem(
                        name = "Third Item",
                        value = 12
                ),
                SampleItem(
                        name = "Third Item",
                        value = 13
                ),
                SampleItem(
                        name = "Third Item",
                        value = 14
                )))
    }
}
