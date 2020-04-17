package io.csqn.rates.domain.factories

import io.csqn.rates.domain.entities.RatesEntity

object RatesEntityFactory {
    fun create(): RatesEntity {
        return RatesEntity(
            RateEntityFactory.create(true),
            listOf(
                RateEntityFactory.create(),
                RateEntityFactory.create(),
                RateEntityFactory.create(),
                RateEntityFactory.create(),
                RateEntityFactory.create()
            )
        )
    }
}
