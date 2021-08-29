package com.mcbath.booksearch.utils

import android.view.View
import android.widget.EditText
import androidx.core.view.ViewCompat

class Utils {
    fun stringJoin(stringList: Array<String>, delimiter: String?): String {
        val sb = StringBuilder()
        for (i in stringList.indices) {
            sb.append(stringList[i])
            if (i != stringList.size - 1) {
                sb.append(delimiter)
            }
        }
        return sb.toString()
    }

    fun removeUnderline(view: View) {
        val paddingBottom = view.paddingBottom
        val paddingStart = ViewCompat.getPaddingStart(view)
        val paddingEnd = ViewCompat.getPaddingEnd(view)
        val paddingTop = view.paddingTop
        ViewCompat.setBackground(view, null)
        ViewCompat.setPaddingRelative(view, paddingStart, paddingTop, paddingEnd, paddingBottom)
    }
}