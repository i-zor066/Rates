package io.csqn.rates.data.repository

import android.util.Log
import io.csqn.rates.data.api.RatesApiType
import io.csqn.rates.data.cache.RatesCacheType
import io.csqn.rates.data.envelopes.RatesEnvelope
import io.csqn.rates.data.mappers.RatesMapper
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.models.Rates
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RatesRepository @Inject constructor(
    private val ratesApi: RatesApiType,
    private val ratesCacheType: RatesCacheType
) : RatesRepositoryType {

    override fun getRates(baseCurrency: String): Single<Rates> {
        return ratesApi.getRates(baseCurrency)
            .doOnSuccess {
                if (!ratesCacheType.areRatesCached()) ratesCacheType.saveToCache(it)
            }
            .onErrorReturn { calculateRatesFromBaseValue(baseCurrency) }
            .map { RatesMapper.fromEnvelope(it) }
    }

    private fun calculateRatesFromBaseValue(baseCurrency: String):RatesEnvelope {
        Log.e("getRates() error", "returning cached value")
        val envelope = ratesCacheType.getCachedRatesEnvelope()

        if (baseCurrency.equals(envelope.baseCurrency)) return envelope

        val baseModifier = envelope.rates[baseCurrency]?:1.00
        val newRates = LinkedHashMap<String, Double> ()
        envelope.rates.filter { it.key != baseCurrency }.forEach {
            Log.d("MAPPING", "${it.key} // ${it.value}" )
            newRates[it.key] = it.value.div(baseModifier)
        }

        return RatesEnvelope(baseCurrency, newRates)
    }
}
