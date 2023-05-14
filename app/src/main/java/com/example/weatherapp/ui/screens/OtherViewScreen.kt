package com.example.weatherapp.ui.screens

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.common.constants.Constants

fun Context.findActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Composable
fun AppSurface(appSurface: @Composable () -> Unit) {
    // A surface container using the 'background' color from the theme
    androidx.compose.material3.Surface(
        modifier = Modifier.fillMaxSize(),
        color = androidx.compose.material3.MaterialTheme.colorScheme.background
    ) {
        Column(
            // for column we are specifying modifier on below line.
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {
            Surface(
                elevation = 10.dp,
                modifier = Modifier.background(MaterialTheme.colors.primarySurface)
            ) {
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    title = {
                        androidx.compose.material.Text(
                            text = "Weather App",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            // back
                        }) {
                            Icon(
                                tint = Color.White,
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ""
                            )
                        }
                    }
                )
            }
            appSurface()
        }
    }
}

@Composable
fun ShowLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ShowErrorMessage(text: String?, actionMessage: String? = null, action: (() -> Unit)? = null) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            println("error: ${text}")
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = text ?: Constants.NO_CONNECTION_MESSAGE,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Normal
                )
            )
            if (action != null && actionMessage != null) {
                Button(
                    onClick = { action() },
                    modifier = Modifier
                        .height(56.dp)
                        .background(MaterialTheme.colors.primarySurface)
                ) {
                    Text(text = actionMessage)
                }
            }
        }
    }
}