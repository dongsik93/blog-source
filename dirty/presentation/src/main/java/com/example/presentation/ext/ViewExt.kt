package com.example.presentation.ext

import android.view.View

fun View?.visibilityExt(isVisibility: Boolean) {
    this?.visibility = if (isVisibility) View.VISIBLE else View.GONE
}