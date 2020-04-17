package io.csqn.rates.presentation.views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.xwray.groupie.GroupAdapter
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
import io.csqn.rates.presentation.views.items.RatesItem
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

        viewModel.inputs.onViewCreated()
        viewModel.outputs.updateRates.observe(this,
            EventObserver {
                Log.d("UPDATING"," ${it.baseRate.code} list: ${it.rates}")
                setView(it)
            })
        viewModel.outputs.hideKeyboard.observe(this, EventObserver {
            this@RatesActivity.hideKeyboard()
        })
    }

    private fun setView(ratesEntity: RatesEntity) {
        baseRateSection.update(listOf(RatesItem(ratesEntity.baseRate) { currencyCode, value ->
            viewModel.inputs.onValueEdited(currencyCode, value)
        }))
        ratesSection.update(ratesEntity.rates.map {
            RatesItem(it) { currencyCode, value ->
                viewModel.inputs.onValueEdited(currencyCode, value)
            }
        })
    }

    private fun initViews() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(baseRateSection)
            add(ratesSection)
        }
        binding.ratesRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@RatesActivity)
            adapter = groupAdapter
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
}
