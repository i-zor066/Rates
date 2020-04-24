package io.csqn.core.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.csqn.core.livedata.Event
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.*
import kotlin.concurrent.schedule
import kotlin.concurrent.timerTask
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), BaseStates {

    private val _error = MutableLiveData<Event<Throwable>>()
    private val _isLoading = MutableLiveData<Event<Boolean>>()
    private val compositeDisposable = CompositeDisposable()

    val baseStates:BaseStates by lazy { this@BaseViewModel }

    protected fun rxError(exception: Throwable) {
        Log.e(this.javaClass.simpleName, exception.message?: "rxException exception, no msg")
        _error.value = Event(exception)
    }

    override val error: LiveData<Event<Throwable>>
        get() = _error

    override val isLoading: LiveData<Event<Boolean>>
        get() = _isLoading

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun Disposable?.addToComposite(): Disposable? {
        if (this != null)
            compositeDisposable.add(this)
        return this
    }

    protected fun clearComposite() {
        compositeDisposable.clear()
    }
}
