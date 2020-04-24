package io.csqn.rates.presentation.viewmodels.input

interface RatesViewModelInputs {
    fun onViewCreated()
    fun updateBaseRate(currencyCode: String, value: Double)
    fun switchBaseCurrency(currencyCode: String, value: Double)
    fun onDone()
}