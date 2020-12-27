package nolambda.kommonadapter.multi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class AdapterDelegateManager<T> {

    companion object {
        private const val DEFAULT_VIEW_TYPE = 999
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

    internal fun onUnbind(vh: RecyclerView.ViewHolder, viewType: Int) {
        delegates[viewType].onUnbind(vh)
    }

    internal fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return try {
            delegates[viewType].onCreateViewHolder(inflater, parent)
        } catch (e: Exception) {
            if (e is IndexOutOfBoundsException) {
                throw IllegalStateException("""
                    Index out of bound, probably because there's no match adapter delegate"
                    View type: $viewType
                    Delegates: $delegates
                """.trimIndent(), e)
            } else throw e
        }
    }
}
