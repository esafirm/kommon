package nolambda.kommonadapter.simple

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import nolambda.kommonadapter.multi.AdapterDelegate
import nolambda.kommonadapter.multi.MultiListAdapter

typealias ViewHolderUnBinder = (vh: ViewHolder) -> Unit
typealias ViewHolderBinder<T> = (vh: ViewHolder, item: T) -> Unit
typealias TypePredicate<T> = (position: Int, item: T) -> Boolean

class SimpleAdapter(private val context: Context) {

    fun create(builder: DelegateBuilder<Any>.() -> Unit): SimpleListAdapter {
        val delegates = DelegateBuilder<Any>().apply(builder).delegates
        val adapter = SimpleListAdapter(context)

        delegates.forEach {
            adapter.addDelegate(it)
        }

        return adapter
    }

    @Suppress("UNCHECKED_CAST")
    class DelegateBuilder<T> {
        internal val delegates: MutableList<SimpleDelegate<T>> by lazy { mutableListOf<SimpleDelegate<T>>() }

        inline fun <reified CHILD : T> map(@LayoutRes layout: Int,
                                           noinline unBinder: ViewHolderUnBinder? = null,
                                           noinline binder: ViewHolderBinder<CHILD>) {
            map(layout, { _, i -> i is CHILD }, unBinder, binder as ViewHolderBinder<T>)
        }

        inline fun <reified CHILD : T> map(@LayoutRes layout: Int,
                                           binderBuilder: (BinderBuilder<CHILD>) -> Unit) {

            val builder = BinderBuilder<CHILD>().apply(binderBuilder)
            val binder = builder.binder
            val unBinder = builder.unBinder

            if (binder == null) {
                throw IllegalStateException("Binder must be implemented from BinderBuilder<T>.binder !!")
            }

            map(layout, { _, i -> i is CHILD }, unBinder, binder as ViewHolderBinder<T>)
        }

        fun map(@LayoutRes layout: Int,
                typePredicate: TypePredicate<T>,
                unBinder: ViewHolderUnBinder? = null,
                binder: ViewHolderBinder<T>) {
            delegates.add(SimpleDelegate(typePredicate, binder, unBinder, layout))
        }
    }

    class BinderBuilder<T> {
        var binder: ViewHolderBinder<T>? = null
        var unBinder: ViewHolderUnBinder? = null
            get() = { vh: ViewHolder -> field?.invoke(vh) }
    }

    class SimpleDelegate<in T>(private val typePredicate: TypePredicate<T>,
                               private val binder: ViewHolderBinder<T>,
                               private val unBinder: ViewHolderUnBinder?,
                               private val layoutRes: Int) : AdapterDelegate<T>() {

        override fun isForType(position: Int, item: T): Boolean = typePredicate(position, item)

        @Suppress("UNCHECKED_CAST")
        override fun onBind(vh: RecyclerView.ViewHolder, item: T, position: Int) =
                binder(vh as @kotlin.ParameterName(name = "vh") ViewHolder, item)

        override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder =
                ViewHolder(inflater.inflate(layoutRes, parent, false))

        override fun onUnbind(vh: RecyclerView.ViewHolder) {
            unBinder?.invoke(vh as ViewHolder)
        }
    }

    class SimpleListAdapter(context: Context) : MultiListAdapter<Any>(context)
}
