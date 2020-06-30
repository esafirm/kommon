package nolambda.kommonadapter

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import java.util.*

typealias DiffUtilMaker<T> = (List<T>, List<T>) -> DiffUtil.Callback

abstract class BaseListAdapter<T>(
    context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = ArrayList<T>()
    protected val inflater: LayoutInflater = LayoutInflater.from(context)

    var diffUtilCallbackMaker: DiffUtilMaker<T> = { o, n -> BaseDiffUtilCallback(o, n) }

    var onItemClickListener: ((Int) -> Unit)? = null
    var onLongItemClickListener: ((Int) -> Boolean)? = null
    var onBottomReachedListener: (() -> Unit)? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener {
                val finalPosition = holder.adapterPosition
                if (finalPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(finalPosition)
                }
            }
        }

        onLongItemClickListener?.let { onLongClick ->
            holder.itemView.setOnLongClickListener {
                val finalPosition = holder.adapterPosition
                if (finalPosition != RecyclerView.NO_POSITION) {
                    onLongClick.invoke(finalPosition)
                } else false
            }
        }

        onBind(holder, position)

        if (isBottom(holder)) {
            onBottomReachedListener?.invoke()
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        onUnbind(holder, holder.adapterPosition)
    }

    protected fun isBottom(viewHolder: RecyclerView.ViewHolder) =
        viewHolder.adapterPosition == itemCount - 1

    protected abstract fun onUnbind(holder: RecyclerView.ViewHolder, position: Int)
    protected abstract fun onBind(holder: RecyclerView.ViewHolder, position: Int)

    override fun getItemCount(): Int = data.size

    @JvmOverloads
    fun pushData(data: List<T>, useDiffUtil: Boolean = true) {
        if (useDiffUtil) {
            DiffUtil.calculateDiff(diffUtilCallbackMaker(this.data, data))
                .also {
                    setData(data)
                    it.dispatchUpdatesTo(this)
                }
        } else {
            setData(data)
            notifyDataSetChanged()
        }
    }

    private fun setData(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
    }

    fun isEmpty() = itemCount == 0
    fun getItem(position: Int): T = data[position]
    fun getData(): List<T> = data
}
