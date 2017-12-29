package nolambda.kommon.compoundview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

abstract class LinearCompoundView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), CompoundView {
    protected val viewInfo = setup(context, attrs)
}
