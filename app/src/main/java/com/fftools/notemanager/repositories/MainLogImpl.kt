package com.fftools.notemanager.repositories

import android.util.Log
import javax.inject.Inject

class MainLogImpl @Inject constructor() : MainLog {
    override fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable?) {
        if (throwable != null) {
            Log.e(tag, msg, throwable)
        } else {
            Log.e(tag, msg)
        }
    }

    override fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }
}