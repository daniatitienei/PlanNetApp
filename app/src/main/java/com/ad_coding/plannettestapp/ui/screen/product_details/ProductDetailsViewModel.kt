package com.ad_coding.plannettestapp.ui.screen.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ad_coding.plannettestapp.domain.use_case.GetProductLevelOneByIdUseCase
import com.ad_coding.plannettestapp.domain.use_case.GetProductLevelTwoByIdUseCase
import com.ad_coding.plannettestapp.domain.util.DataResult
import com.ad_coding.plannettestapp.ui.navigation.ProductDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProductLevelOneByIdUseCase: GetProductLevelOneByIdUseCase,
    private val getProductLevelTwoByIdUseCase: GetProductLevelTwoByIdUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow(ProductDetailsState())
    val state = _state
        .onStart {
            fetchDetails()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ProductDetailsState()
        )

    fun onEvent(event: ProductDetailsEvent) {
        when (event) {
            ProductDetailsEvent.DismissError -> {
                _state.update {
                    it.copy(
                        isErrorDialogVisible = false
                    )
                }
            }
            ProductDetailsEvent.FetchDetails -> fetchDetails()
            else -> Unit
        }
    }

    private fun fetchDetails() {
        _state.update {
            it.copy(isErrorDialogVisible = false, isLoading = true)
        }

        val args = savedStateHandle.toRoute<ProductDetails>()

        val flow = if (args.level == "level1") {
            getProductLevelOneByIdUseCase(args.productId)
        } else {
            getProductLevelTwoByIdUseCase(args.productId)
        }

        flow
            .onEach { result ->
                when (result) {
                    is DataResult.Error ->
                        _state.update {
                            it.copy(
                                errorMessage = result.error,
                                isErrorDialogVisible = true,
                                isLoading = false
                            )
                        }
                    is DataResult.Success ->
                        _state.update {
                            it.copy(
                                product = result.data,
                                isLoading = false
                            )
                        }
                }
            }
            .launchIn(viewModelScope)

    }
}