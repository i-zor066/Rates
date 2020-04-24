package io.csqn.rates.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import io.csqn.rates.data.envelopes.CountryEnvelope
import io.reactivex.Single
import javax.inject.Inject

class CountriesCache @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : CountriesCacheType {

    init {
        CurrencyOverrides.Get.overrides.forEach {
            sharedPreferences.edit().putString(it.key, gson.toJson(it.value)).apply()
        }
    }

    override fun isCachedSingle(currencyCode: String): Single<Boolean> {
        return Single.create<Boolean> {emitter ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(sharedPreferences.contains(currencyCode))
            }
        }
    }

    override fun isCached(currencyCode: String): Boolean {
        return sharedPreferences.contains(currencyCode)
    }

    override  fun saveToCache(
        currencyCode: String,
        countryEnvelope: CountryEnvelope
    ): Single<CountryEnvelope> {
        return Single.create<CountryEnvelope> {emitter ->
            sharedPreferences.edit().putString(currencyCode, gson.toJson(countryEnvelope)).apply()
            if (!emitter.isDisposed) {
               emitter.onSuccess(countryEnvelope)
            }
    }}

    override fun getCountryFromCache(currencyCode: String): Single<CountryEnvelope> {
        return Single.create<CountryEnvelope> { emitter ->
            val json = sharedPreferences.getString(currencyCode, "")
            emitter.onSuccess(gson.fromJson(json, CountryEnvelope::class.java))
        }
    }
}
