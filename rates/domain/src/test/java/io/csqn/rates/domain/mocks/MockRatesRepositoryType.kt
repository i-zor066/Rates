package io.csqn.rates.domain.mocks

import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.factories.RatesFactory
import io.csqn.rates.domain.models.Rates
import io.reactivex.Single

class MockRatesRepositoryType : RatesRepositoryType {

    override fun getRates(baseCurrency: String): Single<Rates> {
        return Single.just(RatesFactory.create())
    }
}