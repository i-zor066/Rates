package io.csqn.rates.presentation.factories

import io.csqn.rates.domain.entities.RateEntity

object RateEntityFactory {
    fun create(isBaseRate: Boolean = false): RateEntity {
        return RateEntity(
            createBaseParam(isBaseRate, "code"),
            createBaseParam(isBaseRate, "currencyCode"),
            createBaseParam(isBaseRate, "currencyName"),
            createBaseParam(isBaseRate, "flagUrl"),
            if (isBaseRate) 0.01 else 0.02
        )
    }

    private fun createBaseParam(isBase: Boolean, param: String): String {
        return if (isBase) "$isBase${param.capitalize()}" else "$param"
    }
}