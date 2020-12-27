package com.nolambda.kommonsample.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nolambda.kommonsample.R
import com.nolambda.kommonsample.databinding.ItemHeaderBinding
import com.nolambda.kommonsample.databinding.ItemTextBinding
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_text.*
import nolambda.kommonadapter.attach
import nolambda.kommonadapter.map
import nolambda.kommonadapter.simple.SimpleAdapter
import nolambda.kommonadapter.viewbinding.map

data class SampleItem(
    val name: String,
    val value: Int
)

data class AnotherSampleItem(
    val value: String
)

class AdapterSampleActivity : AppCompatActivity() {

    private var adapter: SimpleAdapter? = null

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
        val recycler = androidx.recyclerview.widget.RecyclerView(this).apply {
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
                viewCreator = { inflater, parent ->
                    ItemHeaderBinding.inflate(inflater, parent, false).root
                },
                typePredicate = { position, _ -> position == 0 },
                binder = { vh, _ ->
                    val binding = vh.get { ItemHeaderBinding.bind(vh.itemView) }
                    binding.itemTxtHeader.text = "Ini Header"
                }
            )

            map<String>(R.layout.item_text) { vh, item ->
                val binding = vh.get { ItemTextBinding.bind(vh.itemView) }
                binding.itemTxt.text = item
                binding.itemImg.setImageDrawable(ColorDrawable(Color.BLACK))
            }

            map<Int>(R.layout.item_text) { vh, number ->
                val container = vh.get { KotlinContainer(vh.itemView) }
                container.item_txt.text = "Nomor $number"
                container.item_img.setImageDrawable(ColorDrawable(Color.RED))
            }

            map(ItemTextBinding::inflate, AnotherSampleItem::class) { _, item ->
                itemTxt.text = item.value
                itemImg.setImageDrawable(ColorDrawable(Color.YELLOW))
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

                    val container = vh.get { KotlinContainer(vh.itemView) }

                    var counter = 0
                    container.item_txt.text = "Initial value: ${item.value}"
                    val text = container.item_txt
                    var runnable: Runnable? = null
                    runnable = Runnable {
                        counter += 1
                        text.text = "Counter $counter"
                        handler.postDelayed(runnable!!, 1000)
                    }
                    container.item_img.setImageDrawable(ColorDrawable(Color.GRAY))
                    handler.postDelayed(runnable, 1000)
                    log("Bind ${vh.adapterPosition}")

                    mapOfUnbind[vh.adapterPosition] = runnable
                }

                binderContainer.unBinder = {
                    val runnable = mapOfUnbind[it.adapterPosition]
                    if (runnable != null) {
                        handler.removeCallbacks(runnable)
                    }
                    mapOfUnbind.remove(it.adapterPosition)
                    log("Unbind ${it.adapterPosition}")
                }
            }

        }.also { bind(recycler, it) }
    }

    private fun log(message: String) {
        Log.d("ADAPTERSAMPLE", message)
    }

    private fun bind(recycler: androidx.recyclerview.widget.RecyclerView, adapter: SimpleAdapter) {
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
        "Test 1",
        "Test 2",
        "Test 3",
        "Test 4",
        1,
        2,
        3,
        AnotherSampleItem(
            value = "Testing"
        ),
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

class KotlinContainer(override val containerView: View?) : LayoutContainer
