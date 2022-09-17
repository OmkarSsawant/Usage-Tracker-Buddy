package com.visiondev.usagetrackerbuddy.ui

import android.app.usage.UsageStatsManager
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.provider.Settings
import android.provider.Settings.Secure.ANDROID_ID
import android.util.Log
import com.visiondev.usagetrackerbuddy.ui.data.AppUsage
import java.util.*

class UsageTrackerClient (private val context: Context) {
  private val usageStatsManager : UsageStatsManager  = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    fun getUsages():List<AppUsage>{
        val update = mutableListOf<AppUsage>()
        val today = Date()
        today.hours = 0
        today.minutes = 0
        today.seconds = 1
        for( (pkg,usageStat) in usageStatsManager.queryAndAggregateUsageStats(today.time,System.currentTimeMillis()))
        {
            update.add(AppUsage(pkg, usageStat.totalTimeInForeground, usageStat.lastTimeUsed))
        }



        Log.i("Omkar", "getUsages: ${update.size}")
        return  update
    }
}