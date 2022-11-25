package edu.nitt.delta.orientation22.di.viewModel.repository

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.google.ar.core.Anchor
import com.google.ar.core.Session
import edu.nitt.delta.orientation22.ArActivity
import edu.nitt.delta.orientation22.ArHostActivity
import edu.nitt.delta.orientation22.ArMainActivity
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.di.api.ApiInterface
import edu.nitt.delta.orientation22.di.api.ResponseConstants
import edu.nitt.delta.orientation22.di.storage.SharedPrefHelper
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.models.auth.TokenRequestModel
import edu.nitt.delta.orientation22.models.game.Location
import edu.nitt.delta.orientation22.models.game.LocationRequest
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.renderable.Renderable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class ArRepository@Inject constructor(
    private val apiInterface: ApiInterface
) {
    @Inject
    lateinit var sharedPrefHelper: SharedPrefHelper
    fun hostAnchor(cloudAnchorNode: ArModelNode?,sceneView: ArSceneView?,ctx: CoroutineContext, context: Context):Result<String> {

        try {
            var anchorId = ""
            val frame = sceneView!!.currentFrame
            if (!cloudAnchorNode!!.isAnchored) {
                cloudAnchorNode.anchor()
            }
            if (sceneView.arSession?.estimateFeatureMapQualityForHosting(frame!!.camera.pose) == Session.FeatureMapQuality.INSUFFICIENT) {
                context.toast("Map Quality is inefficient")
                return Result.build<String> { throw Exception("Unable To Host Anchor1") }
            }
            cloudAnchorNode.hostCloudAnchor{ anchor: Anchor, success: Boolean ->
                if (success) {
                    anchorId = anchor.cloudAnchorId
                    ArHostActivity.anchorId = anchorId
                    Log.d("Hosting1",anchorId)
                    context.toast("Cloud Anchor hosted successfully. Anchor Id: $anchorId")
                    CoroutineScope(ctx).launch {
                        Log.d("Hosting2",anchorId)
                       var res= updateLocation(
                            LocationRequest(
                                id= ArMainActivity.locationId,
                                anchorHash = anchorId,
                                userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjEwNjEyMDEwNUBuaXR0LmVkdSIsIm5hbWUiOiJTQVJWRVNIIFIifQ.nkOJA30xb5mjI8BbTSz-t0nyQK9H8Dx8eIVAFthApaA",
                                latitude = ArHostActivity.latitude,
                                longitude = ArHostActivity.longitude,
                            ),
                           context = context,
                        )
                        Log.d("Hosting", res.toString());
                    }
                }
                Log.d("Hosting3",anchorId)
            }
            if (anchorId.isEmpty()) Result.build<String> {
                context.toast("Unable To Host Anchor2")
                throw Exception("Unable To Host Anchor2")
            }
            return Result.build { anchorId }
        } catch (e: Exception) {
            return Result.build<String> { throw Exception(ResponseConstants.ERROR) }
        }
    }

    fun resolveAnchor(cloudAnchorNode: ArModelNode?,code : String, context: Context):Result<String> = try {
        var isResolved = false
        cloudAnchorNode?.resolveCloudAnchor(code){ _, success: Boolean ->
            Log.d("resolve",code)
            if(success){
                cloudAnchorNode.isVisible = true
                isResolved = true
                Log.d("resolve", "Success")
                context.toast("Cloud Anchor resolved successfully.")
            }
        }
        if (!isResolved) {
            Result.build { throw Exception("Error occurred while resolving cloud anchor") }
            context.toast("Error occurred while resolving cloud anchor")
            Log.d("resolve", "Error occurred while resolving cloud anchor")
        }
        Result.build{ "Cloud Anchor resolved successfully" }
        } catch (e: Exception) {
        Log.d("resolve", ResponseConstants.ERROR)
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
        glbFileUrl: String,
        isHost: Boolean
    ):Result<ArModelNode> = try{
        Result.build{
            cloudAnchorNode.apply {
                parent = arSceneView
                isSmoothPoseEnable = false
                isVisible = true
                loadModelAsync(
                    context = context,
                    lifecycle = lifecycle,
                    glbFileLocation = glbFileUrl,
                    onLoaded ={
                        cloudAnchorNode.isVisible = isHost
                        cloudAnchorNode.anchor()
                        onTap = onTapModel
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

    suspend fun updateLocation(location:LocationRequest, context: Context) : Result<String> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.hostLocation(location)
        Log.d("Host","I am Called")
        if (response.message == ResponseConstants.SUCCESS){
            Log.d("Host","1")
            context.toast("Anchor details sent to backend successfully")
            Result.build { "Updated Successfully" }
        }
        else {
            Log.d("Host","2")
            context.toast(ResponseConstants.ERROR)
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }

    }catch (e:Exception){
        Log.d("Host",e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }

    suspend fun fetchLocations() : Result<List<Location>> = try {
        val token = sharedPrefHelper.token.toString()
        val response = apiInterface.getLocations(TokenRequestModel("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6IjEwNjEyMDEwNUBuaXR0LmVkdSIsIm5hbWUiOiJTQVJWRVNIIFIifQ.nkOJA30xb5mjI8BbTSz-t0nyQK9H8Dx8eIVAFthApaA"))
        if (response.message == ResponseConstants.SUCCESS){
            Log.d("Response", response.locations.toString())
            Result.build { response.locations }
        }
        else {
            Result.build { throw Exception(ResponseConstants.ERROR) }
        }

    }catch (e:Exception){
        Log.d("Exception", e.message.toString())
        Result.build { throw Exception(ResponseConstants.ERROR) }
    }
}
