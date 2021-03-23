package nolambda.kommonadapter

import nolambda.kommonadapter.simple.SimpleAdapter
import nolambda.kommonadapter.simple.ViewHolderCreator
import nolambda.kommonadapter.simple.ViewHolderBinder
import nolambda.kommonadapter.simple.ViewHolderUnBinder

@Suppress("UNCHECKED_CAST")
inline fun <reified CHILD> SimpleAdapter.DelegateBuilder<Any>.map(
    noinline viewCreator: ViewHolderCreator,
    noinline unBinder: ViewHolderUnBinder? = null,
    noinline binder: ViewHolderBinder<CHILD>
) {
    map(viewCreator, { _, i -> i is CHILD }, unBinder, binder as ViewHolderBinder<Any>)
}