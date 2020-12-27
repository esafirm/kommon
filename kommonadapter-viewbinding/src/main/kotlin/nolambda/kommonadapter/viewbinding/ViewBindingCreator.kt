package nolambda.kommonadapter.viewbinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import nolambda.kommonadapter.simple.ViewCreator
import kotlin.experimental.ExperimentalTypeInference

class ViewBindingCreator<VB : ViewBinding> @UseExperimental(ExperimentalTypeInference::class) constructor(
    @BuilderInference private val creator: (LayoutInflater, ViewGroup?, Boolean) -> VB
) {
    lateinit var viewBinding: VB

    fun asViewCreator(): ViewCreator = { inflater, parent ->
        viewBinding = creator(inflater, parent, false)
        viewBinding.root
    }
}