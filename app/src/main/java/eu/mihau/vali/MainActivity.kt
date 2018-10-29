package eu.mihau.vali

import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import eu.mihau.vali.model.ValidationResult
import eu.mihau.vali.model.ValidationType
import eu.mihau.vali.utils.EMAIL_REGEX
import eu.mihau.vali.utils.validationManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    button.setOnClickListener { Toast.makeText(this, "Validated", Toast.LENGTH_SHORT).show() }

    validationManager {
      debug()
      val validationCallback = { v: EditText, r: ValidationResult ->
        val til = v.parent.parent as TextInputLayout
        til.error = if (r.isValid) null else r.message
      }
      depender {
        onDependantValidationChange = { validationResult -> button.isEnabled = validationResult.isValid }
        dependOn(regExpValidator {
          validatedView = email
          message = getString(R.string.error_incorrect_email)
          onValidated = validationCallback
          regExp = EMAIL_REGEX
        })
        dependOn(minLengthValidator {
          validatedView = password
          message = getString(R.string.error_password_too_short)
          onValidated = validationCallback
          minLength = 6
          this validationType ValidationType.FOCUS_LOST
          this validationType ValidationType.TEXT_CHANGE
        })
      }
    }.run { validate(false) }
  }
}