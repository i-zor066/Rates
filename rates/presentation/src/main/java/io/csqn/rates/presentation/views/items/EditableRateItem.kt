package io.csqn.rates.presentation.views.items

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import io.csqn.rates.domain.entities.RateEntity
import io.csqn.rates.presentation.databinding.RatesItemBinding

data class EditableRateItem(
    val entity: RateEntity,
    val onValueEdited: (currencyCode: String, editedValue: Double) -> Unit,
    val onDone: () -> Unit
) :
    BaseRateItem(entity) {

    val textWatchers = ArrayList<TextWatcher>()

    override fun bind(viewBinding: RatesItemBinding, position: Int) {
        super.bind(viewBinding, position)
        clearListeners(viewBinding)
        setClickListener(viewBinding)
        setValueEditedListener(viewBinding.currencyEdittext, data.currencyCode, onValueEdited)
    }

    override fun bind(
        viewBinding: RatesItemBinding,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.bind(viewBinding, position, payloads)
        if (payloads.isNotEmpty() && payloads[0] is OnlyRate) {
            viewBinding.currencyEdittext.isEnabled = true
        }
    }

    private fun clearListeners(viewBinding: RatesItemBinding) {
        viewBinding.root.setOnClickListener(null)
        viewBinding.currencyEdittext.setOnFocusChangeListener(null)
        viewBinding.currencyEdittext.setOnEditorActionListener(null)
        viewBinding.currencyEdittext.removeTextChangedListener(
            getTextWatcher(
                data.currencyCode,
                onValueEdited
            )
        )
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
        editText.setOnEditorActionListener { view, _, _ ->
            onDone.invoke()
            editText.clearFocus()
            false
        }
        editText.setOnFocusChangeListener { view, hasFocus ->
            view as EditText
            val watcher = getTextWatcher(currencyCode, listener)
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
        currencyCode: String,
        listener: (text: String, double: Double) -> Unit
    ): TextWatcher {
        if (textWatchers.isEmpty()) {
            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    Log.d("EDITTEXT DEBUG", "onTextChanged $p0")
                    listener.invoke(currencyCode,getDoubleFromString(p0.toString()) )
                }
            }
            textWatchers.add(textWatcher)
            return textWatcher
        } else {
            return textWatchers[0]
        }
    }
}
