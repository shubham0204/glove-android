package com.ml.shubham0204.glove_android

import android.content.Context
import androidx.startup.Initializer
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform

/**
 * A startup-initializer which instantiates the Python runtime (with Chaquopy) to run the Python
 * script kept in `src/main/python`.
 */
class PythonInitializer : Initializer<Python> {

    override fun create(context: Context): Python {
        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(context))
        }
        return Python.getInstance()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
