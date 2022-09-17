package com.visiondev.usagetrackerbuddy.ui.data

import android.app.usage.UsageStats

data class AppUsage(val pkg:String,val totalTimeUsed:Long,val lastTimeUsed:Long)
