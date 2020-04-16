package io.csqn.explorer.presentation.di

import io.csqn.core.di.CoreComponent
import javax.inject.Singleton

@Singleton
object RatesComponentManager {
    private var component: RatesComponent? = null

    fun build(coreComponent: CoreComponent): RatesComponent {
        if (component == null) {
            component = DaggerRatesComponent.builder()
                .coreComponent(coreComponent)
                .build()
        }
        return component as RatesComponent
    }

    fun destroy() {
        component = null
    }
}