package com.example.fetchdata

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fetchdata.ui.theme.FetchDataTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchDataTheme {

                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "FetchLocationFromFirebase") {
                    composable("FetchLocationFromFirebase") {
                        FetchLocationFromFirebase(navController)
                    }
                    composable("CameraScreen") {
                        CameraScreen(navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FetchLocationFromFirebase(
    navController: NavController
) {
    var locationText by remember { mutableStateOf("Fetching location...") }

    val database = FirebaseDatabase.getInstance()
    val locationRef = database.getReference("user").child("location")
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        locationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val location = snapshot.getValue(LocationData::class.java)
                location?.let {
                    locationText = "Latitude: ${it.latitude}, Longitude: ${it.longitude}\n${it.address}"
                } ?: run {
                    locationText = "No location data available."
                }
            }

            override fun onCancelled(error: DatabaseError) {
                locationText = "Failed to fetch location: ${error.message}"
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("GPS Tracking")
                    }
                },
                navigationIcon = {
                    Button(
                        onClick = {
                            isVisible = true
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
                            isVisible = false
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
                    selected = true,
                    onClick = { navController.navigate("FetchLocationFromFirebase") },
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.gps),
                                modifier = Modifier.size(32.dp),
                                contentDescription = "GPS Tracking"
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text("GPS Tracking") }
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate("CameraScreen") },
                    icon = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.camera),
                                modifier = Modifier.size(40.dp),
                                contentDescription = "GPS Tracking"
                            )
                            Text("Camera Tracker")
                        }
                    }
                )

            }
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Tracking Screen", fontSize = 32.sp, modifier = Modifier.padding(16.dp))
                Spacer(modifier = Modifier.height(10.dp))
                if (isVisible) {
                    Text(text = locationText, modifier = Modifier.padding(16.dp), fontSize = 18.sp)
                }
                if (!isVisible){
                    Text(text = "Tracking Not Started", modifier = Modifier.padding(16.dp), fontSize = 18.sp)
                }
            }
        }
    )
}


data class LocationData(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = ""
) {
    constructor() : this(0.0, 0.0, "")
}
