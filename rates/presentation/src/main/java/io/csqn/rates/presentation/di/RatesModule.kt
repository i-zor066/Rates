package io.csqn.rates.presentation.di

import dagger.Binds
import dagger.Module
import io.csqn.explorer.presentation.di.RatesScope
import io.csqn.rates.data.api.CountriesApi
import io.csqn.rates.data.api.CountriesApiType
import io.csqn.rates.data.api.RatesApi
import io.csqn.rates.data.api.RatesApiType
import io.csqn.rates.data.cache.CountriesCache
import io.csqn.rates.data.cache.CountriesCacheType
import io.csqn.rates.data.repository.CountriesRepository
import io.csqn.rates.data.repository.RatesRepository
import io.csqn.rates.domain.CountriesRepositoryType
import io.csqn.rates.domain.RatesRepositoryType

@Module(includes = [RatesModule.RatesAbstractModule::class])
object RatesModule {

    @Module
    abstract class RatesAbstractModule {

        @Binds
        @RatesScope
        abstract fun provideRatesApiType(
            ratesApi: RatesApi
        ): RatesApiType

        @Binds
        @RatesScope
        abstract fun provideRatesRepositoryType(
            ratesRepository: RatesRepository
        ): RatesRepositoryType

        @Binds
        @RatesScope
        abstract fun provideCountriesApiType(
            countriesApi: CountriesApi
        ): CountriesApiType

        @Binds
        @RatesScope
        abstract fun provideCountriesRepositoryType(
            countriesRepository: CountriesRepository
        ): CountriesRepositoryType

        @Binds
        @RatesScope
        abstract fun provideCountriesCacheType(
            countriesCache: CountriesCache
        ): CountriesCacheType
    }
}