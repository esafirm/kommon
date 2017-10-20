package nolambda.kommon

fun Int.loop(func: (Int) -> Unit) {
    for (i in 0 until this) {
        func(i)
    }
}

fun <T> unsafeLazy(initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)
