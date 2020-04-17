package io.csqn.rates.domain.usecases

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.domain.mappers.RateEntityMapper
import java.util.*
import javax.inject.Inject

class GetCombinedRatesDataUseCase @Inject constructor(
    private val countriesRepositoryType: CountriesRepositoryType,
    private val ratesRepositoryType: RatesRepositoryType
){
    suspend operator fun invoke(baseCurrency: String, baseRate: Double): RatesEntity {
        val rates = ratesRepositoryType.getRates(baseCurrency)
        val base = countriesRepositoryType.getCountryData(rates.baseCurrency)
        val baseRateEntity = RateEntityMapper.fromModel(base, baseRate)
        val rateEntityList = ArrayList<RateEntity>()

        rates.rates.forEach {
            val currency = countriesRepositoryType.getCountryData(it.key)
            val rate = baseRateEntity.rate.times(it.value)
            val entity = RateEntityMapper.fromModel(currency, rate)
            rateEntityList.add(entity)
        }
        return RatesEntity(baseRateEntity, rateEntityList)
    }
}