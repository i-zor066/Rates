package io.csqn.rates.domain.mappers

import androidx.test.filters.SmallTest
import io.csqn.rates.domain.factories.CountryFactory
import org.junit.Assert.*
import org.junit.Test


@SmallTest
class RateEntityMapperTest {

    @Test
    fun `map envelope to model`() {
        val model = RateEntityMapper.fromCountryModel(CountryFactory.create(), 0.03)

        assertEquals("currencyName", model.name)
        assertEquals("countryCode", model.code)
        assertEquals("flag", model.flagUrl)
        assertEquals("currencyCode", model.currencyCode)
        assertEquals(0.03, model.rate, 0.00)
    }
}