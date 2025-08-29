package com.ad_coding.plannettestapp.ui.screen.product_list_one

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ad_coding.plannettestapp.domain.use_case.GetProductsLevelOneUseCase
import com.ad_coding.plannettestapp.domain.use_case.RefreshUseCase
import com.ad_coding.plannettestapp.domain.util.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListOneViewModel @Inject constructor(
    private val getProductListOneUseCase: GetProductsLevelOneUseCase,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductListOneState())
    val state = _state
        .onStart {
            refreshUseCase.lastRefresh
                .onEach { timestamp ->
                    fetchProductList(forceRefresh = true)
                }
                .launchIn(viewModelScope)

            fetchProductList(forceRefresh = false)
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = ProductListOneState(),
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun onEvent(event: ProductListOneEvent) {
        when (event) {
            ProductListOneEvent.RefreshList ->
                viewModelScope.launch {
                    refreshUseCase.requestRefresh()
                }

            ProductListOneEvent.DismissError ->
                _state.update {
                    it.copy(
                        isErrorDialogVisible = false
                    )
                }

            else -> Unit
        }
    }

    private fun fetchProductList(forceRefresh: Boolean) {
        _state.update {
            it.copy(
                isLoading = true,
                isErrorDialogVisible = false
            )
        }
        getProductListOneUseCase(forceRefresh = forceRefresh)
            .onEach { result ->
                when (result) {
                    is DataResult.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.error,
                                isErrorDialogVisible = true
                            )
                        }
                    }

                    is DataResult.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                productList = result.data ?: emptyList(),
                                isErrorDialogVisible = false
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}