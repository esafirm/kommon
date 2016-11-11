package com.esafirm.kommon

fun Int.loop(func: (Int) -> Unit) {
    for (i in 0..this - 1) {
        func(i)
    }
}
