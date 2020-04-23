package io.csqn.rates.domain.entities

data class RatesEntity(
    val baseRate: RateEntity,
    val rates: List<RateEntity>
)
