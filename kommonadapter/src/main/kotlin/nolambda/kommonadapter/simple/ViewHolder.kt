package nolambda.kommonadapter.simple

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
