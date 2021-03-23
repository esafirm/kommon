package nolambda.kommonadapter.simple

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nolambda.kommonadapter.BaseDiffUtilItemCallback
import nolambda.kommonadapter.DefaultValueComparison
import nolambda.kommonadapter.ValueComparison
import nolambda.kommonadapter.multi.AdapterDelegate
import nolambda.kommonadapter.multi.MultiListAdapter
import kotlin.experimental.ExperimentalTypeInference

typealias ViewHolderUnBinder = (vh: ViewHolder) -> Unit
typealias ViewHolderBinder<T> = (vh: ViewHolder, item: T) -> Unit
typealias TypePredicate<T> = (position: Int, item: T) -> Boolean
typealias ViewHolderCreator = (inflater: LayoutInflater, parent: ViewGroup?) -> ViewHolder

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
            diffUtilCallbackMaker = {
                val passedContentTheSame = result.areContentTheSame ?: DefaultValueComparison()
                val passedItemTheSame = result.areItemTheSame ?: DefaultValueComparison()

                BaseDiffUtilItemCallback(
                    areContentTheSame = passedContentTheSame,
                    areItemTheSame = passedItemTheSame
                )
            }
        }
        return this
    }

    @UseExperimental(ExperimentalTypeInference::class)
    @Suppress("UNCHECKED_CAST")
    class DelegateBuilder<T> {
        internal val delegates: MutableList<SimpleDelegate<T>> by lazy {
            mutableListOf<SimpleDelegate<T>>()
        }

        var areContentTheSame: ValueComparison<T>? = null
        var areItemTheSame: ValueComparison<T>? = null

        fun map(
            viewHolderCreator: ViewHolderCreator,
            typePredicate: TypePredicate<T>,
            unBinder: ViewHolderUnBinder? = null,
            binder: ViewHolderBinder<T>
        ) {
            delegates.add(SimpleDelegate(typePredicate, binder, unBinder, viewHolderCreator))
        }
    }

    class BinderBuilder<T> {
        var binder: ViewHolderBinder<T>? = null
        var unBinder: ViewHolderUnBinder? = null
            get() = { vh: ViewHolder -> field?.invoke(vh) }
    }

    class SimpleDelegate<in T>(
        private val typePredicate: TypePredicate<T>,
        private val binder: ViewHolderBinder<T>,
        private val unBinder: ViewHolderUnBinder?,
        private val creator: ViewHolderCreator
    ) : AdapterDelegate<T>() {

        override fun isForType(position: Int, item: T): Boolean = typePredicate(position, item)

        @Suppress("UNCHECKED_CAST")
        override fun onBind(vh: RecyclerView.ViewHolder, item: T, position: Int) =
            binder(vh as @ParameterName(name = "vh") ViewHolder, item)

        override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder =
            creator.invoke(inflater, parent)

        override fun onUnbind(vh: RecyclerView.ViewHolder) {
            unBinder?.invoke(vh as ViewHolder)
        }
    }
}
