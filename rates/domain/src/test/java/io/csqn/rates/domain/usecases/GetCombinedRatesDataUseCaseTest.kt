package io.csqn.rates.domain.usecases

import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.RatesRepositoryType
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.domain.factories.RatesEntityFactory
import io.csqn.rates.domain.mocks.MockCountriesRepositoryType
import io.csqn.rates.domain.mocks.MockRatesRepositoryType
import io.reactivex.subscribers.TestSubscriber
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
        val ratesEntity = TestSubscriber<RatesEntity>()
        ratesEntity.onNext(RatesEntityFactory.create())
        val useCase = GetCombinedRatesDataUseCase(countriesRepositoryType,ratesRepositoryType)
        useCase.invoke("code")
        ratesEntity.assertValue{it.baseRate.code == "trueCode"}
        ratesEntity.assertValue{it.baseRate.currencyCode == "trueCurrencyCode"}
        ratesEntity.assertValue{it.baseRate.name == "trueCurrencyName"}
        ratesEntity.assertValue{it.baseRate.flagUrl == "trueFlagUrl"}
        ratesEntity.assertValue{it.baseRate.rate == 0.01}

        ratesEntity.assertValue{it.rates[0].code == "code"}
        ratesEntity.assertValue{it.rates[0].currencyCode == "currencyCode"}
        ratesEntity.assertValue{it.rates[0].name == "currencyName"}
        ratesEntity.assertValue{it.rates[0].flagUrl == "flagUrl"}
        ratesEntity.assertValue{it.rates[0].rate == 0.02}
    }
}
