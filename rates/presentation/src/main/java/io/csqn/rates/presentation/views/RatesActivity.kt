package io.csqn.rates.presentation.views

import android.os.Bundle
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
import io.csqn.rates.domain.entities.RatesEntity
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
        viewModel.outputs.updateRates.observe(this,
            EventObserver {
                setView(it)
            })
        viewModel.outputs.hideKeyboard.observe(this, EventObserver {
            this@RatesActivity.hideKeyboard()
        })
    }

    private fun setView(ratesEntity: RatesEntity) {
        baseRateSection.update(listOf(EditableRateItem(ratesEntity.baseRate) { currencyCode, value ->
            viewModel.inputs.onValueEdited(currencyCode, value)
        }))
        ratesSection.update(ratesEntity.rates.map {
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

        setEventListener(
            this,
            this,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    viewModel.inputs.onKeyboardVisibilityChange(isOpen)
                }
            })
    }

    val listener = OnItemClickListener { item, view ->
        if (item !is EditableRateItem && item is RateItem)
            Toast.makeText(this@RatesActivity, "NOT NULL ${(item.data.currencyCode)}", Toast.LENGTH_SHORT).show()
    }

    private fun handleError(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
    }
}
