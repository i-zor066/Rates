package io.csqn.rates.domain.mappers

import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.domain.models.Country

object RateEntityMapper {
    fun fromModel(model: Country, rate: Double): RateEntity {
        return RateEntity(
            model.countryCode,
            model.currencyCode,
            model.currencyName,
            model.flag,
            rate
        )
    }
}
