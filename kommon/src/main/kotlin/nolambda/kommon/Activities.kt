package nolambda.kommon

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

fun Activity.toast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT): Unit = Toast.makeText(this, text, duration).show()

inline fun <reified T : Any> Activity.start(extras: Intent.() -> Unit) {
    val intent = intentFor<T>(this)
    intent.apply(extras)
    startActivity(intent)
}

inline fun <reified T : Any> Activity.start() {
    val intent = intentFor<T>(this)
    startActivity(intent)
}

inline fun <reified T : Any> Activity.startActivityForResult(requestCode: Int, options: Bundle? = null, action: String? = null) {
    startActivityForResult(intentFor<T>(this).setAction(action), requestCode, options)
}

inline fun <reified T : Any> String.toJson(clazz: Class<T>) {

}
