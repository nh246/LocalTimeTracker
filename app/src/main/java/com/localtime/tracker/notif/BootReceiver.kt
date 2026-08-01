package com.localtime.tracker.notif

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * On reboot, re-schedule any local reminders the user had set (via AlarmScheduler).
 * Hook this up to read saved reminder times from Room/SharedPreferences once you add a
 * "set reminder" UI - kept minimal here since notifications aren't the paywalled feature.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Re-arm alarms here using AlarmScheduler once reminder times are persisted.
        }
    }
}
