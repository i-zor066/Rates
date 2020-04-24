package io.csqn.rates.domain

import io.csqn.rates.domain.models.Country
import io.reactivex.Single

interface CountriesRepositoryType {
    fun getCountryData(code: String): Single<Country>
}