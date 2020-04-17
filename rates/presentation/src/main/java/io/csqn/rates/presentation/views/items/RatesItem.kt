package io.csqn.rates.presentation.views.items

import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.xwray.groupie.viewbinding.BindableItem
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.presentation.R
import io.csqn.rates.presentation.databinding.RatesItemBinding
import java.text.NumberFormat

data class RatesItem(val data: RateEntity, val onValueEdited: (currencyCode: String, editedValue: Double ) -> Unit) :
    BindableItem<RatesItemBinding>() {

    override fun getLayout(): Int = R.layout.rates_item

    override fun initializeViewBinding(view: View): RatesItemBinding {
        return RatesItemBinding.bind(view)
    }

    override fun getId(): Long {
        return data.currencyCode.hashCode().toLong()
    }

    override fun bind(viewBinding: RatesItemBinding, position: Int) {
        viewBinding.currencyNameTextview.text = data.name
        viewBinding.currencyCodeTextview.text = data.currencyCode
        viewBinding.currencyEdittext.setText(formatNumber(data.rate))
        loadFlag(viewBinding.currencyFlagImageview)
        setValueEditedListener(viewBinding.currencyEdittext, data.currencyCode, onValueEdited)
    }

    private fun setValueEditedListener(
        view: EditText,
        currencyCode: String,
        listener: (text: String, double: Double) -> Unit
    ) {
        view.setOnEditorActionListener { textView, i, keyEvent ->
            listener.invoke(currencyCode, textView.text.toString().toDoubleOrNull() ?: 1.00)
            textView.clearFocus()
            false
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
