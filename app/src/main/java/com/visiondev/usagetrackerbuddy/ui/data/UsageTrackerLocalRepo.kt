package com.visiondev.usagetrackerbuddy.ui.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UsageTrackerLocalRepo(context: Context) {

    private val sp:SharedPreferences by lazy {
        context.getSharedPreferences("usage-tracker-prefs",Context.MODE_PRIVATE)
    }

    fun getJoinedGroupIds():List<String>? = sp.getStringSet("joined_groups",null)?.toList()

    fun addGroupId(groupId:String)  = sp.edit(true){
        val prevGroups = getJoinedGroupIds() ?: emptyList()
        val newGroups = prevGroups.toMutableList().apply { add(groupId) }.toSet()
        putStringSet("joined_groups",newGroups)
    }


}