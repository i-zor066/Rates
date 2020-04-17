package io.csqn.rates.data.mappers

import androidx.test.filters.SmallTest
import io.csqn.rates.data.factories.RatesEnvelopeFactory
import org.junit.Assert.*
import org.junit.Test

@SmallTest
class RatesMapperTest {

    @Test
    fun `map envelope to model`() {
        val model = RatesMapper.fromEnvelope(RatesEnvelopeFactory.create())

        assertEquals("baseCurrency", model.baseCurrency)
        assertEquals(true, model.rates.containsKey("currency1"))
        assertEquals(true, model.rates.containsKey("currency2"))
        assertEquals(0.01, model.rates["currency1"])
        assertEquals(0.02, model.rates["currency2"])
    }
}
