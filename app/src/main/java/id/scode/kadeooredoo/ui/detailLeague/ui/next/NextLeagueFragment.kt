package id.scode.kadeooredoo.ui.detailLeague.ui.next

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.scode.kadeooredoo.R

class NextLeagueFragment : Fragment() {

    private lateinit var nextLeagueViewModel: NextLeagueViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nextLeagueViewModel =
            ViewModelProviders.of(this).get(NextLeagueViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        val idLeague = arguments?.getString("ID_LEAGUE")
        nextLeagueViewModel.text.observe(this, Observer {
            textView.text = "next $idLeague"
        })

        return root
    }
}