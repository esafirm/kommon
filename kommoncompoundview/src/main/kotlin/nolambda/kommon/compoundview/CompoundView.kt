package nolambda.kommon.compoundview

import android.support.annotation.LayoutRes

interface CompoundView {
    @LayoutRes fun getLayoutResId(): Int
}
