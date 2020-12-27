package nolambda.kommonadapter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

typealias DiffUtilMaker<T> = () -> DiffUtil.ItemCallback<T>

abstract class BaseListAdapter<T>(
    context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val inflater: LayoutInflater = LayoutInflater.from(context)

    var diffUtilCallbackMaker: DiffUtilMaker<T> = { BaseDiffUtilItemCallback() }

    private val listDiffer by lazy {
        AsyncListDiffer<T>(this, diffUtilCallbackMaker())
    }

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

    override fun getItemCount(): Int = listDiffer.currentList.size

    @JvmOverloads
    fun pushData(data: List<T>) {
        listDiffer.submitList(data)
    }

    fun isEmpty() = itemCount == 0
    fun getItem(position: Int): T = listDiffer.currentList[position]
    fun getData(): List<T> = listDiffer.currentList
}
