package com.localtime.tracker.data

import android.content.Context

class Repository(context: Context) {
    private val db = AppDatabase.getInstance(context)
    val projectDao = db.projectDao()
    val taskDao = db.taskDao()
    val sessionDao = db.sessionDao()
}
