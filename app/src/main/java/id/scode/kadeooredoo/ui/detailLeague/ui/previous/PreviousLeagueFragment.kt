package id.scode.kadeooredoo.ui.detailLeague.ui.previous

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.scode.kadeooredoo.R

class PreviousLeagueFragment : Fragment() {

    private lateinit var previousLeagueViewModel: PreviousLeagueViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        previousLeagueViewModel =
            ViewModelProviders.of(this).get(PreviousLeagueViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)

        val idLeague = arguments?.getString("ID_LEAGUE")

        previousLeagueViewModel.text.observe(this, Observer {
            textView.text = "prev $idLeague"
        })
        return root
    }
}