package eu.mihau.vali.validators

import android.widget.EditText
import eu.mihau.vali.model.ValidationResult
import eu.mihau.vali.model.ValidationType

class RegExpValidator(
    override val validatedView: EditText,
    private val regExp: String,
    val message: String,
    override val validationCallback: (EditText, ValidationResult) -> Unit,
    vararg validationTypes: ValidationType
) : BaseValidator<EditText>() {

    init {
        validationTypes.forEach { it.handler(validatedView, this) }
    }

    override val validationAction: (view: EditText) -> ValidationResult
        get() = { view -> ValidationResult(Regex(regExp).matches(view.text.toString()), view, message) }

    class Builder {

        lateinit var validatedView: EditText
        lateinit var regExp: String
        lateinit var message: String
        lateinit var onValidated: (EditText, ValidationResult) -> Unit
        private var validationTypes: Array<ValidationType> = emptyArray()

        infix fun validationType(validationType: ValidationType): Builder {
            this.validationTypes = arrayOf(*validationTypes, validationType)
            return this
        }

        fun build(): RegExpValidator {
            if (validationTypes.isEmpty()) {
                validationTypes = arrayOf(ValidationType.TEXT_CHANGE)
            }
            return RegExpValidator(validatedView, regExp, message, onValidated, *validationTypes)
        }
    }
}