package com.mindfultechacademy.zenflow.presentation.training.newTraining

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindfultechacademy.zenflow.ZenFlowApp
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.model.TrainingAsana
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas
import com.mindfultechacademy.zenflow.presentation.asanas.AsanasList
import com.mindfultechacademy.zenflow.presentation.common.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTrainingForm(
    onBack: () -> Unit,
    onSave: (TrainingWithAsanas) -> Unit
) {
    val viewModel: NewTrainingViewModel =
        viewModel(factory = viewModelFactory { NewTrainingViewModel(ZenFlowApp.appModule.asanasRepository) })
    var openBottomSheet by remember { mutableStateOf(false) }
    val trainingAsanas = viewModel.trainingAsanas.collectAsState().value

    val topbarState = rememberTopAppBarState()
    val scrollState = rememberScrollState()
    topbarState.contentOffset = scrollState.value.toFloat()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topbarState)

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text("Nowy trening")
                },
                actions = {
                    TextButton(
                        onClick = { onSave(viewModel.saveableTraining) },
                        enabled = viewModel.canSave,
                        content = { Text("Zapisz") })
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                openBottomSheet = true
            }) {
                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text("Dodaj asanę")
                    Icon(Icons.Default.AddCircle, contentDescription = "")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Column(
            Modifier
                .padding(
                    bottom = it.calculateBottomPadding(),
                    top = it.calculateTopPadding() + 8.dp
                )
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
                .fillMaxSize()
        ) {
            TextField(
                value = viewModel.trainingName.value,
                onValueChange = { viewModel.trainingName.value = it },
                label = {
                    Text(text = "Nazwa")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Asany")

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(8.dp))

            trainingAsanas.forEachIndexed { index, trainingAsana ->
                TrainingAsanaView(
                    trainingAsana,
                    onSelectDuration = {
                        viewModel.updateTrainingAsanaDuration(index, it)
                    },
                    onSelectRepetitions = {
                        viewModel.updateTrainingAsanaRepetitions(index, it)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            if (openBottomSheet) {
                AsanaPicker(
                    viewModel.asanas,
                    onDismiss = {
                        openBottomSheet = false
                    },
                    onAsanaTap = { asana ->
                        viewModel.addTrainingAsana(TrainingAsana(asana.id, asana.name, duration = 30, trainingId = 0, repetitions = 1))
                        openBottomSheet = false
                    }
                )
            }
        }
    }
}

@Composable
private fun TrainingAsanaView(
    asana: TrainingAsana,
    onSelectDuration: (Int) -> Unit,
    onSelectRepetitions: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = asana.asanaName, Modifier.padding(horizontal = 20.dp))
        Duration(
            currentDuration = asana.duration,
            onSelectDuration = onSelectDuration)
        Repetitions(
            number = asana.repetitions,
            onSelectRepetitions = onSelectRepetitions
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Duration(currentDuration: Int, onSelectDuration: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Czas trwania: ", style = MaterialTheme.typography.titleSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            InputChip(
                selected = currentDuration == 30,
                onClick = { onSelectDuration(30) },
                label = {
                    Text("30 sek", style = MaterialTheme.typography.labelMedium)
                })
            InputChip(
                selected = currentDuration == 60,
                onClick = { onSelectDuration(60) },
                label = {
                    Text("1 min", style = MaterialTheme.typography.labelMedium)
                })

            InputChip(
                selected = currentDuration == 300,
                onClick = { onSelectDuration(300) },
                label = {
                    Text("5 min", style = MaterialTheme.typography.labelMedium)
                })
        }
    }
}


@Composable
private fun Repetitions(
    number: Int = 0,
    onSelectRepetitions: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Text("Powtórzenia: ", style = MaterialTheme.typography.titleSmall)
            Text(number.toString())
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            val plusColor = if (number < 10) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)

            Text(
                text = "+",
                modifier = Modifier
                    .background(plusColor, RoundedCornerShape(20.dp))
                    .padding(horizontal = 6.dp)
                    .clickable {
                        if (number < 10) {
                            onSelectRepetitions(number + 1)

                        }
                    },
                color = MaterialTheme.colorScheme.onPrimary,
            )
            val minusColor = if (number > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
            Text(
                text = "-",
                modifier = Modifier
                    .background(minusColor, RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp)
                    .clickable {
                        if (number > 0) {
                            onSelectRepetitions(number - 1)

                        }
                    },
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun AsanaPicker(
    asanas: List<Asana>,
    onAsanaTap: (Asana) -> Unit,
    onDismiss: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = modalBottomSheetState
    ) {
        AsanasList(
            asanas = asanas,
            modifier = Modifier,
            onAsanaTap = onAsanaTap
        )
    }
}
