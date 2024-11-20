package com.app.stepup.ui.components

import android.content.Context
import android.widget.Toast

fun showToast(context: Context, string: Int) {
    Toast.makeText(context, context.getString(string), Toast.LENGTH_SHORT).show()
}