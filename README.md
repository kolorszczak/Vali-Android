# Vali [![Build Status](https://travis-ci.org/kolorszczak/vali-android.svg?branch=master)](https://travis-ci.org/kolorszczak/vali-android)

Vali is a simbple Android Validation library written in Kotlin.


##### Installation
Add this in app's `build.gradle` file:
```sh
implementation "eu.mihau:vali-android:1.0.0"
```

##### Usage
```kotlin
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
```


### Development
1. Fork project
2. Create feature branch
3. Commit your changes
4. Push to the branch
5. Create new Pull Request

### Todos

 - Write Unit Tests
 - Fix all known bugs

License
----

MIT
