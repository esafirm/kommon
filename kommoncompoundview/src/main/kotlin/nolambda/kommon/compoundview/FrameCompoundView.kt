package nolambda.kommon.compoundview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

abstract class FrameCompoundView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CompoundView {
    protected val viewInfo = setup(context, attrs!!)
}
