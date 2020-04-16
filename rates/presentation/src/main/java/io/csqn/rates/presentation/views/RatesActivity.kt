package io.csqn.rates.presentation.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import io.csqn.core.coreComponent
import io.csqn.core.livedata.EventObserver
import io.csqn.core.viewmodels.getViewModel
import io.csqn.explorer.presentation.di.RatesComponentManager
import io.csqn.rates.presentation.viewmodels.RatesViewModel
import io.csqn.rates.presentation.views.items.RatesItem
import io.csqn.rates.domain.entities.RatesEntity
import io.csqn.rates.presentation.databinding.ActivityRatesBinding

class RatesActivity : AppCompatActivity() {

    private val component by lazy { RatesComponentManager.build(coreComponent()) }
    private val viewModel by lazy { getViewModel { RatesViewModel(component.environment()) } }
    private val binding by lazy { ActivityRatesBinding.inflate(layoutInflater) }
    private val ratesSection = Section()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()

        viewModel.inputs.onViewCreated()
        viewModel.outputs.updateRates.observe(this,
            EventObserver {
                setView(it)
            })
    }

    private fun setView(ratesEntity: RatesEntity) {
        ratesSection.add(RatesItem(ratesEntity.baseRate))
        ratesSection.addAll(ratesEntity.rates.map { RatesItem(it) })
    }

    private fun initViews() {
        val groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            add(ratesSection)
        }
        binding.ratesRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@RatesActivity)
            adapter = groupAdapter
        }
    }
}
