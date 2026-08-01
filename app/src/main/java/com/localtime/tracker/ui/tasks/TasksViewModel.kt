package com.localtime.tracker.ui.tasks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.localtime.tracker.data.Repository
import com.localtime.tracker.data.Task
import kotlinx.coroutines.launch

class TasksViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository(app)
    val tasks: LiveData<List<Task>> = repo.taskDao.getAll()

    // No cap here on purpose - original app capped free users at 3 tasks,
    // this build allows unlimited tasks for everyone.
    fun addTask(projectId: Long, title: String) {
        viewModelScope.launch {
            repo.taskDao.insert(Task(projectId = projectId, title = title))
        }
    }

    fun toggleDone(task: Task) {
        viewModelScope.launch { repo.taskDao.update(task.copy(completed = !task.completed)) }
    }

    fun delete(task: Task) {
        viewModelScope.launch { repo.taskDao.delete(task) }
    }
}
