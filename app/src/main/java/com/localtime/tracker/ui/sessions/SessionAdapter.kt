package com.localtime.tracker.ui.sessions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.localtime.tracker.R
import com.localtime.tracker.data.Session
import java.text.SimpleDateFormat
import java.util.*

class SessionAdapter : ListAdapter<Session, SessionAdapter.VH>(DIFF) {

    class VH(v: android.view.View) : RecyclerView.ViewHolder(v) {
        val date: android.widget.TextView = v.findViewById(R.id.text_date)
        val duration: android.widget.TextView = v.findViewById(R.id.text_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_session, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val s = getItem(position)
        val sdf = SimpleDateFormat("EEE, MMM d - HH:mm", Locale.US)
        holder.date.text = sdf.format(Date(s.startTime))
        val h = s.durationSeconds / 3600
        val m = (s.durationSeconds % 3600) / 60
        holder.duration.text = "${h}h ${m}m"
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Session>() {
            override fun areItemsTheSame(a: Session, b: Session) = a.id == b.id
            override fun areContentsTheSame(a: Session, b: Session) = a == b
        }
    }
}
