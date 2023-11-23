package com.mindfultechacademy.zenflow.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun FirebaseImage(photoName: String, modifier: Modifier = Modifier) {
    val imageState = loadNetworkImage(photoName = photoName)
    AsyncImage(model = imageState.value, contentDescription = "Image", modifier = modifier)
}

@Composable
fun loadNetworkImage(
    photoName: String,
): State<String?> {
    return produceState<String?>(initialValue = null, photoName) {

        // In a coroutine, can make suspend calls
        val image = FirebaseStorage.getInstance().reference.child(photoName).downloadUrl.await()

        // Update State with either an Error or Success result.
        // This will trigger a recomposition where this State is read
        value = if (image == null) {
            null
        } else {
            image.toString()
        }
    }
}