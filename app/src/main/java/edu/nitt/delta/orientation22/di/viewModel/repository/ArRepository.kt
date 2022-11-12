package edu.nitt.delta.orientation22.di.viewModel.repository

import android.content.Context
import android.view.MotionEvent
import androidx.lifecycle.Lifecycle
import com.google.ar.core.Anchor
import com.google.ar.core.Session
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.models.Result
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.renderable.Renderable
import javax.inject.Inject

class ArRepository@Inject constructor(
    private val apiInterface: ApiInterface
) {


    fun hostAnchor(cloudAnchorNode: ArModelNode?,sceneView: ArSceneView?):Result<String> {
        try {
            var anchorId = ""
            val frame = sceneView!!.currentFrame
            if (!cloudAnchorNode!!.isAnchored) {
                cloudAnchorNode.anchor()
            }
            if (sceneView.arSession?.estimateFeatureMapQualityForHosting(frame!!.camera.pose) == Session.FeatureMapQuality.INSUFFICIENT) {
                return Result.build<String> { throw Exception("Unable To Host Anchor") }
            }
            cloudAnchorNode.hostCloudAnchor(10) { anchor: Anchor, success: Boolean ->
                if (success)
                    anchorId = anchor.cloudAnchorId
            }
            if (anchorId.isEmpty()) Result.build<String> { throw Exception("Unable To Host Anchor") }
            return Result.build { anchorId }
        } catch (e: Exception) {
            return Result.build<String> { throw e }
        }
    }

    fun loadModel(
        arSceneView:ArSceneView?,
        cloudAnchorNode: ArModelNode,
        onTapModel:((MotionEvent, Renderable?) -> Unit)?,
        context:Context,
        lifecycle: Lifecycle?,
    ):Result<ArModelNode> = try{
        Result.build{
            cloudAnchorNode.apply {
                parent = arSceneView
                isSmoothPoseEnable = false
                isVisible = true
                loadModelAsync(
                    context = context,
                    lifecycle = lifecycle,
                    glbFileLocation = "miyawaki.glb",
                    onLoaded ={
                        cloudAnchorNode.anchor()
                        onTap =onTapModel
                    }
                )
            }
        }
    } catch (e: Exception){
        Result.build<ArModelNode> { throw e }
    }
}
