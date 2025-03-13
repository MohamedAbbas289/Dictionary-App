package com.example.dictionaryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionaryapp.feature_dictionary.presentation.WordInfoItem
import com.example.dictionaryapp.feature_dictionary.presentation.WordInfoViewModel
import com.example.dictionaryapp.ui.theme.DictionaryAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: WordInfoViewModel = hiltViewModel()
            val snackBarHostState = remember { SnackbarHostState() }
            DisplaySnackBar(viewModel = viewModel, snackBarHostState = snackBarHostState)
            DictionaryAppTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
                ) { innerPadding ->
                    if (viewModel.wordInfo.value.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Center),
                                color = Color.Blue,
                                strokeWidth = 2.dp
                            )
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = viewModel.state.value,
                            onValueChange = viewModel::onSearch,
                            placeholder = { Text("Search for a word here..") }
                        )
                        Spacer(Modifier.height(16.dp))

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(viewModel.wordInfo.value.wordInfo.size) { index ->
                                val wordInfo = viewModel.wordInfo.value.wordInfo[index]
                                if (index > 0) {
                                    Spacer(Modifier.height(8.dp))
                                }
                                WordInfoItem(wordInfo = wordInfo)
                                if (index < viewModel.wordInfo.value.wordInfo.size - 1) {
                                    HorizontalDivider()
                                }
                            }

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun DisplaySnackBar(
    viewModel: WordInfoViewModel,
    snackBarHostState: SnackbarHostState,
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is WordInfoViewModel.UiEvent.ShowSnackBar -> {
                        snackBarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = "OK",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        }
    }
}
