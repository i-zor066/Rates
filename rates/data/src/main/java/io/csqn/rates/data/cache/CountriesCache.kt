package io.csqn.rates.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import io.csqn.rates.data.envelopes.CountryEnvelope
import io.csqn.rates.data.mappers.CurrencyOverrides
import javax.inject.Inject

class CountriesCache @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CountriesCacheType {

    init {
        CurrencyOverrides.Get.overrides.forEach { initialize(it.key, it.value) }
    }

    private fun initialize(
        currencyCode: String,
        countryEnvelope: CountryEnvelope
    ): CountryEnvelope {
        sharedPreferences.edit().putString(currencyCode, gson.toJson(countryEnvelope)).apply()
        return countryEnvelope
    }

    override suspend fun isCached(currencyCode: String): Boolean {
        return sharedPreferences.contains(currencyCode)
    }

    override suspend fun saveToCache(
        currencyCode: String,
        countryEnvelope: CountryEnvelope
    ): CountryEnvelope {
        sharedPreferences.edit().putString(currencyCode, gson.toJson(countryEnvelope)).apply()
        return countryEnvelope
    }

    override suspend fun getCountryFromCache(currencyCode: String): CountryEnvelope {
        val json = sharedPreferences.getString(currencyCode, "")
        return gson.fromJson(json, CountryEnvelope::class.java)
    }
}