package nolambda.kommon.compoundview

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

abstract class RelativeCompoundView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs), CompoundView {
    init {
        setup(context, attrs)
    }
}
