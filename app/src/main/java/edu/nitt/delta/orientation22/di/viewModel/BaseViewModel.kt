package edu.nitt.delta.orientation22.di.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<Action> : ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.IO

    protected val mutableError = mutableStateOf<String?>(null)
    val error:String?
        get() = mutableError.value

    protected val mutableSuccess = mutableStateOf<String?>(null)
    val success: String?
        get() = mutableSuccess.value

    abstract fun doAction(action: Action): Any
}
