package eu.mihau.vali.utils

import eu.mihau.vali.ValidationManager

inline fun validationManager(init: ValidationManager.() -> Unit): ValidationManager {
    val validationManager = ValidationManager()
    validationManager.init()
    return validationManager
}