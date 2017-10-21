package nolambda.kommon.compoundview

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

typealias ViewBinder = (View) -> Unit

fun CompoundView.setup(context: Context, attributeSet: AttributeSet, viewBinder: ViewBinder? = null) {
    val parent = this as ViewGroup
    View.inflate(context, getLayoutResId(), parent)
    if (!parent.isInEditMode) {
        viewBinder?.invoke(parent)
    }
    init(context, attributeSet)
}

fun AttributeSet.extract(context: Context, @StyleableRes resId: IntArray, block: TypedArray.() -> Unit) {
    val array = context.obtainStyledAttributes(this, resId, 0, 0)
    block(array)
    array.recycle()
}
