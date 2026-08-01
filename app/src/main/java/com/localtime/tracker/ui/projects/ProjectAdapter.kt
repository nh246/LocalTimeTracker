package com.localtime.tracker.ui.projects

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.localtime.tracker.R
import com.localtime.tracker.data.Project

class ProjectAdapter(
    private val onPlayToggle: (Project) -> Unit,
    private val onClick: (Project) -> Unit
) : ListAdapter<Project, ProjectAdapter.VH>(DIFF) {

    // Which project currently has a running timer (null = none running)
    var runningProjectId: Long? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class VH(view: android.view.View) : RecyclerView.ViewHolder(view) {
        val play: android.widget.ImageButton = view.findViewById(R.id.btn_play)
        val name: android.widget.TextView = view.findViewById(R.id.text_name)
        val progress: android.widget.TextView = view.findViewById(R.id.text_progress)
        val percent: android.widget.TextView = view.findViewById(R.id.text_percent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_project, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val project = getItem(position)
        holder.name.text = project.name
        holder.name.setTextColor(runCatching { Color.parseColor(project.colorHex) }.getOrDefault(Color.WHITE))
        val budgetText = if (project.dailyBudgetMinutes > 0)
            "0h 00m / ${project.dailyBudgetMinutes / 60}h ${project.dailyBudgetMinutes % 60}m"
        else "No daily budget set"
        holder.progress.text = budgetText
        holder.percent.text = "0 %"

        val isRunning = runningProjectId == project.id
        holder.play.setImageResource(
            if (isRunning) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
        )
        holder.play.setOnClickListener { onPlayToggle(project) }
        holder.itemView.setOnClickListener { onClick(project) }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Project>() {
            override fun areItemsTheSame(a: Project, b: Project) = a.id == b.id
            override fun areContentsTheSame(a: Project, b: Project) = a == b
        }
    }
}
