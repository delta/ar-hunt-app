package edu.nitt.delta.orientation22.compose.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.github.sceneview.ar.ArSceneView

@Composable
fun ArScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    updateSceneView:(ArSceneView)->Unit
){
    AndroidView(factory = {
        context ->
        ArSceneView(
            context,
        ).apply {
            updateSceneView(this)
        }
    }
    )
}
