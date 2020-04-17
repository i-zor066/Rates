package io.csqn.rates.presentation.viewmodels.input

interface RatesViewModelInputs {
    fun onViewCreated()
    fun onValueEdited(currencyCode: String, value: Double)
    fun onKeyboardVisibilityChange(isOpen: Boolean)
}