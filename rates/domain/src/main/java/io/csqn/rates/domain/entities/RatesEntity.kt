package io.csqn.rates.domain.entities

class RatesEntity(
    val baseRate: RateEntity,
    val rates: List<RateEntity>
)
