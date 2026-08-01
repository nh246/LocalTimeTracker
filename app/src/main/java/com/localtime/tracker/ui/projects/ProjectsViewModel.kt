package com.localtime.tracker.ui.projects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.localtime.tracker.data.Project
import com.localtime.tracker.data.Repository
import com.localtime.tracker.data.Session
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ProjectsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository(app)
    val projects: LiveData<List<Project>> = repo.projectDao.getAll()

    fun addProject(name: String, colorHex: String, dailyBudgetMinutes: Int) {
        viewModelScope.launch {
            repo.projectDao.insert(Project(name = name, colorHex = colorHex, dailyBudgetMinutes = dailyBudgetMinutes))
        }
    }

    // Records a finished timer session locally. Nothing leaves the device.
    fun logSession(projectId: Long, startMillis: Long, endMillis: Long) {
        viewModelScope.launch {
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val session = Session(
                projectId = projectId,
                startTime = startMillis,
                endTime = endMillis,
                durationSeconds = (endMillis - startMillis) / 1000,
                dateKey = fmt.format(Date(startMillis))
            )
            repo.sessionDao.insert(session)
        }
    }
}
