package com.esafirm.kommon

fun Int.loop(func: (Int) -> Unit) {
    for (i in 0 until this) {
        func(i)
    }
}
