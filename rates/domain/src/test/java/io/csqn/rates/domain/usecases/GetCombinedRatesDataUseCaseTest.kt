package io.csqn.rates.domain.usecases

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.mocks.MockCountriesRepositoryType
import io.csqn.rates.domain.mocks.MockRatesRepositoryType
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCombinedRatesDataUseCaseTest {
    private lateinit var countriesRepositoryType: CountriesRepositoryType
    private lateinit var ratesRepositoryType: RatesRepositoryType

    @Before
    fun setUp() {
        countriesRepositoryType = MockCountriesRepositoryType()
        ratesRepositoryType = MockRatesRepositoryType()
    }

    @Test
    fun `fetch combined data for base currency`() {
        val entity = runBlocking {
            GetCombinedRatesDataUseCase(
                countriesRepositoryType,
                ratesRepositoryType).invoke("GBP", 0.25)
        }
        assertNotNull(entity)
        assertEquals("currencyName", entity.baseRate.name)
        assertEquals("currencyCode", entity.baseRate.currencyCode)
        assertEquals("countryCode", entity.baseRate.code)
        assertEquals("flag", entity.baseRate.flagUrl)
        assertEquals(0.25, entity.baseRate.rate, 0.00)

        assertEquals("currencyName", entity.rates[0].name)
        assertEquals("currencyCode", entity.rates[0].currencyCode)
        assertEquals("countryCode", entity.rates[0].code)
        assertEquals("flag", entity.rates[0].flagUrl)
        assertEquals(0.0025, entity.rates[0].rate, 0.00)

        assertEquals("currencyName", entity.rates[1].name)
        assertEquals("currencyCode", entity.rates[1].currencyCode)
        assertEquals("countryCode", entity.rates[1].code)
        assertEquals("flag", entity.rates[1].flagUrl)
        assertEquals(0.005, entity.rates[1].rate, 0.00)
    }
}
