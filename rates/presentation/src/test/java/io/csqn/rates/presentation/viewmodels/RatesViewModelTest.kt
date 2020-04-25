package io.csqn.rates.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import io.csqn.rates.presentation.factories.RatesEnvironmentFactory
import io.csqn.test.getOrAwaitValue
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@SmallTest
class RatesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpTests() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler{ Schedulers.trampoline()}
    }

    private val environment = RatesEnvironmentFactory.create()

    @Test
    fun `hide keyboard when value edited`() {
        val viewModel = RatesViewModel(environment)
        viewModel.inputs.switchBaseCurrency("GBP", 0.75)
        assertNotNull(viewModel.outputs.hideKeyboard)
    }

    @Test
    fun `load data when view created`() {
        val viewModel = RatesViewModel(environment)
        viewModel.onViewCreated()
        assertNotNull(viewModel.outputs.updateBaseRate)
        assertEquals ("countryCode", viewModel.outputs.updateBaseRate.getOrAwaitValue().peek().code)
        assertEquals ("currencyCode", viewModel.outputs.updateBaseRate.getOrAwaitValue().peek().currencyCode)
        assertEquals (1.0, viewModel.outputs.updateBaseRate.getOrAwaitValue().peek().rate, 0.0)

        assertEquals ("countryCode", viewModel.outputs.updateRates.getOrAwaitValue().peek()[0].code)
        assertEquals ("currencyCode", viewModel.outputs.updateRates.getOrAwaitValue().peek()[0].currencyCode)
        assertEquals (0.01, viewModel.outputs.updateRates.getOrAwaitValue().peek()[0].rate, 0.0)
    }
}
