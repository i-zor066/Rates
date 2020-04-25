package io.csqn.rates.data.cache

import android.content.SharedPreferences
import com.google.gson.Gson
import io.csqn.rates.data.envelopes.RatesEnvelope
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RatesCache @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : RatesCacheType {

    override fun shouldWriteToCache(): Boolean {
        return sharedPreferences.contains(RATES_ENVELOPE_KEY) && isTimestampExpired()
    }

    override fun saveToCache(ratesEnvelope: RatesEnvelope): Single<RatesEnvelope> {
        sharedPreferences.edit().putString(RATES_ENVELOPE_KEY, gson.toJson(ratesEnvelope)).apply()
        saveTimeStamp()
        return Single.create<RatesEnvelope> { emitter ->
            if (!emitter.isDisposed) {
                emitter.onSuccess(ratesEnvelope)
            }
        }
    }

    private fun saveTimeStamp() {
        sharedPreferences.edit().putLong(CACHED_TIMESTAMP_KEY, System.currentTimeMillis()).apply()
    }

    private fun isTimestampExpired(): Boolean {
        val timeStamp = sharedPreferences.getLong(CACHED_TIMESTAMP_KEY, 0L)
        return System.currentTimeMillis() - timeStamp > TimeUnit.MINUTES.toMillis(CACHE_TTL_MINUTES)
    }

    override fun getCachedRatesEnvelope(): RatesEnvelope {
        val json = sharedPreferences.getString(RATES_ENVELOPE_KEY, "")
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
        const val CACHED_TIMESTAMP_KEY = "cached_timestamp_key"
        const val CACHE_TTL_MINUTES = 15L
    }
}