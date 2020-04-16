package io.csqn.rates.data.repository

import io.csqn.rates.data.api.RatesApiType
import io.csqn.rates.data.mappers.RatesMapper
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.models.Rates
import javax.inject.Inject

class RatesRepository @Inject constructor(private val ratesApi: RatesApiType): RatesRepositoryType {

    override suspend fun getRates(baseCurrency: String): Rates {
        return RatesMapper.fromEnvelope(ratesApi.getRates(baseCurrency))
    }
}