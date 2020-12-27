package nolambda.kommonadapter.multi

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

interface AdapterDelegateInterface<in T> {
    fun isForType(position: Int, item: T): Boolean
    fun onBind(vh: androidx.recyclerview.widget.RecyclerView.ViewHolder, item: T, position: Int)
    fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder
    fun onUnbind(vh: androidx.recyclerview.widget.RecyclerView.ViewHolder)
}
