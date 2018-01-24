package nolambda.kommonadapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper

fun <T> RecyclerView.attach(
        layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
        adapter: BaseListAdapter<T>,
        itemDecoration: RecyclerView.ItemDecoration? = null,
        hasFixedSize: Boolean = false,
        snap: SnapHelper? = null,
        onItemClick: ((Int, T) -> Unit)? = null
) {
    setLayoutManager(layoutManager)
    setAdapter(adapter)
    setHasFixedSize(hasFixedSize)

    if (itemDecoration != null) {
        addItemDecoration(itemDecoration)
    }

    if (onItemClick != null) {
        requireNotNull(adapter)
        adapter.onItemClickListener = {
            onItemClick(it, adapter.getItem(it))
        }
    }

    snap?.attachToRecyclerView(this)
}
