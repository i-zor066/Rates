package io.csqn.rates.domain.usecases

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.domain.mappers.RateEntityMapper
import io.csqn.rates.domain.models.Rates
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetCombinedRatesDataUseCase @Inject constructor(
    private val countriesRepositoryType: CountriesRepositoryType,
    private val ratesRepositoryType: RatesRepositoryType
) {

    fun invoke(baseCurrency: String): Flowable<RatesEntity> {

        return ratesRepositoryType.getRates(baseCurrency).flatMap { rates ->

            val rateEntityListSingle = Single.zip(
                getRatesList(rates)
            ) { t: Array<Any> -> t.map { it as RateEntity } }

            Single.zip(
                getBaseRateEntitySingle(rates),
                rateEntityListSingle,
                BiFunction { t1: RateEntity, t2: List<RateEntity> -> RatesEntity(t1, t2) }
            )
        }.toFlowable()
    }

    private fun getRatesList(rates: Rates): List<Single<RateEntity>> {
        return rates.rates.map {
            countriesRepositoryType.getCountryData(it.key).map { currency ->
                RateEntityMapper.fromCountryModel(currency, it.value)
            }
        }
    }

    private fun getBaseRateEntitySingle(rates: Rates): Single<RateEntity> {
        return countriesRepositoryType.getCountryData(rates.baseCurrency).map { country ->
            RateEntityMapper.fromCountryModel(country)
        }
    }

}
