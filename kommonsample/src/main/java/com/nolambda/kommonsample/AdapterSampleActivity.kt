package com.nolambda.kommonsample

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.item_header.*
import kotlinx.android.synthetic.main.item_text.*
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.simple.SimpleAdapter

data class SampleItem(
    val name: String,
    val value: Int
)

class AdapterSampleActivity : AppCompatActivity() {

    private var adapter: SimpleAdapter.SimpleListAdapter? = null

    private fun createLayoutParams(matchWidth: Boolean = true, matchHeight: Boolean = true) =
        ViewGroup.LayoutParams(
            when (matchWidth) {
                true -> ViewGroup.LayoutParams.MATCH_PARENT
                else -> ViewGroup.LayoutParams.WRAP_CONTENT
            },
            when (matchHeight) {
                true -> ViewGroup.LayoutParams.MATCH_PARENT
                else -> ViewGroup.LayoutParams.WRAP_CONTENT
            }
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recycler = RecyclerView(this).apply {
            layoutParams = createLayoutParams()
        }

        val linear = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = createLayoutParams()
            addView(EditText(context).apply {
                layoutParams = createLayoutParams(matchHeight = false)
                addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(q: Editable?) {
                        filter(q?.toString() ?: "")
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                })
            })
            addView(recycler)
        }

        setContentView(linear)

        adapter = SimpleAdapter(this).create {
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
        recycler.attach(adapter = adapter) { pos, _ ->
            Toast.makeText(applicationContext, "Pos: $pos", Toast.LENGTH_SHORT).show()
        }
        adapter.pushData(createList())
    }

    private fun filter(query: String) {
        adapter?.pushData(createList().filter {
            if (it is String) {
                it.startsWith(query, ignoreCase = true)
            } else {
                true
            }
        })
    }

    private fun createList() = listOf(
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
        ))
}
