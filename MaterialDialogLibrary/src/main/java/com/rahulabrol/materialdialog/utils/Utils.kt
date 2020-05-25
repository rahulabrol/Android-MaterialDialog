package com.rahulabrol.materialdialog.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat

/**
 * Created by Rahul Abrol on 25/5/20.
 */
fun View.remove() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun getColor(@NonNull context: Context?, @ColorRes id: Int): Int {
    return context?.let {
        ContextCompat.getColor(context, id)
    } ?: 0
}