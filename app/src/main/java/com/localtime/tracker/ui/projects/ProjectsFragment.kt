package com.localtime.tracker.ui.projects

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.recyclerview.widget.RecyclerView
import com.localtime.tracker.R
import com.localtime.tracker.data.Project

class ProjectsFragment : Fragment(R.layout.fragment_projects) {

    private val vm: ProjectsViewModel by viewModels()
    private lateinit var adapter: ProjectAdapter

    // Tracks the currently running timer's start time in memory (resets on process death;
    // for production you'd persist this via a foreground service, kept simple here).
    private var runningStartMillis: Long? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler_projects)
        adapter = ProjectAdapter(
            onPlayToggle = { project -> toggleTimer(project) },
            onClick = { /* open project detail / stats - hook up as needed */ }
        )
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        vm.projects.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        view.findViewById<FloatingActionButton>(R.id.fab_add_project).setOnClickListener {
            showAddProjectDialog()
        }
    }

    private fun toggleTimer(project: Project) {
        if (adapter.runningProjectId == project.id) {
            // stop -> log session locally
            val start = runningStartMillis
            if (start != null) {
                vm.logSession(project.id, start, System.currentTimeMillis())
            }
            adapter.runningProjectId = null
            runningStartMillis = null
        } else {
            adapter.runningProjectId = project.id
            runningStartMillis = System.currentTimeMillis()
        }
    }

    private fun showAddProjectDialog() {
        val input = EditText(requireContext())
        input.hint = "Project name"
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("New project")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isNotEmpty()) {
                    // Unlimited projects/tasks - no premium wall.
                    vm.addProject(name, randomColor(), 0)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun randomColor(): String {
        val palette = listOf("#2ECC71", "#9B59B6", "#E67E22", "#3498DB", "#E74C3C", "#F1C40F")
        return palette.random()
    }
}
