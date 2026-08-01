package com.localtime.tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class Project(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val colorHex: String,          // e.g. "#2ECC71"
    val dailyBudgetMinutes: Int = 0, // 0 = no budget set
    val archived: Boolean = false
)

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val projectId: Long,
    val title: String,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val projectId: Long,
    val taskId: Long? = null,
    val startTime: Long,           // epoch millis
    val endTime: Long,             // epoch millis
    val durationSeconds: Long,
    val dateKey: String            // "yyyy-MM-dd" of session start, for fast day queries
)
