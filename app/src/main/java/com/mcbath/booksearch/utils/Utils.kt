package com.mcbath.booksearch.utils

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
}