package nolambda.kommonadapter.simple

import android.content.Context
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import nolambda.kommonadapter.BaseDiffUtilItemCallback
import nolambda.kommonadapter.DefaultValueComparison
import nolambda.kommonadapter.ValueComparison
import nolambda.kommonadapter.multi.AdapterDelegate
import nolambda.kommonadapter.multi.MultiListAdapter

typealias ViewHolderUnBinder = (vh: ViewHolder) -> Unit
typealias ViewHolderBinder<T> = (vh: ViewHolder, item: T) -> Unit
typealias TypePredicate<T> = (position: Int, item: T) -> Boolean

open class SimpleAdapter(context: Context) : MultiListAdapter<Any>(context) {

    /**
     * @return the simple adapter itself to keep compatibility
     */
    fun create(builder: DelegateBuilder<Any>.() -> Unit): SimpleAdapter {
        val result = DelegateBuilder<Any>().apply(builder)
        val delegates = result.delegates

        delegates.forEach {
            addDelegate(it)
        }

        if (result.areContentTheSame != null || result.areItemTheSame != null) {
            diffUtilCallbackMaker = { old, new ->

                val passedContentTheSame = result.areContentTheSame ?: DefaultValueComparison()
                val passedItemTheSame = result.areItemTheSame ?: DefaultValueComparison()

                BaseDiffUtilItemCallback(
                    old = old,
                    new = new,
                    areContentTheSame = passedContentTheSame,
                    areItemTheSame = passedItemTheSame
                )
            }
        }
        return this
    }

    @Suppress("UNCHECKED_CAST")
    class DelegateBuilder<T> {
        internal val delegates: MutableList<SimpleDelegate<T>> by lazy {
            mutableListOf<SimpleDelegate<T>>()
        }

        var areContentTheSame: ValueComparison<T>? = null
        var areItemTheSame: ValueComparison<T>? = null

        inline fun <reified CHILD : T> map(
            @LayoutRes layout: Int,
            noinline unBinder: ViewHolderUnBinder? = null,
            noinline binder: ViewHolderBinder<CHILD>
        ) {
            map(layout, { _, i -> i is CHILD }, unBinder, binder as ViewHolderBinder<T>)
        }

        inline fun <reified CHILD : T> map(
            @LayoutRes layout: Int,
            binderBuilder: (BinderBuilder<CHILD>) -> Unit
        ) {

            val builder = BinderBuilder<CHILD>().apply(binderBuilder)
            val binder = builder.binder
            val unBinder = builder.unBinder

            if (binder == null) {
                throw IllegalStateException("Binder must be implemented from BinderBuilder<T>.binder !!")
            }

            map(layout, { _, i -> i is CHILD }, unBinder, binder as ViewHolderBinder<T>)
        }

        fun map(
            @LayoutRes layout: Int,
            typePredicate: TypePredicate<T>,
            unBinder: ViewHolderUnBinder? = null,
            binder: ViewHolderBinder<T>
        ) {
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
        override fun onBind(vh: androidx.recyclerview.widget.RecyclerView.ViewHolder, item: T, position: Int) =
            binder(vh as @ParameterName(name = "vh") ViewHolder, item)

        override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder =
            ViewHolder(inflater.inflate(layoutRes, parent, false))

        override fun onUnbind(vh: androidx.recyclerview.widget.RecyclerView.ViewHolder) {
            unBinder?.invoke(vh as ViewHolder)
        }
    }
}
