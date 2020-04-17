package io.csqn.rates.presentation.mocks

import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.presentation.factories.RatesFactory
import io.csqn.rates.domain.models.Rates

class MockRatesRepositoryType : RatesRepositoryType {
    override suspend fun getRates(baseCurrency: String): Rates {
        return RatesFactory.create()
    }
}