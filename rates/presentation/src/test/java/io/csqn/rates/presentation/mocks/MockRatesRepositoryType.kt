package io.csqn.rates.presentation.mocks

import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.models.Rates
import io.csqn.rates.presentation.factories.RatesFactory
import io.reactivex.Single

class MockRatesRepositoryType : RatesRepositoryType {

    override fun getRates(baseCurrency: String): Single<Rates> {
        return Single.just(RatesFactory.create())
    }
}