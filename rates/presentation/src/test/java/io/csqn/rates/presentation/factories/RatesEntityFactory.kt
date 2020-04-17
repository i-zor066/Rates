package io.csqn.rates.presentation.factories

import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.presentation.factories.RateEntityFactory

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
