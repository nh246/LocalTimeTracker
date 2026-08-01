package com.localtime.tracker.ui.sessions

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.localtime.tracker.R
import com.localtime.tracker.data.Repository
import java.text.SimpleDateFormat
import java.util.*

class SessionsFragment : Fragment(R.layout.fragment_sessions) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = Repository(requireContext())
        val adapter = SessionAdapter()
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_sessions)
        val empty = view.findViewById<TextView>(R.id.text_empty)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        val todayKey = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        repo.sessionDao.getForDate(todayKey).observe(viewLifecycleOwner, Observer { list ->
            adapter.submitList(list)
            empty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            recycler.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        })
    }
}
