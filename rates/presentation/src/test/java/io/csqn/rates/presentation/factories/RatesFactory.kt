package io.csqn.rates.presentation.factories

import io.csqn.rates.domain.models.Rates

object RatesFactory {
    fun create(): Rates {
        return Rates(
            "baseCurrency",
            linkedMapOf(
                "currency1" to 0.01,
                "currency2" to 0.02
            )
        )
    }
}
