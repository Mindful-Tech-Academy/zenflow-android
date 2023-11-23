package com.mindfultechacademy.zenflow.presentation.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindfultechacademy.zenflow.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    var email: String by remember { mutableStateOf("test@mindfultech.academy") }
    var password: String by remember { mutableStateOf("test1234") }

    Scaffold() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.logo_no_background),
                contentDescription = "",
                modifier = Modifier.padding(bottom = 100.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.padding(bottom = 16.dp),
                label = {
                    Text(text = "Email")
                },
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.padding(bottom = 16.dp),
                label = {
                    Text(text = "Hasło")
                },
                visualTransformation = PasswordVisualTransformation(),
            )

            Button(
                enabled = email.isNotEmpty() && password.isNotEmpty(),
                onClick = {
                    onLogin(email, password)
                }) {
                Text(text = "Zaloguj się")
            }
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview () {
    LoginScreen() { _, _ -> }
}