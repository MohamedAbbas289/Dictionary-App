package com.example.dictionaryapp.feature_dictionary.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.feature_dictionary.domain.usecases.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
) : ViewModel() {

    private val _state = mutableStateOf("")
    val state: State<String> = _state

    private val _wordInfo = mutableStateOf(WordInfoState())
    val wordInfo: State<WordInfoState> = _wordInfo

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _state.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            getWordInfo(query)
                .onEach { result ->
                    when (result) {
                        is Resource.Failure -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackBar(
                                    message = result.message ?: "Unknown error"
                                )
                            )
                            Log.e("TAG: error: ", result.message.toString())
                        }

                        is Resource.Loading -> {
                            _wordInfo.value = _wordInfo.value.copy(
                                wordInfo = result.data ?: emptyList(),
                                isLoading = true
                            )
                            Log.e("TAG: loading: ", result.data.toString())
                        }

                        is Resource.Success -> {
                            _wordInfo.value = _wordInfo.value.copy(
                                wordInfo = result.data ?: emptyList(),
                                isLoading = false
                            )
                            Log.e("TAG: success: ", result.data.toString())
                        }

                        is Resource.Unspecified -> Unit
                    }
                }.launchIn(this)

        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
    }
}
