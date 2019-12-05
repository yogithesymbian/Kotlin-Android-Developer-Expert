package id.scode.kadeooredoo.ui.detailLeague.ui.previous

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreviousLeagueViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Previous Fragment"
    }
    val text: LiveData<String> = _text
}