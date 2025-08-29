@file:OptIn(ExperimentalMaterial3Api::class)

package com.ad_coding.plannettestapp.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun TabNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val startDestinationEnum = DestinationEnum.PRODUCT_LIST_ONE
    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestinationEnum.ordinal) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val isTabVisible by remember {
        derivedStateOf {
            navBackStackEntry?.destination?.hasRoute(ProductDetails::class) != true
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = isTabVisible,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier.zIndex(1f)
        ) {
            PrimaryTabRow(
                selectedTabIndex = selectedDestination,
                modifier = Modifier.statusBarsPadding()
            ) {
                DestinationEnum.entries.forEachIndexed { index, destination ->
                    Tab(
                        selected = selectedDestination == index,
                        onClick = {
                            navController.navigate(route = destination.getRoute())
                            selectedDestination = index
                        },
                        text = {
                            Text(
                                text = destination.getLabel(),
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    )
                }
            }
        }
        AppNavHost(
            navController = navController,
            startDestinationEnum = startDestinationEnum
        )
    }
}