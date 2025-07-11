package com.devyd.settings.common


import android.widget.AutoCompleteTextView

fun AutoCompleteTextView.setWidthByLongestItem(items: List<String>) {

    val paint = paint
    val maxTextPx = items.maxOfOrNull { paint.measureText(it) } ?: 300f
    val totalPx = maxTextPx + paddingLeft * 3 + paddingRight * 3
    val lp = layoutParams
    lp.width = totalPx.toInt()
    layoutParams = lp

    requestLayout()
}