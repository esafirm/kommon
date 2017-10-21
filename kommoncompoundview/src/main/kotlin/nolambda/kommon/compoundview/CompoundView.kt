package nolambda.kommon.compoundview

import android.content.Context
import android.support.annotation.LayoutRes
import android.util.AttributeSet

interface CompoundView {
    @LayoutRes
    fun getLayoutResId(): Int
    fun init(context: Context, attrs: AttributeSet)
}
