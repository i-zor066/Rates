package io.csqn.rates.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import io.csqn.rates.presentation.factories.RatesEnvironmentFactory
import io.csqn.test.MainCoroutineRule
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test


@SmallTest
@ExperimentalCoroutinesApi
class RatesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = MainCoroutineRule()

    private val environment = RatesEnvironmentFactory.create()

    @Test
    fun `hide keyboard when value edited`() {
        val viewModel = RatesViewModel(environment)
        viewModel.inputs.switchBaseCurrency("GBP", 0.75)
        assertNotNull(viewModel.outputs.hideKeyboard)
    }
}
