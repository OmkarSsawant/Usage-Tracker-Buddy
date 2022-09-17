package com.visiondev.usagetrackerbuddy.ui.screens

import android.R
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.visiondev.usagetrackerbuddy.ui.data.UsageTrackerLocalRepo
import com.visiondev.usagetrackerbuddy.ui.theme.Shapes
import com.visiondev.usagetrackerbuddy.ui.theme.UsageTrackerBuddyTheme


@Composable
fun MainEntry(navController: NavController,usageTrackerLocalRepo: UsageTrackerLocalRepo){
    val groupIds = usageTrackerLocalRepo.getJoinedGroupIds()
    if(groupIds!=null){
        GroupListing(rememberNavController())
    }else{
        Column {
            Text(text = "You will need to create or join group")
            Button(onClick = {
                navController.navigate("group")
            }){
                Text(text = "Enter")
        }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupListing(navController: NavController){
    val groupIds = listOf("group1","group2","group3")

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.1f),Arrangement.SpaceAround) {
            Text(text = "Groups", style = MaterialTheme.typography.h2)
            IconButton(onClick = {
                navController.navigate("group")
            }) {
                Icon(painterResource(id = R.drawable.ic_input_add), contentDescription = "Add Group")
            }
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.9f)

            .padding(12.dp), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)){
            items(groupIds, itemContent = {gId:String->
                ListItem(modifier = Modifier
                    .border(1.dp, Color.Gray, RectangleShape)
                    .clickable {
                        navController.navigate("home/$gId")
                    }
                    ) {

                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(text = gId.split("#").last(), modifier = Modifier.fillMaxSize())
                    }
                }
            })
        }
    }

}


@Preview(showBackground = true)
@Composable
fun EntryScreenPreview(){
    androidx.compose.material.Surface(   modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background) {
        UsageTrackerBuddyTheme {
            GroupListing(rememberNavController())
        }
    }
}