package com.mindfultechacademy.zenflow.presentation.training.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindfultechacademy.zenflow.ZenFlowApp
import com.mindfultechacademy.zenflow.presentation.common.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingDetailsScreen(
    id: Int,
    onBack: () -> Unit,
    onAsanaTap: (String) -> Unit
) {
    val trainingViewModel: TrainingDetailsViewModel = viewModel(factory = viewModelFactory {
        TrainingDetailsViewModel(id, ZenFlowApp.appModule.trainingsRepository)
    })

    val topbarState = rememberTopAppBarState()
    val scrollState = rememberScrollState()
    topbarState.contentOffset = scrollState.value.toFloat()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topbarState)

    trainingViewModel.training.collectAsState().value?.let { training ->
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(training.training.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null)
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(20.dp)
                    .fillMaxSize()
                    .verticalScroll(state = scrollState)
            ) {
                Text(text = "Struktura treningu")

                training.asanas.forEach { asana ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                onAsanaTap(asana.asanaId)
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = asana.asanaName)
                            Row {
                                Text("Czas trwania: ", style = MaterialTheme.typography.titleSmall)
                                Text(text = asana.duration.toString())
                            }
                            Row {
                                Text("Powtórzenia: ", style = MaterialTheme.typography.titleSmall)
                                Text(text = asana.repetitions.toString())
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
    }

}