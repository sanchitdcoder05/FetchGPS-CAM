package com.example.fetchdata

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CameraScreen(
    navController: NavController
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Camera Tracking")
                    }
                },
                navigationIcon = {
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            "Start"
                        )
                    }
                },
                actions = {
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Text(
                            "Stop"
                        )
                    }
                }

            )
        },
        bottomBar = {
            BottomAppBar {

                NavigationBarItem(
                    selected =  false,
                    onClick = { navController.navigate("FetchLocationFromFirebase") },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.gps),
                                modifier = Modifier.size(32.dp),
                                contentDescription = "GPS Tracking"
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("GPS Tracking")
                        }
                    }
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate("CameraScreen") },
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                modifier = Modifier.size(32.dp),
                                contentDescription = "Camera Tracker"
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Camera Tracker")
                        }
                    }
                )

            }
        },
        content = {
            Column(modifier = Modifier.padding(it)) {
                Text("Hello World")
            }
        }
    )
}