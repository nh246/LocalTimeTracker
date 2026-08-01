package com.localtime.tracker.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.localtime.tracker.R
import com.localtime.tracker.data.Task

class TaskAdapter(
    private val onToggle: (Task) -> Unit,
    private val onDelete: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.VH>(DIFF) {

    class VH(v: android.view.View) : RecyclerView.ViewHolder(v) {
        val check: android.widget.CheckBox = v.findViewById(R.id.check_done)
        val title: android.widget.TextView = v.findViewById(R.id.text_title)
        val delete: android.widget.ImageButton = v.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val task = getItem(position)
        holder.title.text = task.title
        holder.check.setOnCheckedChangeListener(null)
        holder.check.isChecked = task.completed
        holder.title.paintFlags = if (task.completed)
            holder.title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else
            holder.title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        holder.check.setOnCheckedChangeListener { _, _ -> onToggle(task) }
        holder.delete.setOnClickListener { onDelete(task) }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(a: Task, b: Task) = a.id == b.id
            override fun areContentsTheSame(a: Task, b: Task) = a == b
        }
    }
}
