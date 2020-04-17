package io.csqn.rates.domain.mocks

import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.factories.RatesFactory
import io.csqn.rates.domain.models.Rates

class MockRatesRepositoryType : RatesRepositoryType {
    override suspend fun getRates(baseCurrency: String): Rates {
        return RatesFactory.create()
    }
}