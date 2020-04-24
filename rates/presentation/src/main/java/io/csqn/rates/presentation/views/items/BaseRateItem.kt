package io.csqn.rates.presentation.views.items

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.Item
import com.xwray.groupie.viewbinding.BindableItem
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.presentation.R
import io.csqn.rates.presentation.databinding.RatesItemBinding
import java.text.NumberFormat

abstract class BaseRateItem(
    val data: RateEntity
) :
    BindableItem<RatesItemBinding>() {

    private lateinit var viewBinding: RatesItemBinding

    override fun getLayout(): Int = R.layout.rates_item

    override fun initializeViewBinding(view: View): RatesItemBinding {
        viewBinding = RatesItemBinding.bind(view)
        return viewBinding
    }

    override fun getId(): Long {
        return data.currencyCode.hashCode().toLong()
    }

    override fun isSameAs(other: Item<*>): Boolean {
        return id == other.id
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        return data.rate.equals((other as BaseRateItem).data.rate)
    }

    override fun bind(viewBinding: RatesItemBinding, position: Int) {
        viewBinding.currencyNameTextview.text = data.name
        viewBinding.currencyCodeTextview.text = data.currencyCode
        viewBinding.currencyEdittext.setText(formatNumber(data.rate))
        loadFlag(viewBinding.currencyFlagImageview)
    }

    override fun bind(
        viewBinding: RatesItemBinding,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && payloads[0] is OnlyRate) {
            if (!viewBinding.currencyEdittext.hasFocus())
                viewBinding.currencyEdittext.setText(formatNumber(data.rate))
        } else {
            bind(viewBinding, position)
        }
    }

    override fun getChangePayload(newItem: Item<*>): Any? {
        return OnlyRate.Instance
    }

    fun removeFocus() {
        if(::viewBinding.isInitialized) {
            viewBinding.currencyEdittext.clearFocus()
            viewBinding.root.clearFocus()
        }
    }

    private fun loadFlag(currencyFlagImageview: ImageView) {
        Glide.with(currencyFlagImageview.context)
            .asBitmap()
            .load(data.flagUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(currencyFlagImageview)
    }

    private fun formatNumber(rate: Double): String {
        return NumberFormat.getInstance().apply {
            maximumFractionDigits = 2
        }.format(rate)
    }
}
