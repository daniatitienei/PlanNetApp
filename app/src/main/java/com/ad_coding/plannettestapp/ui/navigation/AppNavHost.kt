package com.ad_coding.plannettestapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ad_coding.plannettestapp.ui.screen.grade_list.GradeListScreen
import com.ad_coding.plannettestapp.ui.screen.grade_list.GradeListViewModel
import com.ad_coding.plannettestapp.ui.screen.product_details.ProductDetailsEvent
import com.ad_coding.plannettestapp.ui.screen.product_details.ProductDetailsScreen
import com.ad_coding.plannettestapp.ui.screen.product_details.ProductDetailsViewModel
import com.ad_coding.plannettestapp.ui.screen.product_list_one.ProductListOneEvent
import com.ad_coding.plannettestapp.ui.screen.product_list_one.ProductListOneScreen
import com.ad_coding.plannettestapp.ui.screen.product_list_one.ProductListOneViewModel
import com.ad_coding.plannettestapp.ui.screen.product_list_two.ProductListTwoEvent
import com.ad_coding.plannettestapp.ui.screen.product_list_two.ProductListTwoScreen
import com.ad_coding.plannettestapp.ui.screen.product_list_two.ProductListTwoViewModel

@Composable
fun AppNavHost(navController: NavHostController, startDestinationEnum: DestinationEnum) {

    NavHost(navController = navController, startDestination = startDestinationEnum.getRoute()) {
        composable<ProductListOne> {
            val viewModel = hiltViewModel<ProductListOneViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ProductListOneScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is ProductListOneEvent.ProductClick -> {
                            navController.navigate(
                                route = ProductDetails(
                                    productId = event.product.id,
                                    level = "level1"
                                ))
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

        composable<ProductListTwo> {
            val viewModel = hiltViewModel<ProductListTwoViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ProductListTwoScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is ProductListTwoEvent.ProductClick -> {
                            navController.navigate(
                                route = ProductDetails(
                                    productId = event.product.id,
                                    level = "level2"
                                ))
                        }

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }

        composable<GradeList> {
            val viewModel = hiltViewModel<GradeListViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            GradeListScreen(
                state = state,
                onEvent = viewModel::onEvent
            )
        }

        composable<ProductDetails> {
            val viewModel = hiltViewModel<ProductDetailsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ProductDetailsScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        ProductDetailsEvent.NavigateBack -> navController.popBackStack()

                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
    }

}