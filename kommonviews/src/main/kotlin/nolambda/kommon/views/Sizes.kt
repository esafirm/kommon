package nolambda.kommon.views

import android.content.res.Resources
import androidx.fragment.app.Fragment as SupportFragment

val Int.dp
    get() = Resources.getSystem().displayMetrics.density * this

val Int.sp
    get() = Resources.getSystem().displayMetrics.scaledDensity * this
