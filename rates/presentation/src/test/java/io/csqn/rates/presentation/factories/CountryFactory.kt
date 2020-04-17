package io.csqn.rates.presentation.factories

import io.csqn.rates.domain.models.Country

object CountryFactory {
    fun create(): Country {
        return Country(
            "countryCode",
            "currencyCode",
            "currencyName",
            "flag"
        )
    }
}