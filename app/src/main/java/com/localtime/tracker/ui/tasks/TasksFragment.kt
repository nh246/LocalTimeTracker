package com.localtime.tracker.ui.tasks

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.localtime.tracker.R

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val vm: TasksViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TaskAdapter(
            onToggle = { vm.toggleDone(it) },
            onDelete = { vm.delete(it) }
        )
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_tasks)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        vm.tasks.observe(viewLifecycleOwner) { adapter.submitList(it) }

        view.findViewById<FloatingActionButton>(R.id.fab_add_task).setOnClickListener {
            val input = EditText(requireContext())
            input.hint = "Task title"
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("New task")
                .setView(input)
                .setPositiveButton("Add") { _, _ ->
                    val title = input.text.toString().trim()
                    // projectId = 0 placeholder here; wire up a project picker as needed.
                    if (title.isNotEmpty()) vm.addTask(0, title)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
