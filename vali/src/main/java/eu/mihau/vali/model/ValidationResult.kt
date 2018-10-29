package eu.mihau.vali.model

data class ValidationResult(val isValid: Boolean,
                            val sender: Any,
                            val message: String)