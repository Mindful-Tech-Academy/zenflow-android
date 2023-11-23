package com.mindfultechacademy.zenflow.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindfultechacademy.zenflow.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(user: User?, onLogout: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Profil")
                }
            )
        },
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            user?.let {
                UserDetails(it)
            }
            Button(
                onClick = onLogout,
                Modifier.padding(bottom = 20.dp)
            ) {
                Text("Wyloguj się")
            }
        }
    }
}

@Composable
private fun UserDetails(user: User) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            Icons.Filled.Person, contentDescription = "User",
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary, RoundedCornerShape(40.dp))
                .size(40.dp),
            tint = MaterialTheme.colorScheme.onPrimary
            )
        Column {
            Text("Email")
            Text(user.email)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_2)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(User(0, "test.mindfultech.academy")) { }
}