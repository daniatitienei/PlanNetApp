package com.ad_coding.plannettestapp.ui.screen.grade_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ad_coding.plannettestapp.domain.use_case.GetGradeListUseCase
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
class GradeListViewModel @Inject constructor(
    private val getGradeListUseCase: GetGradeListUseCase,
    private val refreshUseCase: RefreshUseCase
) : ViewModel() {

    private var _state = MutableStateFlow(GradeListState())
    val state = _state
        .onStart {
            refreshUseCase.lastRefresh
                .onEach { timestamp ->
                    loadGradeList(forceRefresh = true)
                }
                .launchIn(viewModelScope)

            loadGradeList(forceRefresh = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = GradeListState()
        )

    fun onEvent(event: GradeListEvent) {
        when (event) {
            GradeListEvent.RefreshList ->
                viewModelScope.launch {
                    _state.update {
                        it.copy(isRefreshing = true)
                    }
                    refreshUseCase.requestRefresh()
                }

            GradeListEvent.DismissError -> {
                _state.update {
                    it.copy(
                        isErrorDialogVisible = false
                    )
                }
            }
        }
    }

    private fun loadGradeList(forceRefresh: Boolean) {
        _state.update {
            it.copy(isLoading = true, isErrorDialogVisible = false)
        }
        getGradeListUseCase(forceRefresh = forceRefresh)
            .onEach { result ->
                when (result) {
                    is DataResult.Error ->
                        _state.update {
                            it.copy(
                                errorMessage = result.error,
                                isErrorDialogVisible = true,
                                isLoading = false,
                                isRefreshing = false
                            )
                        }

                    is DataResult.Success -> {
                        _state.update {
                            it.copy(
                                gradeList = result.data ?: emptyList(),
                                isLoading = false,
                                isRefreshing = false
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)

    }
}