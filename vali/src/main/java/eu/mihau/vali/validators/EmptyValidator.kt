package eu.mihau.vali.validators

import android.widget.EditText
import eu.mihau.vali.model.ValidationResult
import eu.mihau.vali.model.ValidationType

class EmptyValidator(
    override val validatedView: EditText,
    val message: String,
    override val validationCallback: (EditText, ValidationResult) -> Unit,
    vararg validationTypes: ValidationType
) : BaseValidator<EditText>() {

    init {
        validationTypes.forEach { it.handler(validatedView, this) }
    }

    override val validationAction: (view: EditText) -> ValidationResult
        get() = { view -> ValidationResult(view.text.toString().isNotEmpty(), view, message) }

    class Builder {

        lateinit var validatedView: EditText
        lateinit var message: String
        lateinit var onValidated: (EditText, ValidationResult) -> Unit
        private var validationTypes: Array<ValidationType> = emptyArray()

        infix fun validationType(validationType: ValidationType): Builder {
            this.validationTypes = arrayOf(*validationTypes, validationType)
            return this
        }

        fun build(): EmptyValidator {
            if (validationTypes.isEmpty()) {
                validationTypes = arrayOf(ValidationType.TEXT_CHANGE)
            }
            return EmptyValidator(validatedView, message, onValidated, *validationTypes)
        }
    }
}