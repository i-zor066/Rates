package io.csqn.rates.data.mappers

import androidx.test.filters.SmallTest
import io.csqn.rates.data.factories.CountryEnvelopeFactory
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@SmallTest
class CountryMapperTest {
    private val mockBaseUrl = "MOCK_BASE"

    @Before
    fun beforeTests() {
        mockkObject(CountryMapper)
        every { CountryMapper.getBaseFlagUrl() } returns mockBaseUrl
    }

    @Test
    fun `map model to entity`() {
        val nonEuEntity =  CountryMapper.fromEnvelope(CountryEnvelopeFactory.create())
        val euEntity =  CountryMapper.fromEnvelope(CountryEnvelopeFactory.create(true))

        assertEquals("alpha2Code", nonEuEntity.countryCode)
        assertEquals("GBP", nonEuEntity.currencyCode)
        assertEquals("MOCK_BASEalpha2code.png", nonEuEntity.flag)

        assertEquals("EU", euEntity.countryCode)
        assertEquals("EUR", euEntity.currencyCode)
        assertEquals("https://flagpedia.net/data/org/w1160/eu.png", euEntity.flag)
    }

    @After
    fun afterTests() {
        unmockkAll()
    }
}