package com.mindfultechacademy.zenflow.presentation.asanas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindfultechacademy.zenflow.ZenFlowApp
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.model.TrainingWithAsanas
import com.mindfultechacademy.zenflow.presentation.common.FirebaseImage
import com.mindfultechacademy.zenflow.presentation.common.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsanaDetailsScreen(asanaId: String, onBack: () -> Unit) {
    val asanaVM: AsanaDetailsViewModel = viewModel(factory = viewModelFactory {
        AsanaDetailsViewModel(
            asanaId = asanaId,
            asanasRepository = ZenFlowApp.appModule.asanasRepository)
    })
    val topbarState = rememberTopAppBarState()
    val scrollState = rememberScrollState()
    topbarState.contentOffset = scrollState.value.toFloat()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topbarState)
    asanaVM.asana.value?.let { asana ->
        Scaffold(
            topBar = {
                MediumTopAppBar(
                    title = {
                        Text(
                            text = asana.name)
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
            AsanaView(
                asana,
                Modifier
                    .padding(
                        bottom = it.calculateBottomPadding(),
                        top = it.calculateTopPadding()
                    )
                    .verticalScroll(scrollState)
            )
        }
    }
}

@Composable
private fun AsanaView(
    asana: Asana,
    modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
        ,
    ) {
        FirebaseImage(
            asana.photo,
            modifier = Modifier.fillMaxWidth().aspectRatio(1f)
        )
        Text(asana.name, style = MaterialTheme.typography.headlineSmall)
        benefits(asana.benefits)
        contraindications(asana.contraindications)
        steps(asana.steps)

    }
}

@Composable
private fun benefits(benefits: String) {
    Row {
        Text("Korzyści: ", style = MaterialTheme.typography.titleSmall)
        Text(benefits)
    }
}

@Composable
private fun contraindications(contraindications: String) {
    Row {
        Text("Przeciwskazania: ", style = MaterialTheme.typography.titleSmall)
        Text(contraindications)
    }
}

@Composable
private fun steps(steps: List<String>) {
    Column {
        Text("Kroki: ", style = MaterialTheme.typography.titleSmall)
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            steps.forEachIndexed { index, step ->
                Text("${index + 1}. $step")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AsanaDetailsScreenPreview() {
    AsanaView(
        asana = Asana(
            "1",
            0,
            "korzyści wielkie i wymierne",
            "brak",
            "Super asana",
            "Asanas/1.png",
            listOf("Krok 1", "Krok 2", "Krok 3")
        )
    )
}