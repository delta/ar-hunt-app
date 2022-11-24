package edu.nitt.delta.orientation22.di.viewModel.uiState

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.ArHostActivity
import edu.nitt.delta.orientation22.ArMainActivity
import edu.nitt.delta.orientation22.compose.toast
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.di.viewModel.repository.ArRepository
import edu.nitt.delta.orientation22.di.viewModel.BaseViewModel
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
import edu.nitt.delta.orientation22.models.game.Location
import edu.nitt.delta.orientation22.models.game.LocationRequest
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.renderable.Renderable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ArStateViewModel @Inject constructor(
    private val arRepository: ArRepository
):BaseViewModel<ArAction>() {


    var locationData = mutableStateOf<List<Location>>(emptyList())

    var locationId = mutableStateOf<Int>(5)
    var glbUrl : String = "miyawaki.glb"


    override fun doAction(action: ArAction): Any = when(action){
        is ArAction.HostAnchor -> hostModel(action.sceneView,action.cloudAnchorNode, action.context)
        is ArAction.ResolveAnchor -> resolveModel(action.cloudAnchorNode,action.code, action.context)
        is ArAction.ResetAnchor -> resetModel(action.cloudAnchorNode)
        is ArAction.LoadModel -> loadModel(action.onTapModel,
            action.context,
            action.lifecycle,
            action.cloudAnchorNode,
            action.sceneView,
            action.gldFileUrl,
            action.isHost,
        )
        is ArAction.PostAnswer -> postAnswer()
        is ArAction.FetchLocation -> fetchLocation()
        is ArAction.UpdateLocation -> updateLocation(action.location, action.context)
    }

    private fun hostModel(sceneView: ArSceneView, cloudAnchorNode: ArModelNode, context: Context) = launch {
        when(val res=arRepository.hostAnchor(cloudAnchorNode,sceneView,this.coroutineContext, context = context)){
            is Result.Value -> {
                mutableSuccess.value = res.value
                Log.d("Hosting", res.value)
            }
            is Result.Error -> {
                mutableError.value= res.exception.message
                Log.d("HostingErr", res.exception.message.toString())
            }
        }
    }

    private fun resolveModel(cloudAnchorNode: ArModelNode,code:String, context: Context) = launch {
        when(val res =  arRepository.resolveAnchor(cloudAnchorNode,code, context = context)){
            is Result.Value -> mutableSuccess.value = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

    private fun resetModel(cloudAnchorNode: ArModelNode) = launch {
        when(val res =  arRepository.resetAnchor(cloudAnchorNode)){
            is Result.Value -> mutableSuccess.value = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }
    private fun loadModel(
        onTapModel:((MotionEvent, Renderable?) -> Unit)?,
        context: Context,
        lifecycle: Lifecycle?,
        cloudAnchorNode: ArModelNode,
        sceneView: ArSceneView,
        glbFileUrl: String,
        isHost: Boolean,
    )= CoroutineScope(Dispatchers.Main).launch{
        when (val res = arRepository.loadModel(sceneView,cloudAnchorNode, onTapModel, context, lifecycle, glbFileUrl, isHost)){
            is Result.Value -> Log.v(res.value.name,res.toString())
            is Result.Error -> Log.v("InArStateViewModel",res.exception.message.toString())
        }
    }

    private fun postAnswer() = launch {
        when(val res =  arRepository.postAnswer()){
            is Result.Value -> mutableSuccess.value = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

    private fun fetchLocation() = launch {
        when(val res = arRepository.fetchLocations()){
            is Result.Value-> locationData.value = res.value
            is Result.Error -> mutableError.value = res.exception.message
        }
    }

    private fun updateLocation(req:LocationRequest, context: Context) = launch {
        when(val res = arRepository.updateLocation(req, context = context)){
            is Result.Value -> {
                mutableSuccess.value = res.value
            }
            is Result.Error -> {
                mutableError.value = res.exception.message
            }
        }
    }
}

