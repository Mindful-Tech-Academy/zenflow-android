package com.mindfultechacademy.zenflow.presentation.training

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingsScreen(
    viewModel: TrainingsViewModel,
    onAddTrainingTap: () -> Unit,
    onTrainingTap: (TrainingWithAsanas) -> Unit) {

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
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onAddTrainingTap()
            }) {
                Row(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Icon(Icons.Default.AddCircle, contentDescription = "")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        TrainingsList(
            viewModel.trainings.collectAsState().value,
            Modifier
                .padding(
                    bottom = it.calculateBottomPadding(),
                    top = it.calculateTopPadding() + 8.dp
                ),
            onTrainingTap = onTrainingTap
        )}
}


@Composable
fun TrainingsList(
    trainings: List<TrainingWithAsanas>,
    modifier: Modifier,
    onTrainingTap: (TrainingWithAsanas) -> Unit) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 20.dp)
    ) {
        items(trainings) {
            Card(
                modifier = Modifier
                    .clickable {
                        onTrainingTap(it)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = it.training.name,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Icon(
                        Icons.Filled.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }

            }
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}