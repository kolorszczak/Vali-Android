package eu.mihau.vali.validators

import android.widget.EditText
import eu.mihau.vali.model.ValidationResult
import eu.mihau.vali.model.ValidationType

class MinLengthValidator(override val validatedView: EditText,
                         private val minLength: Int,
                         val message: String,
                         override val validationCallback: (EditText, ValidationResult) -> Unit,
                         vararg validationTypes: ValidationType = arrayOf(ValidationType.TEXT_CHANGE)) : BaseValidator<EditText>() {

    init {
        validationTypes.forEach { it.handler(validatedView, this) }
    }

    override val validationAction: (view: EditText) -> ValidationResult
        get() = { view -> ValidationResult(view.text.toString().length >= minLength, view, message) }

    class Builder {

        lateinit var validatedView: EditText
        lateinit var message: String
        var minLength: Int = 0
        lateinit var onValidated: (EditText, ValidationResult) -> Unit
        private var validationTypes: Array<ValidationType> = emptyArray()

        infix fun validationType(validationType: ValidationType): Builder {
            this.validationTypes = arrayOf(*validationTypes, validationType)
            return this
        }

        fun build(): MinLengthValidator {
            if (validationTypes.isEmpty()) {
                validationTypes = arrayOf(ValidationType.TEXT_CHANGE)
            }
            return MinLengthValidator(validatedView, minLength, message, onValidated, *validationTypes)
        }
    }
}