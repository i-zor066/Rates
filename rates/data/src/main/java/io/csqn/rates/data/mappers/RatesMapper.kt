package io.csqn.rates.data.mappers

import io.csqn.rates.data.envelopes.RatesEnvelope
import io.csqn.rates.domain.models.Rates

object RatesMapper {
    fun fromEnvelope(envelope: RatesEnvelope): Rates {
        return Rates(envelope.baseCurrency, envelope.rates)
    }
}