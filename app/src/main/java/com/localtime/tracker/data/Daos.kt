package com.localtime.tracker.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProjectDao {
    @Query("SELECT * FROM projects WHERE archived = 0 ORDER BY id ASC")
    fun getAll(): LiveData<List<Project>>

    @Query("SELECT * FROM projects WHERE id = :id")
    suspend fun getById(id: Long): Project?

    @Insert
    suspend fun insert(project: Project): Long

    @Update
    suspend fun update(project: Project)

    @Delete
    suspend fun delete(project: Project)
}

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY createdAt DESC")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE projectId = :projectId ORDER BY createdAt DESC")
    fun getForProject(projectId: Long): LiveData<List<Task>>

    @Insert
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}

@Dao
interface SessionDao {
    @Insert
    suspend fun insert(session: Session): Long

    @Query("SELECT * FROM sessions WHERE dateKey = :dateKey ORDER BY startTime ASC")
    fun getForDate(dateKey: String): LiveData<List<Session>>

    @Query("SELECT * FROM sessions ORDER BY startTime ASC")
    suspend fun getAllOnce(): List<Session>

    @Query("SELECT * FROM sessions WHERE projectId = :projectId ORDER BY startTime ASC")
    suspend fun getForProjectOnce(projectId: Long): List<Session>

    @Query("SELECT COALESCE(SUM(durationSeconds),0) FROM sessions WHERE projectId = :projectId AND dateKey = :dateKey")
    fun getTodaySecondsForProject(projectId: Long, dateKey: String): LiveData<Long>

    @Query("SELECT COALESCE(SUM(durationSeconds),0) FROM sessions WHERE projectId = :projectId")
    fun getTotalSecondsForProject(projectId: Long): LiveData<Long>

    @Delete
    suspend fun delete(session: Session)
}
