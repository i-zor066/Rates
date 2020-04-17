package io.csqn.rates.presentation.factories

import io.csqn.rates.domain.usecases.GetCombinedRatesDataUseCase
import io.csqn.rates.presentation.RatesEnvironment
import io.csqn.rates.presentation.mocks.MockCountriesRepositoryType
import io.csqn.rates.presentation.mocks.MockRatesRepositoryType

object RatesEnvironmentFactory {

    fun create(): RatesEnvironment {
        return RatesEnvironment(
            GetCombinedRatesDataUseCase(
                MockCountriesRepositoryType(),
                MockRatesRepositoryType()
            )
        )
    }
}