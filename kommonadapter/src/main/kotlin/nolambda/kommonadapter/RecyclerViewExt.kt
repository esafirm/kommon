package nolambda.kommonadapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.attach(
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    adapter: BaseListAdapter<T>,
    itemDecoration: RecyclerView.ItemDecoration? = null,
    hasFixedSize: Boolean = false,
    snap: androidx.recyclerview.widget.SnapHelper? = null,
    onBottomReached: (() -> Unit)? = null,
    onLongClickListener: ((Int, T) -> Boolean)? = null,
    onItemClick: ((Int, T) -> Unit)? = null
) {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
    setHasFixedSize(hasFixedSize)

    if (itemDecoration != null) {
        addItemDecoration(itemDecoration)
    }

    if (onItemClick != null) {
        adapter.onItemClickListener = {
            onItemClick(it, adapter.getItem(it))
        }
    }

    if (onLongClickListener != null) {
        adapter.onLongItemClickListener = {
            onLongClickListener.invoke(it, adapter.getItem(it))
        }
    }

    if (onBottomReached != null) {
        adapter.onBottomReachedListener = {
            onBottomReached()
        }
    }

    snap?.attachToRecyclerView(this)
}
