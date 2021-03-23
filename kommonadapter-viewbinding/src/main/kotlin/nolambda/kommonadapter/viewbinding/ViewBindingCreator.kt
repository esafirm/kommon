package nolambda.kommonadapter.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import nolambda.kommonadapter.simple.ViewHolder
import nolambda.kommonadapter.simple.ViewHolderCreator
import kotlin.experimental.ExperimentalTypeInference

class ViewBindingCreator<VB : ViewBinding> @UseExperimental(ExperimentalTypeInference::class) constructor(
    @BuilderInference private val creator: (LayoutInflater, ViewGroup?, Boolean) -> VB
) {
    fun asViewCreator(): ViewHolderCreator = { inflater, parent ->
        val viewBinding = creator(inflater, parent, false)
        ViewHolder(viewBinding.root).apply {
            store(viewBinding)
        }
    }
}