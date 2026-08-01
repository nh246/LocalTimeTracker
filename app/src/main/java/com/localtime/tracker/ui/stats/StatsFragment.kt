package com.localtime.tracker.ui.stats

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.localtime.tracker.R
import com.localtime.tracker.data.Repository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatsFragment : Fragment(R.layout.fragment_stats) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = Repository(requireContext())
        val chart = view.findViewById<BarChart>(R.id.chart_week)
        val totalText = view.findViewById<TextView>(R.id.text_week_total)
        val countText = view.findViewById<TextView>(R.id.text_sessions_count)

        // All stats/reports are free here - nothing is gated behind "premium".
        viewLifecycleOwner.lifecycleScope.launch {
            val all = repo.sessionDao.getAllOnce()
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val cal = Calendar.getInstance()
            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            var weekTotalSeconds = 0L

            for (i in 6 downTo 0) {
                val day = Calendar.getInstance()
                day.add(Calendar.DAY_OF_YEAR, -i)
                val key = fmt.format(day.time)
                val secondsForDay = all.filter { it.dateKey == key }.sumOf { it.durationSeconds }
                weekTotalSeconds += secondsForDay
                entries.add(BarEntry((6 - i).toFloat(), (secondsForDay / 60f)))
                labels.add(SimpleDateFormat("EEE", Locale.US).format(day.time))
            }

            val dataSet = BarDataSet(entries, "Minutes")
            chart.data = BarData(dataSet)
            chart.description.isEnabled = false
            chart.invalidate()

            val hours = weekTotalSeconds / 3600
            val mins = (weekTotalSeconds % 3600) / 60
            totalText.text = "${hours}h ${mins}m this week"
            countText.text = "${all.size} sessions total"
        }
    }
}
