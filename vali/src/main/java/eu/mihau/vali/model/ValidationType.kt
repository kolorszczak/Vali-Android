package eu.mihau.vali.model

import android.view.View
import eu.mihau.vali.validators.BaseValidator

enum class ValidationType(val handler: (v: View, validator: BaseValidator<*>) -> Unit) {
    TEXT_CHANGE({ v, validator ->
        if (v is android.widget.EditText) {
            val observableTextWatcher = eu.mihau.vali.utils.ObservableTextWatcher()
            observableTextWatcher.textChanges.subscribe { validator.validate(true) }
            v.addTextChangedListener(observableTextWatcher)
        }

    }),
    FOCUS_LOST({ v, validator ->
        if (v is android.widget.EditText) {
            v.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus.not()) {
                    validator.validate(true)
                }
            }
        }
    })
}