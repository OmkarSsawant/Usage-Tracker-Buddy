package com.visiondev.usagetrackerbuddy.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.provider.Settings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.visiondev.usagetrackerbuddy.getDateString
import com.visiondev.usagetrackerbuddy.ui.data.AppUsage

class UsageTrackerFirebaseClient {
      private val ff:FirebaseFirestore = FirebaseFirestore.getInstance()
    /*instead use userID to understand who*/
    @SuppressLint("HardwareIds")
    fun getDeviceId(context:Context):String = Settings.Secure.getString(context.contentResolver,
        Settings.Secure.ANDROID_ID
    )

        fun createGroup(action:(String)->Unit){
                val groupId = java.util.UUID.randomUUID().toString()
                ff.collection(groupId)
                        .document(getDateString())
                    .set(emptyMap<String,String>())
                    .addOnCompleteListener {
                        action(groupId)
                    }
        }

    fun doesGroupExists(groupId:String,action:(Boolean)->Unit){
        ff.collection(groupId)
            .get().addOnSuccessListener { action(!it.isEmpty) }
            .addOnFailureListener{ action(false) }
    }

    fun updateUsageStats(context: Context,groupId:String,statsData:List<AppUsage>,isSuccessful:(Boolean)->Unit){
        ff.collection(groupId)
            .document(getDateString())
            .collection("stats")
            .document(getDeviceId(context))
            .set(mapOf("data" to statsData) )
            .addOnCompleteListener {
                isSuccessful(it.isSuccessful)
            }
    }
}