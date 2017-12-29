package nolambda.kommon.compoundview

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

typealias ViewInfo = Pair<Context, AttributeSet>

fun CompoundView.setup(context: Context, attributeSet: AttributeSet): ViewInfo {
    val parent = this as ViewGroup
    View.inflate(context, getLayoutResId(), parent)
    return context to attributeSet
}

fun ViewInfo.extract(@StyleableRes resId: IntArray, block: TypedArray.() -> Unit) {
    val context: Context = first
    val attrs: AttributeSet = second
    val array = context.obtainStyledAttributes(attrs, resId, 0, 0)
    block(array)
    array.recycle()
}
