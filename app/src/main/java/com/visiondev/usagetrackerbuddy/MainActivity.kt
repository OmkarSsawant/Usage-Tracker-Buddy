package com.visiondev.usagetrackerbuddy

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.visiondev.usagetrackerbuddy.ui.UsageTrackerClient
import com.visiondev.usagetrackerbuddy.ui.UsageTrackerFirebaseClient
import com.visiondev.usagetrackerbuddy.ui.screens.GroupEntryScreen
import com.visiondev.usagetrackerbuddy.ui.theme.UsageTrackerBuddyTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UsageTrackerBuddyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "group"){
                        composable("group"){
                            GroupEntryScreen(navController)
                        }
                        composable("home/{home_id}", arguments = listOf(navArgument("home_id"){type = NavType.StringType})){

                            it.arguments?.getString("home_id","NoID")?.let { it1 -> Dashboard(it1) }
                        }
                    }
                }
            }
        }
    }

    @Composable
     fun Dashboard(groupId: String) {
        val usageTrackerClient = UsageTrackerClient(this@MainActivity)
        val usageTrackerFirebaseClient = UsageTrackerFirebaseClient()
        val stats = usageTrackerClient.getUsages()
        if(stats.isEmpty()){
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        usageTrackerFirebaseClient.updateUsageStats(this@MainActivity, groupId ,stats){

        }
        Text(groupId)
    }
}
