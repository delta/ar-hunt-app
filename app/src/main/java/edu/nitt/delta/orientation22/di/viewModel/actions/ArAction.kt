package edu.nitt.delta.orientation22.di.viewModel.actions

import android.content.Context
import android.view.MotionEvent
import androidx.lifecycle.Lifecycle
import edu.nitt.delta.orientation22.models.game.LocationRequest
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.renderable.Renderable

sealed class ArAction {
    data class HostAnchor(val sceneView: ArSceneView,
    val cloudAnchorNode: ArModelNode, val context: Context): ArAction()
    data class ResolveAnchor(val cloudAnchorNode: ArModelNode,val code:String, val context: Context) : ArAction()
    data class ResetAnchor(val cloudAnchorNode: ArModelNode) : ArAction()
    data class LoadModel(
        val context: Context,
        val lifecycle: Lifecycle?,
        val onTapModel:((MotionEvent, Renderable?) -> Unit)?,
        val sceneView: ArSceneView,
        val cloudAnchorNode: ArModelNode,
        val gldFileUrl: String,
        val isHost: Boolean,
    ) : ArAction()
    data class UpdateLocation(val location:LocationRequest, val context: Context): ArAction()
    object FetchLocation:ArAction()
    object PostAnswer : ArAction()
}
