package io.csqn.rates.domain.factories

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