package io.csqn.core.viewmodels

import androidx.lifecycle.LiveData
import io.csqn.core.livedata.Event

interface BaseStates {
    val error: LiveData<Event<Throwable>>
    val isLoading: LiveData<Event<Boolean>>
}