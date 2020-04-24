package io.csqn.rates.data.cache

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import io.csqn.rates.data.envelopes.RatesEnvelope
import io.reactivex.Single
import javax.inject.Inject


class RatesCache @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : RatesCacheType {

    override fun areRatesCached(): Boolean {
        Log.d("CACHING SNAFU", "areRatesCached: ${sharedPreferences.contains(RATES_ENVELOPE_KEY)}")
        return sharedPreferences.contains(RATES_ENVELOPE_KEY)
    }

    override fun saveToCache(ratesEnvelope: RatesEnvelope): Single<RatesEnvelope> {
        Log.d(
            "CACHING SNAFU",
            "saveToCache ratesEnvelope: $ratesEnvelope // ${gson.toJson(ratesEnvelope)}"
        )
        sharedPreferences.edit().putString(RATES_ENVELOPE_KEY, gson.toJson(ratesEnvelope)).apply()
        Log.d(
            "CACHING SNAFU",
            "saveToCache after // ratesEnvelope: ${areRatesCached()} // ${gson.toJson(ratesEnvelope)}"
        )
        return Single.create<RatesEnvelope> { emitter ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(ratesEnvelope)
            }
        }
    }

    override fun getCachedRatesEnvelope(): RatesEnvelope {
        val json = sharedPreferences.getString(RATES_ENVELOPE_KEY, "")
        Log.d("CACHING SNAFU", "getCachedRatesEnvelope json: $json")
        return gson.fromJson(json, RatesEnvelope::class.java)
    }

    override fun getCachedRatesEnvelopeSingle(): Single<RatesEnvelope> {
        return Single.create<RatesEnvelope> { emitter ->
            val json = sharedPreferences.getString(RATES_ENVELOPE_KEY, "")
            if (!emitter.isDisposed) {
                emitter.onSuccess(gson.fromJson(json, RatesEnvelope::class.java))
            }
        }
    }

    companion object {
        const val RATES_ENVELOPE_KEY = "rates_envelope_key"
    }
}