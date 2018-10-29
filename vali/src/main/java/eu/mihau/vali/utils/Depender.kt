package eu.mihau.vali.utils

import eu.mihau.vali.model.ValidationResult
import eu.mihau.vali.validators.BaseValidator

class Depender(val onValidationChange: (ValidationResult) -> Unit, val validators: Array<BaseValidator<*>>)

class DependerBuilder {
    lateinit var onDependantValidationChange: (ValidationResult) -> Unit
    private var dependants: Array<BaseValidator<*>> = emptyArray()

    infix fun dependOn(validator: BaseValidator<*>) {
        this.dependants = arrayOf(*dependants, validator)
    }

    fun build(): Depender = Depender(onDependantValidationChange, dependants)

}