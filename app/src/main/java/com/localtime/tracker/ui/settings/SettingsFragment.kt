package com.localtime.tracker.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.localtime.tracker.R
import com.localtime.tracker.data.Repository
import com.localtime.tracker.util.CsvExporter
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = Repository(requireContext())
        val status = view.findViewById<TextView>(R.id.text_status)

        // CSV export was "Premium" in the original app - unlocked for everyone here,
        // and it only ever writes to local device storage.
        view.findViewById<Button>(R.id.btn_export_csv).setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val sessions = repo.sessionDao.getAllOnce()
                val path = CsvExporter.exportSessions(requireContext(), sessions)
                status.text = "Exported to $path"
            }
        }

        view.findViewById<Button>(R.id.btn_export_backup).setOnClickListener {
            status.text = "Backup export: hook this up to your own JSON dump of Room tables if you want it."
        }
    }
}
