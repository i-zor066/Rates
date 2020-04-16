package io.csqn.rates.domain.usecases

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.domain.entities.RatesEntity
import java.util.*
import javax.inject.Inject

class GetCombinedRatesDataUseCase @Inject constructor(
    private val countriesRepositoryType: CountriesRepositoryType,
    private val ratesRepositoryType: RatesRepositoryType
){
    suspend operator fun invoke(baseCurrency: String, baseRate: Double = 3.00): RatesEntity {
        val rates = ratesRepositoryType.getRates(baseCurrency)
        val base = countriesRepositoryType.getCountryData(rates.baseCurrency)
        val baseRateEntity = RateEntity(
            base.countryCode,
            base.currencyCode,
            base.currencyName,
            base.flag,
            baseRate
        )
        val rateEntityList = ArrayList<RateEntity>()

        rates.rates.forEach {
            val currency = countriesRepositoryType.getCountryData(it.key)
            val rate = baseRateEntity.rate.times(it.value)
            val entity = RateEntity(
                currency.countryCode,
                currency.currencyCode,
                currency.currencyName,
                currency.flag,
                rate
            )
            rateEntityList.add(entity)
        }
        return RatesEntity(baseRateEntity, rateEntityList)
    }
}