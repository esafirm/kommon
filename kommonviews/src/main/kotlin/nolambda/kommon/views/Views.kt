package nolambda.kommon.views

import androidx.core.view.ViewCompat
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

fun <T : View> T.afterMeasured(f: T.() -> Unit) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            f()
            return false
        }
    })
}

fun View.removeFromParent() {
    if (parent is ViewGroup) {
        (parent as ViewGroup).removeView(this)
    }
}

fun View.disableTouch() {
    setOnTouchListener { _, _ -> true }
}

fun View.setVisible(isVisible: Boolean, animate: Boolean = false) {
    if (animate) {
        if (isVisible) {
            alpha = 0f
            setVisible(true)
        }
        ViewCompat.animate(this)
                .alpha(if (isVisible) 1f else 0f)
                .withEndAction {
                    if (!isVisible) {
                        setVisible(false)
                    }
                }
    } else {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
