package com.mindfultechacademy.zenflow.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mindfultechacademy.zenflow.ZenFlowApp
import com.mindfultechacademy.zenflow.presentation.asanas.AsanasList
import com.mindfultechacademy.zenflow.presentation.common.viewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    featuredAsanaTap: (String) -> Unit,
) {
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory {
        HomeViewModel(
            trainingsRepository = ZenFlowApp.appModule.trainingsRepository,
            asanasRepository = ZenFlowApp.appModule.asanasRepository
        )
    })

    val topbarState = rememberTopAppBarState()
    val scrollState = rememberScrollState()
    topbarState.contentOffset = scrollState.value.toFloat()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topbarState)

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "ZenFlow ")
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        Column(
            Modifier
                .padding(
                    bottom = it.calculateBottomPadding(),
                    top = it.calculateTopPadding()
                )
                .padding(horizontal = 20.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            if (viewModel.trainingsCount.value > 0) {
                Card(
                    Modifier.fillMaxWidth()
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = "Liczba treningów ${viewModel.trainingsCount.value}")
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }
            viewModel.mostUsedAsana.value?.let {
                Card(
                    Modifier.fillMaxWidth()
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = "Najczęściej wykonywana asana to ${it.name}")
                }

                Spacer(modifier = Modifier.padding(8.dp))
            }

            viewModel.featuredAsana.value?.let {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            featuredAsanaTap(it.id)
                        }
                ) {
                    Text(modifier = Modifier.padding(8.dp), text = "Polecamy ${it.name}")
                }
            }
        }
    }
}
