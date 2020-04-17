package io.csqn.rates.data.mappers

import io.csqn.rates.data.BuildConfig
import io.csqn.rates.data.cache.CurrencyOverrides
import io.csqn.rates.data.envelopes.CountryEnvelope
import io.csqn.rates.domain.models.Country

object CountryMapper {
    private const val baseFlagUrl = BuildConfig.MEDIA_URL
    private const val flagExtension = ".png"
    fun fromEnvelope(countryEnvelope: CountryEnvelope): Country {
        return Country(
            countryEnvelope.alpha2Code,
            countryEnvelope.currencies[0].code,
            countryEnvelope.currencies[0].name,
            getFlagUrl(countryEnvelope.alpha2Code)
        )
    }

    private fun getFlagUrl(countryCode: String):String {
        return when (countryCode) {
            CurrencyOverrides.EUR.countryEnvelope.alpha2Code -> CurrencyOverrides.EUR.currencyFlag
            else -> "${getBaseFlagUrl()}${countryCode.toLowerCase()}$flagExtension"
        }
    }

    fun getBaseFlagUrl() = baseFlagUrl
}