package edu.nitt.delta.orientation22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.romainguy.kotlin.math.Float3
import edu.nitt.delta.orientation22.compose.navigation.NavigationRoutes
import edu.nitt.delta.orientation22.compose.screens.ArScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.ArStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
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
    private val mapStateViewModel: MapStateViewModel by viewModels()
    private val DEFAULT_LIGHT_INTENSITY = 0.3f
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {
                mapStateViewModel.doAction(MapAction.GetRoute)
                val routeList = mapStateViewModel.routeListData.value
                mapStateViewModel.doAction(MapAction.GetCurrentLevel)
                val currentLevel = mapStateViewModel.currentState.value
                val glbUrl = intent.getStringExtra("glb")!!
                val anchorHash = intent.getStringExtra("anchorHash")!!
                val scale = intent.getDoubleExtra("anchorScale", 0.5)
                Log.d("Resolve",anchorHash)
                Log.d("Resolve",glbUrl)

                val answer: String? = routeList.firstOrNull { it.position == currentLevel }?.answer

                ArScreen(updateSceneView = {arSceneView ->
                    this.arSceneView = arSceneView
                    cloudAnchorNode = ArModelNode(
                        placementMode = PlacementMode.PLANE_HORIZONTAL,
                        hitPosition = Float3(0f, 0f, 0f),
                        followHitPosition = true,
                        instantAnchor = true
                    )

                    // Check if scale value from backend is proper
                    setUpEnvironment(glbUrl, 0.5)
                }, onClick = {
                    viewModel.doAction(ArAction.PostAnswer(currentLevel))
                }, answer = answer ?: "",
                    onReset = {
                        Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show()
                        viewModel.doAction(ArAction.ResetAnchor(cloudAnchorNode))
                 }, onBack = {
                        MainActivity.startDestination = NavigationRoutes.Map.route
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    private fun setUpEnvironment( glbUrl:String, scale: Double){
        arSceneView.lightEstimationMode = LightEstimationMode.DISABLED
        arSceneView.mainLight?.intensity = DEFAULT_LIGHT_INTENSITY
        cloudAnchorNode.scale = Float3(scale.toFloat(), scale.toFloat(), scale.toFloat())

        Log.d("Scale", scale.toFloat().toString())
        Log.d("Resolve glb",glbUrl)

        Toast.makeText(applicationContext,"Scan the surroundings for flat surfaces.",Toast.LENGTH_SHORT).show()

        viewModel.doAction(
            ArAction.LoadModel(
                this,
                lifecycle,
                { _, _ ->
                    tapModel()
                },
                arSceneView,
                cloudAnchorNode,
                glbUrl
            )
        )
    }

    private fun tapModel(){
        cloudAnchorNode.playAnimation(0,false)
    }
}
