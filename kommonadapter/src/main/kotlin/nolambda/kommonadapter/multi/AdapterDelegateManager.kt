package nolambda.kommonadapter.multi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import java.util.*

class AdapterDelegateManager<T> {

    companion object {
        const private val DEFAULT_VIEW_TYPE = 999
    }

    private val delegates = ArrayList<AdapterDelegate<T>>()

    fun addDelegate(delegate: AdapterDelegate<T>) {
        delegates.add(delegate)
    }

    fun clear() {
        delegates.clear()
    }

    internal fun getItemViewType(position: Int, item: T): Int {
        return delegates.indices.find { delegates[it].isForType(position, item) }
                ?: DEFAULT_VIEW_TYPE
    }

    internal fun onBind(vh: RecyclerView.ViewHolder, item: T, position: Int, viewType: Int) {
        delegates[viewType].onBind(vh, item, position)
    }

    internal fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegates[viewType].onCreateViewHolder(inflater, parent)
    }
}
