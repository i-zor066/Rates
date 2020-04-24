package io.csqn.rates.presentation.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.csqn.core.coreComponent
import io.csqn.core.extensions.hideKeyboard
import io.csqn.core.livedata.EventObserver
import io.csqn.core.viewmodels.getViewModel
import io.csqn.explorer.presentation.di.RatesComponentManager
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.presentation.databinding.ActivityRatesBinding
import io.csqn.rates.presentation.viewmodels.RatesViewModel
import io.csqn.rates.presentation.views.items.EditableRateItem
import io.csqn.rates.presentation.views.items.RateItem
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent.setEventListener
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


class RatesActivity : AppCompatActivity() {

    private val component by lazy { RatesComponentManager.build(coreComponent()) }
    private val viewModel by lazy { getViewModel { RatesViewModel(component.environment()) } }
    private val binding by lazy { ActivityRatesBinding.inflate(layoutInflater) }
    private val baseRateSection = Section()
    private val ratesSection = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
        viewModel.baseStates.error.observe(this,
            EventObserver {
                handleError(it)
            })
        viewModel.inputs.onViewCreated()
        viewModel.outputs.updateBaseRate.observe(this,
        EventObserver {
            Log.d("BASE RATE DEBUG", "Observer onCreate: Baserate: rateEntity: $it")
            setBaseRateView(it)
        })
        viewModel.outputs.updateRates.observe(this,
            EventObserver {
                setRatesListView(it)
            })
        viewModel.outputs.hideKeyboard.observe(this, EventObserver {
            this@RatesActivity.hideKeyboard()
        })
    }

    private fun setBaseRateView(baseRate: RateEntity) {
        baseRateSection.update(
            listOf(
                EditableRateItem(baseRate, { currencyCode, value ->
            viewModel.inputs.updateBaseRate(currencyCode, value)
        }, {viewModel.inputs.onDone()})))
    }

    private fun setRatesListView(rates: List<RateEntity>) {
        ratesSection.update(rates.map {
            RateItem(it)
        })
    }

    private fun initViews() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(baseRateSection)
            add(ratesSection)
            setHasStableIds(true)
        }
        groupAdapter.setOnItemClickListener(listener)
        binding.ratesRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@RatesActivity)
            adapter = groupAdapter
        }
    }

    private val listener = OnItemClickListener { item, view ->
        if (item !is EditableRateItem && item is RateItem) {
            viewModel.inputs.switchBaseCurrency(item.data.currencyCode, item.data.rate)
            binding.ratesRecyclerview.smoothScrollToPosition(0)
        }
    }

    private fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }
}
