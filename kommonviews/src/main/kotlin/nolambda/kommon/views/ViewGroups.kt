package nolambda.kommon.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

val ViewGroup.views: List<View>
    get() = (0 until childCount).map { getChildAt(it) }

fun ViewGroup?.inflate(resId: Int, attachToRooT: Boolean = false): View {
    requireNotNull(this)
    val inflater = LayoutInflater.from(this!!.context)
    return inflater.inflate(resId, this, attachToRooT)
}
