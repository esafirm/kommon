package nolambda.kommonadapter.multi

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import nolambda.kommonadapter.BaseListAdapter

abstract class MultiListAdapter<T>(context: Context) : BaseListAdapter<T>(context) {

    private val delegateManager = AdapterDelegateManager<T>()

    fun addDelegate(delegate: AdapterDelegate<T>) {
        delegateManager.addDelegate(delegate)
    }

    override fun getItemViewType(position: Int): Int {
        return delegateManager.getItemViewType(position, getItem(position))
    }

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBind(holder, getItem(position), position, getItemViewType(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(inflater, parent, viewType)
    }
}
