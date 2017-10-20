package nolambda.kommon

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.net.Uri
import java.io.File


inline fun <reified T : Any> intentFor(context: Context): Intent = Intent(context, T::class.java)

fun Intent.start(context: Context) = context.startActivity(this)

fun Intent.startForResult(activity: Activity, requestCode: Int) = activity.startActivityForResult(this, requestCode)

fun Intent.startForResult(fragment: Fragment, requestCode: Int) = fragment.startActivityForResult(this, requestCode)

fun Context.startIntentViewAction(data: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data)))
}

fun Context.startIntentShareFile(file: File, mimeType: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = mimeType
    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    startActivity(intent)
}

fun Context.startIntentDial(phoneNo: String) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNo, null))
    startActivity(intent)
}


