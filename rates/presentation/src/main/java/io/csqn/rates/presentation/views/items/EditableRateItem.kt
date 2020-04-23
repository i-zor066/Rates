package io.csqn.rates.presentation.views.items

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.xwray.groupie.Item
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.presentation.databinding.RatesItemBinding

data class EditableRateItem(
    val entity: RateEntity,
    val onValueEdited: (currencyCode: String, editedValue: Double) -> Unit
) :
    RateItem(entity) {

    override fun bind(viewBinding: RatesItemBinding, position: Int) {
        super.bind(viewBinding, position)
        setClickListener(viewBinding)
        setValueEditedListener(viewBinding.currencyEdittext, data.currencyCode, onValueEdited)
    }

    private fun setClickListener(viewBinding: RatesItemBinding) {
        viewBinding.root.setOnClickListener {
            with(viewBinding.currencyEdittext) {
                requestFocus()
                setSelection(this.text.length)
            }
        }
    }

    private fun setValueEditedListener(
        editText: EditText,
        currencyCode: String,
        listener: (text: String, double: Double) -> Unit
    ) {
        editText.isEnabled = true
        editText.setOnEditorActionListener { view, i, keyEvent ->
            listener.invoke(currencyCode, view.text.toString().toDoubleOrNull() ?: 1.00)
            view.clearFocus()
            false
        }
        editText.setOnFocusChangeListener { view, hasFocus ->
            view as EditText
            val watcher = getTextWatcher(view, currencyCode, listener)
            if (hasFocus) {
                editText.requestFocus()
                editText.setSelection(editText.text.length)
                view.addTextChangedListener(watcher)
            } else {
                view.removeTextChangedListener(watcher)
            }
        }
    }

    private fun getTextWatcher(
        editText: EditText,
        currencyCode: String,
        listener: (text: String, double: Double) -> Unit
    ): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                Log.d("EDITTEXT DEBUG", "afterTextChanged ${p0.toString()}")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("EDITTEXT DEBUG", "beforeTextChanged $p0")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("EDITTEXT DEBUG", "onTextChanged $p0")
            }

        }
    }
}
