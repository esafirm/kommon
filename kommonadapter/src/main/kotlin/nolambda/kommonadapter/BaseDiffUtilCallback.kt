package nolambda.kommonadapter

import android.support.v7.util.DiffUtil

typealias ValueComparison<T> = (T, T) -> Boolean

class DefaultValueComparison<T> : ValueComparison<T> {
    override fun invoke(p1: T, p2: T): Boolean {
        return p1 == p2
    }
}

class BaseDiffUtilCallback<out T>(
    private val old: List<T>,
    private val new: List<T>,
    private val areItemTheSame: ValueComparison<T> = DefaultValueComparison(),
    private val areContentTheSame: ValueComparison<T> = DefaultValueComparison()
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areItemTheSame(old[oldItemPosition], new[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        areContentTheSame(old[oldItemPosition], new[newItemPosition])
}
