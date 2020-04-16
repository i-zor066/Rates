package io.csqn.rates.domain

import io.csqn.rates.domain.models.Country

interface CountriesRepositoryType {
    suspend fun getCountryData(code: String): Country
}