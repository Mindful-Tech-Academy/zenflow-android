package com.mindfultechacademy.zenflow.presentation.asanas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindfultechacademy.zenflow.ZenFlowApp
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.presentation.common.FirebaseImage
import com.mindfultechacademy.zenflow.presentation.common.viewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsanasScreen(
    onAsanaTap: (Asana) -> Unit
) {
    val topbarState = rememberTopAppBarState()
    val scrollState = rememberScrollState()
    topbarState.contentOffset = scrollState.value.toFloat()
    val scrollBehavior = enterAlwaysScrollBehavior(topbarState)

    val asanasViewModel: AsanasViewModel =
        viewModel(factory = viewModelFactory { AsanasViewModel(asanasRepository = ZenFlowApp.appModule.asanasRepository) })
    val state =
        asanasViewModel.state.collectAsState(initial = AsanasViewModel.Companion.AsanasState())

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Asany")
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        AsanasList(
            state.value.asanas,
            Modifier.padding(
                bottom = it.calculateBottomPadding(),
                top = it.calculateTopPadding()
            ),
            onAsanaTap)
    }
}

