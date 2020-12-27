package nolambda.kommonadapter.multi

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import nolambda.kommonadapter.BaseListAdapter
import nolambda.kommonadapter.simple.SimpleAdapter

open class MultiListAdapter<T>(context: Context) : BaseListAdapter<T>(context) {

    constructor(context: Context, builder: SimpleAdapter.DelegateBuilder<T>.() -> Unit) : this(context) {
        addDelegates(builder)
    }

    private val delegateManager = AdapterDelegateManager<T>()

    fun addDelegate(delegate: AdapterDelegate<T>) {
        delegateManager.addDelegate(delegate)
    }

    fun addDelegates(builder: SimpleAdapter.DelegateBuilder<T>.() -> Unit) {
        val delegateBuilder = SimpleAdapter.DelegateBuilder<T>().apply(builder)
        for (delegate in delegateBuilder.delegates) {
            delegateManager.addDelegate(delegate as AdapterDelegate<T>)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return delegateManager.getItemViewType(position, getItem(position))
    }

    override fun onBind(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onBind(holder, getItem(position), position, holder.itemViewType)
    }

    override fun onUnbind(holder: RecyclerView.ViewHolder, position: Int) {
        delegateManager.onUnbind(holder, holder.itemViewType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateManager.onCreateViewHolder(inflater, parent, viewType)
    }
}
