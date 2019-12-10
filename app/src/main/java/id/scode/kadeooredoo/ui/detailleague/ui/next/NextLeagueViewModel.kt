package id.scode.kadeooredoo.ui.detailleague.ui.next

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NextLeagueViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Next Fragment"
    }
    val text: LiveData<String> = _text
}