package com.devyd.common.util

import android.util.Log
import com.devyd.common.BuildConfig


inline fun <reified T> T.logTag(): String {
    val tag = "Filter." + T::class.java.simpleName
    return tag
}

object LogUtil {
    private const val DEFAULT_TAG = "AppLog"

    /**
     * Debug log
     * @param tag Optional tag; defaults to class and method name
     * @param message The message to log
     */
    fun d(tag: String = defaultTag(), message: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message)

        }
    }

    /**
     * Info log
     */
    fun i(tag: String = defaultTag(), message: String) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message)
        }
    }

    /**
     * Warning log
     */
    fun w(tag: String = defaultTag(), message: String) {
        Log.w(tag, message)
    }

    /**
     * Error log
     */
    fun e(tag: String = defaultTag(), message: String, throwable: Throwable? = null) {
        Log.e(tag, message, throwable)
    }

    /**
     * Generate a default tag based on calling class and method
     */
    private fun defaultTag(): String {
        val stack = Throwable().stackTrace
        if (stack.size >= 3) {
            val element = stack[2]
            val className = element.fileName.substringBeforeLast(".kt")
            return "$className#${element.methodName}"
        }
        return DEFAULT_TAG
    }
}
