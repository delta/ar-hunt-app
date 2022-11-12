package edu.nitt.delta.orientation22.di.viewModel.uiState

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.Lifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.nitt.delta.orientation22.models.Result
import edu.nitt.delta.orientation22.di.viewModel.repository.ArRepository
import edu.nitt.delta.orientation22.di.viewModel.BaseViewModel
import edu.nitt.delta.orientation22.di.viewModel.actions.ArAction
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


    override fun doAction(action: ArAction): Any = when(action){
        is ArAction.HostAnchor -> hostModel(action.sceneView,action.cloudAnchorNode)
        is ArAction.LoadModel -> loadModel(action.onTapModel,
            action.context,
            action.lifecycle,
            action.cloudAnchorNode,
            action.sceneView
        )
    }

    private fun hostModel(sceneView: ArSceneView, cloudAnchorNode: ArModelNode) = launch {
        when(val res=arRepository.hostAnchor(cloudAnchorNode,sceneView)){
            is Result.Value -> mutableSuccess.value =res.value
            is Result.Error -> mutableError.value= res.exception.message
        }
    }

    private fun loadModel(
        onTapModel:((MotionEvent, Renderable?) -> Unit)?,
        context: Context,
        lifecycle: Lifecycle?,
        cloudAnchorNode: ArModelNode,
        sceneView: ArSceneView
    )= CoroutineScope(Dispatchers.Main).launch{
        when (val res = arRepository.loadModel(sceneView,cloudAnchorNode, onTapModel, context, lifecycle)){
            is Result.Value -> Log.v(res.value.name,res.toString())
            is Result.Error -> Log.v("InArStateViewModel",res.exception.message.toString())
        }
    }
}
