package io.csqn.rates.domain.factories

import io.csqn.rates.domain.models.Rates

object RatesFactory {
    fun create(): Rates {
        return Rates(
            "baseCurrency",
            hashMapOf(
                "currency1" to 0.01,
                "currency2" to 0.02
            )
        )
    }
}