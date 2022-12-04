package edu.nitt.delta.orientation22

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.google.ar.sceneform.animation.ModelAnimator
import dagger.hilt.android.AndroidEntryPoint
import dev.romainguy.kotlin.math.Float3
import edu.nitt.delta.orientation22.compose.screens.ArScreen
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
import edu.nitt.delta.orientation22.di.viewModel.actions.MapAction
import edu.nitt.delta.orientation22.di.viewModel.uiState.ArStateViewModel
import edu.nitt.delta.orientation22.di.viewModel.uiState.MapStateViewModel
import edu.nitt.delta.orientation22.models.MarkerModel
import edu.nitt.delta.orientation22.models.game.LocationData
import edu.nitt.delta.orientation22.ui.theme.Orientation22androidTheme
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.arcore.LightEstimationMode
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.light.intensity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


@AndroidEntryPoint
class ArActivity : ComponentActivity() {
    private lateinit var arSceneView: ArSceneView
    private lateinit var cloudAnchorNode: ArModelNode
    private val viewModel:ArStateViewModel by viewModels()
    private val mapStateViewModel: MapStateViewModel by viewModels()
    private val DEFAULT_LIGHT_INTENSITY=0.3f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Orientation22androidTheme {

                mapStateViewModel.doAction(MapAction.GetRoute)
                var routeList = mapStateViewModel.routeListData.value
                mapStateViewModel.doAction(MapAction.GetCurrentLevel)
                val currentLevel = mapStateViewModel.currentState.value
                val glbUrl = intent.getStringExtra("glb")!!
                val anchorHash = intent.getStringExtra("anchorHash")!!
                val scale = intent.getDoubleExtra("anchorScale", 0.5)
                Log.d("Resolve",anchorHash)
                Log.d("Resolve",glbUrl)
                ArScreen(updateSceneView = {arSceneView ->
                    this.arSceneView =arSceneView
                    cloudAnchorNode = ArModelNode(placementMode = PlacementMode.PLANE_HORIZONTAL)
                    setUpEnvironment(glbUrl, scale)
                }, onClick = {
                    viewModel.doAction(ArAction.PostAnswer)
                }, currentLevel = currentLevel, routeList = routeList, onResolve = {
                    Log.d("Resolve","Called")
                    viewModel.doAction(ArAction.ResolveAnchor(cloudAnchorNode,anchorHash))
                })
            }
        }
    }

    private fun setUpEnvironment( glbUrl:String, scale: Double){
        arSceneView.lightEstimationMode= LightEstimationMode.DISABLED
        arSceneView.mainLight?.intensity =DEFAULT_LIGHT_INTENSITY
        arSceneView.cloudAnchorEnabled=true
        cloudAnchorNode.scale = Float3(scale.toFloat(), scale.toFloat(), scale.toFloat())
        cloudAnchorNode.isScaleEditable = false
        cloudAnchorNode.isPositionEditable = false
        cloudAnchorNode.isRotationEditable = false
        Log.d("Scale", scale.toFloat().toString())
        Log.d("Resolve glb",glbUrl)
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

    override fun onBackPressed() {
        val intent = Intent(applicationContext,MainActivity::class.java)
        startActivity(intent)
        return
        super.onBackPressed()
    }
}
