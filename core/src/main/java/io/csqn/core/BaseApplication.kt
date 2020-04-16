package io.csqn.core

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import io.csqn.core.di.CoreComponent
import io.csqn.core.di.CoreModule
import io.csqn.core.di.DaggerCoreComponent

class BaseApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.builder()
            .coreModule(CoreModule(this))
            .build()
    }

    companion object {
        fun coreComponent(context: Context) =
            (context.applicationContext as BaseApplication).coreComponent
    }
}

fun AppCompatActivity.coreComponent() = BaseApplication.coreComponent(this)
