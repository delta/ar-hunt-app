package edu.nitt.delta.orientation22.compose

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence)
{
Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}