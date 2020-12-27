package nolambda.kommonadapter.simple

import android.view.View

class ViewHolder(
    view: View
) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    private var state: Any? = null

    /**
     * This omit the need of extending [ViewHolder] for state or object association
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(stateCreator: () -> T): T {
        if (state == null) {
            state = stateCreator()
        }
        return state!! as T
    }
}
