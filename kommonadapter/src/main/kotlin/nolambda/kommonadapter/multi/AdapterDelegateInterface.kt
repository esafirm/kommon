package nolambda.kommonadapter.multi

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

interface AdapterDelegateInterface<in T> {
    fun isForType(position: Int, item: T): Boolean
    fun onBind(vh: RecyclerView.ViewHolder, item: T, position: Int)
    fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder
    fun onUnbind(vh: RecyclerView.ViewHolder)
}
