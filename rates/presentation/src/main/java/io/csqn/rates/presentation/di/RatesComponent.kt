package io.csqn.explorer.presentation.di

import dagger.Component
import io.csqn.core.di.CoreComponent
import io.csqn.rates.presentation.RatesEnvironment
import io.csqn.rates.presentation.di.RatesModule


@RatesScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [RatesModule::class]
)
interface RatesComponent {
    fun environment(): RatesEnvironment
}