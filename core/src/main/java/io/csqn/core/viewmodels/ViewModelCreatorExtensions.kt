package io.csqn.core.viewmodels

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@SuppressWarnings("MandatoryBracesIfStatements")
inline fun <reified T : ViewModel> Fragment.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

@SuppressWarnings("MandatoryBracesIfStatements")
inline fun <reified T : ViewModel> AppCompatActivity.getViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null) {
        ViewModelProvider(this).get(T::class.java)
    } else {
        ViewModelProvider(this, BaseViewModelFactory(creator)).get(T::class.java)
    }
}

@Suppress("UNCHECKED_CAST")
class BaseViewModelFactory<T>(val creator: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return creator() as T
    }
}