package com.visiondev.usagetrackerbuddy.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.visiondev.usagetrackerbuddy.ui.UsageTrackerFirebaseClient
import kotlinx.coroutines.launch

@Composable
fun GroupEntryScreen(
    navController: NavController
){
    val navIndex = remember {
        mutableStateOf(0)
    }
    val scaffoldState = rememberScaffoldState()
    Scaffold(modifier = Modifier.fillMaxSize(), scaffoldState = scaffoldState) {
        when(navIndex.value){
            0 -> Entry(navIndex = navIndex,navController)
            1 -> JoinGroup(navController,scaffoldState)
        }
    }

}


@Composable
fun Entry(navIndex: MutableState<Int>, navController: NavController){
    Column(modifier = Modifier.fillMaxSize(),Arrangement.SpaceEvenly, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            navIndex.value = 1
        }) {
            Text("Join Group")
        }
        val client = UsageTrackerFirebaseClient()


        Button(onClick = {
            /*Create a group and navigate to main screen with group Id
            * */
        }) {
            Text("Create")
        }
    }
}

@Composable
fun JoinGroup(navController: NavController, scaffoldState: ScaffoldState) {
    val client = UsageTrackerFirebaseClient()

    Column(modifier = Modifier.fillMaxSize()) {
        val groupName = remember {
            mutableStateOf("")
        }
        TextField(value = groupName.value, onValueChange = {c -> groupName.value = c}, placeholder = { Text(
            text = "Enter Group Id"
        )})
        val scope = rememberCoroutineScope()

        Button(onClick = {
            //Check If Group Id Exists
            /*Create a group and navigate to main screen with group Id
            * */
            client.doesGroupExists(groupId = groupName.value){exists->
                if(exists){
                    navController.navigate("home/${groupName.value}")
                }else{
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Group Does not exists",null,SnackbarDuration.Short)
                        Log.i("Omkar","Group Does not exists")
                    }
                    }
            }
        }) {
            Text("Join")
        }
    }
}

@Composable
fun CreateGroup(navController: NavController, scaffoldState: ScaffoldState) {
    val client = UsageTrackerFirebaseClient()

    Column(modifier = Modifier.fillMaxSize()) {
        val groupName = remember {
            mutableStateOf("")
        }
        TextField(value = groupName.value, onValueChange = {c -> groupName.value = c}, placeholder = { Text(
            text = "Enter Group Id"
        )})
        val scope = rememberCoroutineScope()

        Button(onClick = {
            //Check If Group Id Exists
            /*Create a group and navigate to main screen with group Id
            * */
            client.doesGroupExists(groupId = groupName.value){exists->
                if(exists){
                    navController.navigate("home/${groupName.value}")
                }else{
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("Group Does not exists",null,SnackbarDuration.Short)
                        Log.i("Omkar","Group Does not exists")
                    }
                }
            }
        }) {
            Text("Join")
        }
    }
}
@Preview
@Composable
fun GroupEntryPreview(){
    GroupEntryScreen(rememberNavController())
}