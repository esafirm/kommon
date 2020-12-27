package nolambda.kommonadapter

import androidx.annotation.LayoutRes
import nolambda.kommonadapter.simple.SimpleAdapter
import nolambda.kommonadapter.simple.ViewCreator
import nolambda.kommonadapter.simple.ViewHolderBinder
import nolambda.kommonadapter.simple.ViewHolderUnBinder

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SimpleAdapter.DelegateBuilder<Any>.map(
    @LayoutRes layout: Int,
    noinline unBinder: ViewHolderUnBinder? = null,
    noinline binder: ViewHolderBinder<T>
) {
    val viewCreator: ViewCreator = { inflater, parent -> inflater.inflate(layout, parent, false) }
    map(viewCreator, { _, i -> i is T }, unBinder, binder as ViewHolderBinder<Any>)
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> SimpleAdapter.DelegateBuilder<Any>.map(
    @LayoutRes layout: Int,
    binderBuilder: (SimpleAdapter.BinderBuilder<T>) -> Unit
) {

    val builder = SimpleAdapter.BinderBuilder<T>().apply(binderBuilder)
    val binder = builder.binder
    val unBinder = builder.unBinder

    if (binder == null) {
        throw IllegalStateException("Binder must be implemented from BinderBuilder<T>.binder !!")
    }

    val viewCreator: ViewCreator = { inflater, parent -> inflater.inflate(layout, parent, false) }
    map(viewCreator, { _, i -> i is T }, unBinder, binder as ViewHolderBinder<Any>)
}