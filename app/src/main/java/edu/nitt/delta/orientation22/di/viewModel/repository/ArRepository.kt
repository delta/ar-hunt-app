package edu.nitt.delta.orientation22.di.viewModel.repository

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.Lifecycle
import com.google.ar.core.Anchor
import com.google.ar.core.Session
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.renderable.Renderable
import javax.inject.Inject

class ArRepository@Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
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
            cloudAnchorNode.hostCloudAnchor{ anchor: Anchor, success: Boolean ->
                if (success)
                    anchorId = anchor.cloudAnchorId
            }
            if (anchorId.isEmpty()) Result.build<String> { throw Exception("Unable To Host Anchor") }
            return Result.build { anchorId }
        } catch (e: Exception) {
            return Result.build<String> { throw Exception(ResponseConstants.ERROR) }
        }
    }

    fun resolveAnchor(cloudAnchorNode: ArModelNode?,code : String):Result<String> = try {
        var isResolved = false
        Log.d("Resolve","inside repo")
        cloudAnchorNode?.resolveCloudAnchor(code){ _, success: Boolean ->
            Log.d("Resolve","Inside cloudanchor resolve")
            if(success){
                Log.d("Resolve","Done resolving")
                cloudAnchorNode.isVisible = true
                isResolved = true
            }
        }
        if (!isResolved)
            Result.build { throw Exception("Error occurred while resolving cloud anchor") }
        Result.build{ "Cloud Anchor resolved successfully" }
        } catch (e: Exception) {
            Log.d("Resolve",e.message.toString())
            Result.build<String> { throw Exception(ResponseConstants.ERROR) }
    }


    fun resetAnchor(cloudAnchorNode: ArModelNode?): Result<String> = try {
        if(cloudAnchorNode!!.isAnchored) {
            cloudAnchorNode.detachAnchor()
        } else {
            Result.build { throw Exception("Cloud anchor is not anchored") }
        }
            Result.build { "Cloud anchor detached successfully" }
        }catch (e:Exception){
            Result.build<String> { throw Exception(ResponseConstants.ERROR) }
    }

    fun loadModel(
        arSceneView:ArSceneView?,
        cloudAnchorNode: ArModelNode,
        onTapModel:((MotionEvent, Renderable?) -> Unit)?,
        context:Context,
        lifecycle: Lifecycle?,
        glbUrl : String
    ):Result<ArModelNode> = try{
        Result.build{
            cloudAnchorNode.apply {
                parent = arSceneView
                isSmoothPoseEnable = false
                isVisible = false
                isRotationEditable = false
                isScaleEditable = false
                isPositionEditable = false
                loadModelAsync(
                    context = context,
                    lifecycle = lifecycle,
                    glbFileLocation = glbUrl,
                    autoAnimate = false,
                    onLoaded ={
                        cloudAnchorNode.anchor()
                        onTap =onTapModel
                    }
                )
            }
        }
    } catch (e: Exception){
        Result.build<ArModelNode> { throw Exception(ResponseConstants.ERROR) }
    }

    suspend fun postAnswer() : Result<String> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.postAnswer(TokenRequestModel(token))
        if (response.message == ResponseConstants.SUCCESS){
            Result.build { "Level Completed" }
        }
        else {
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }

    }catch (e:Exception){
       Result.build { throw Exception(ResponseConstants.ERROR) }
    }
}
