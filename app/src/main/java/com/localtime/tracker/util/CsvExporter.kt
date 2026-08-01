package com.localtime.tracker.util

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.localtime.tracker.data.Session
import java.text.SimpleDateFormat
import java.util.*

/**
 * Exports session history to a CSV file in the device's local Downloads folder.
 * No network calls, no remote upload - this is a free feature in this build
 * (it was paywalled as "Premium" in the original app).
 */
object CsvExporter {

    fun exportSessions(context: Context, sessions: List<Session>): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val header = "project_id,task_id,start_time,end_time,duration_seconds,date\n"
        val rows = sessions.joinToString("\n") { s ->
            "${s.projectId},${s.taskId ?: ""},${sdf.format(Date(s.startTime))},${sdf.format(Date(s.endTime))},${s.durationSeconds},${s.dateKey}"
        }
        val csv = header + rows
        val filename = "focus_ledger_export_${System.currentTimeMillis()}.csv"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = context.contentResolver
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            uri?.let {
                resolver.openOutputStream(it)?.use { out -> out.write(csv.toByteArray()) }
            }
            return "Downloads/$filename"
        } else {
            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = java.io.File(dir, filename)
            file.writeText(csv)
            return file.absolutePath
        }
    }
}
