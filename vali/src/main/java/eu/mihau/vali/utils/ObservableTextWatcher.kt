package eu.mihau.vali.utils

import android.text.Editable
import android.text.TextWatcher
import io.reactivex.subjects.PublishSubject

class ObservableTextWatcher : TextWatcher {

    val textChanges: PublishSubject<String> = PublishSubject.create()

    override fun afterTextChanged(s: Editable?) {
        textChanges.onNext(s?.toString() ?: "")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}