package nolambda.kommonadapter.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import nolambda.kommonadapter.simple.SimpleAdapter
import nolambda.kommonadapter.simple.ViewHolder
import nolambda.kommonadapter.simple.ViewHolderBinder
import kotlin.reflect.KClass

inline fun <reified T : Any, VB : ViewBinding> SimpleAdapter.DelegateBuilder<Any>.map(
    noinline viewBindingCreator: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    @Suppress("UNUSED_PARAMETER")
    classPredicate: KClass<T>? = null, // optional for syntax purpose
    noinline binder: VB.(ViewHolder, T) -> Unit
) {
    val creator = ViewBindingCreator(viewBindingCreator)
    val finalBinder: ViewHolderBinder<Any> = { vh, item ->
        binder.invoke(vh.fetch<VB>()!!, vh, item as T)
    }

    map(creator.asViewCreator(), { _, i -> i is T }, null, finalBinder)
}
