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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mindfultechacademy.zenflow.model.Asana
import com.mindfultechacademy.zenflow.presentation.common.FirebaseImage


@Composable
fun AsanasList(
    asanas: List<Asana> = emptyList(),
    modifier: Modifier,
    onAsanaTap: (Asana) -> Unit) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    ) {
        this.items(asanas) {
            Column(
                modifier = Modifier
                    .clickable {
                        onAsanaTap(it)
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
                ) {
                    FirebaseImage(
                        it.photo,
                        Modifier
                            .size(100.dp)
                            .padding(8.dp))
                    Text(
                        text = it.name,
                        modifier = Modifier
                            .padding(8.dp),
                        fontSize = TextUnit(16f, TextUnitType.Sp),
                    )
                }
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

        }
    }
}