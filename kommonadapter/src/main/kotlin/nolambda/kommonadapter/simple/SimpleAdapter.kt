package nolambda.kommonadapter.simple

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nolambda.kommonadapter.multi.AdapterDelegate
import nolambda.kommonadapter.multi.MultiListAdapter

typealias ViewHolderBinder<T> = (vh: ViewHolder, item: T) -> Unit
typealias TypePredicate = (position: Int, item: Any) -> Boolean

class SimpleAdapter(private val context: Context) {

    fun create(builder: DelegateBuilder.() -> Unit): SimpleListAdapter {
        val delegates = DelegateBuilder().apply(builder).delegates
        val adapter = SimpleListAdapter(context)

        delegates.forEach {
            adapter.addDelegate(it)
        }

        return adapter
    }

    @Suppress("UNCHECKED_CAST")
    class DelegateBuilder {
        internal val delegates: MutableList<SimpleDelegate<Any>> by lazy { mutableListOf<SimpleDelegate<Any>>() }

        inline fun <reified T : Any> map(@LayoutRes layout: Int, noinline binder: ViewHolderBinder<T>) {
            map({ _, i -> i is T }, layout, binder)
        }

        fun <T : Any> map(typePredicate: TypePredicate, @LayoutRes layout: Int, binder: ViewHolderBinder<T>) {
            delegates.add(SimpleDelegate(
                    typePredicate = typePredicate,
                    binder = binder as ViewHolderBinder<Any>,
                    layoutRes = layout
            ))
        }
    }


    class SimpleDelegate<T>(private val typePredicate: TypePredicate,
                            private val binder: ViewHolderBinder<T>,
                            private val layoutRes: Int) : AdapterDelegate<Any>() {
        override fun isForType(position: Int, item: Any): Boolean = typePredicate(position, item)

        @Suppress("UNCHECKED_CAST")
        override fun onBind(vh: RecyclerView.ViewHolder, item: Any, position: Int) =
                binder(vh as @kotlin.ParameterName(name = "vh") ViewHolder, item as T)

        override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder =
                ViewHolder(inflater.inflate(layoutRes, parent, false))
    }

    class SimpleListAdapter(context: Context) : MultiListAdapter<Any>(context)
}
