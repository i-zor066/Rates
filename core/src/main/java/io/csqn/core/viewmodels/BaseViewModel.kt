package io.csqn.core.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.csqn.core.livedata.Event
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job

open class BaseViewModel : ViewModel(), BaseStates {

    private val _error = MutableLiveData<Event<Throwable>>()
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    val baseStates:BaseStates by lazy { this@BaseViewModel }

    protected fun Job.setLoadingState(isLoading: Boolean = true): Job {
        _isLoading.value = Event(isLoading)
        invokeOnCompletion { _isLoading.value = Event(false) }
        return this
    }

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(this.javaClass.simpleName, exception.message?: "Coroutine exception, no msg")
        _error.value = Event(exception)
    }

    override val error: LiveData<Event<Throwable>>
        get() = _error

    override val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading
}