package eu.mihau.vali

import android.util.Log
import android.view.View
import eu.mihau.vali.model.ValidationResult
import eu.mihau.vali.utils.Depender
import eu.mihau.vali.utils.DependerBuilder
import eu.mihau.vali.validators.BaseValidator
import eu.mihau.vali.validators.EmptyValidator
import eu.mihau.vali.validators.MinLengthValidator
import eu.mihau.vali.validators.RegExpValidator
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject


class ValidationManager {

    companion object {
        const val TAG = "ValidationManager"
    }

    private val validators: ArrayList<BaseValidator<*>> = ArrayList()
    private val validationSubject: PublishSubject<ValidationResult> = PublishSubject.create()
    private var compositeDisposable = CompositeDisposable()

    fun ValidationManager.debug(): ValidationManager {
        validationSubject.subscribe {
            Log.d(TAG, "valid: ${it.isValid} message:${it.message}")
        }.let { compositeDisposable.add(it)}
        return this
    }

    fun validate(notifyUi: Boolean) {
        validators.map { it.validate(notifyUi) }.forEach { validationSubject.onNext(it) }
    }

    fun ValidationManager.depender(actions: DependerBuilder.() -> Unit): Depender {
        val builder = DependerBuilder()
        builder.actions()
        val depender = builder.build()
        val combine: (Array<Any>) -> Any = { validationResults ->
            validationResults.find { (it as? ValidationResult)?.isValid?.not() ?: false }
                ?: validationResults.first()
        }
        Observable.combineLatest(depender.validators.map { it.changeSubject }, combine)
            .subscribe { validationResult -> depender.onValidationChange(validationResult as ValidationResult) }
        return depender
    }

    fun <V : View> ValidationManager.validator(validator: BaseValidator<V>): BaseValidator<*> {
        this.validators.add(validator)
        return validator
    }

    fun ValidationManager.regExpValidator(actions: RegExpValidator.Builder.() -> Unit): RegExpValidator {
        val builder = RegExpValidator.Builder()
        builder.actions()
        return builder.build()
    }

    fun ValidationManager.emptyValidator(actions: EmptyValidator.Builder.() -> Unit): EmptyValidator {
        val builder = EmptyValidator.Builder()
        builder.actions()
        return builder.build()
    }

    fun ValidationManager.minLengthValidator(actions: MinLengthValidator.Builder.() -> Unit): MinLengthValidator {
        val builder = MinLengthValidator.Builder()
        builder.actions()
        return builder.build()
    }

}












