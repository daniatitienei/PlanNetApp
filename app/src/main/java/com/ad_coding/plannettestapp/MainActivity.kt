package com.ad_coding.plannettestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ad_coding.plannettestapp.ui.navigation.TabNavigation
import com.ad_coding.plannettestapp.ui.theme.PlanNetTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanNetTestAppTheme {
                TabNavigation()
            }
        }
    }
}