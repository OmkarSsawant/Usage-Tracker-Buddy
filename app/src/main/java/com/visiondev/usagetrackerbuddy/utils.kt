package com.visiondev.usagetrackerbuddy

import com.google.type.DateTime
import java.text.SimpleDateFormat
import java.util.*


fun getDateString():String{
    val df = SimpleDateFormat("dd-MM-yyyy")
    return df.format(Date())
}
