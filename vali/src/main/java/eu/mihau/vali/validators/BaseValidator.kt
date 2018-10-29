package eu.mihau.vali.validators

import android.view.View
import eu.mihau.vali.model.ValidationResult
import io.reactivex.subjects.PublishSubject

abstract class BaseValidator<V : View> {
    abstract val validationCallback: (V, ValidationResult) -> Unit
    abstract val validatedView: V
    abstract val validationAction: (view: V) -> ValidationResult
    val changeSubject: PublishSubject<ValidationResult> = PublishSubject.create()

    fun validate(notifyUi: Boolean): ValidationResult {
        val result = validationAction(validatedView)
        changeSubject.onNext(result)
        if (notifyUi) {
            validationCallback(validatedView, result)
        }
        return result
    }
}