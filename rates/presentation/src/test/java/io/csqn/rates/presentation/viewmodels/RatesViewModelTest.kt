package io.csqn.rates.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import io.csqn.rates.presentation.RatesEnvironment
import io.csqn.rates.presentation.factories.RatesEnvironmentFactory
import io.csqn.test.CoroutineTestRule
import io.csqn.test.MainCoroutineRule
import io.csqn.test.getOrAwaitValue
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.measureTime


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
        viewModel.inputs.onValueEdited("GBP", 0.75)
        assertNotNull(viewModel.outputs.hideKeyboard)
    }
}
