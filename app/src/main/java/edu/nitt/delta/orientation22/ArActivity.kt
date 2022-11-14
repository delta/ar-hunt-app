package edu.nitt.delta.orientation22

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import edu.nitt.delta.orientation22.compose.screens.ArScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.ArStateViewModel
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.arcore.LightEstimationMode
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.light.intensity


@AndroidEntryPoint
class ArActivity : ComponentActivity() {
    private lateinit var arSceneView: ArSceneView
    private lateinit var cloudAnchorNode: ArModelNode
    private val viewModel:ArStateViewModel by viewModels()
    private val DEFAULT_LIGHT_INTENSITY=0.3f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                ArScreen(updateSceneView = {arSceneView ->
                    this.arSceneView =arSceneView
                    cloudAnchorNode = ArModelNode(placementMode = PlacementMode.PLANE_HORIZONTAL)
                    setUpEnvironment()
                })
            }
        }
    }

    private fun setUpEnvironment(){
        arSceneView.lightEstimationMode= LightEstimationMode.DISABLED
        arSceneView.mainLight?.intensity =DEFAULT_LIGHT_INTENSITY
        arSceneView.cloudAnchorEnabled=true
        viewModel.doAction(
            ArAction.LoadModel(
                this,
                lifecycle,
                { _, _ ->
                    tapModel()
                },
                arSceneView,
                cloudAnchorNode
            )
        )

    }

    private fun tapModel(){
        Toast.makeText(applicationContext,"Model clicked successfully",Toast.LENGTH_SHORT).show()
    }

}
