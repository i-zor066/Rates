package io.csqn.rates.data.repository

import io.csqn.rates.data.api.RatesApiType
import io.csqn.rates.data.mappers.RatesMapper
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.models.Rates
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RatesRepository @Inject constructor(private val ratesApi: RatesApiType): RatesRepositoryType {

    override fun getRates(baseCurrency: String): Single<Rates> {
        return ratesApi.getRates(baseCurrency).map { RatesMapper.fromEnvelope(it) }
    }
}