package id.scode.kadeooredoo.ui.detailLeague.ui.next

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.invisible
import id.scode.kadeooredoo.ui.detailLeague.adapter.RvNextMatchLeague
import id.scode.kadeooredoo.ui.detailLeague.presenter.NextPresenter
import id.scode.kadeooredoo.ui.detailLeague.view.NextMatchLeagueView
import id.scode.kadeooredoo.visible
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class NextMatchLeagueFragment : Fragment(), NextMatchLeagueView, AnkoLogger {

    private lateinit var nextLeagueViewModel: NextLeagueViewModel
    private var idLeague: String? = null
    /**
     * apply the MainPresenter and MainAdapter
     * to the this context
     */
    private var eventNextMutableList: MutableList<EventNext> = mutableListOf()
    private lateinit var nextPresenter: NextPresenter
    private lateinit var rvNextMatchLeagueAdapter: RvNextMatchLeague
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nextLeagueViewModel =
            ViewModelProviders.of(this).get(NextLeagueViewModel::class.java)

        // initialize binding
        val root = inflater.inflate(R.layout.fragment_next, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        recyclerView = root.findViewById(R.id.rv_next_match_leagues)

        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        // get pass data args
        idLeague = arguments?.getString("ID_LEAGUE")

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        nextPresenter = NextPresenter(this, request, gson)

        idLeague?.let { id ->
            // call the api
            nextPresenter.getNextLeagueList(id)
            info ("get data with $id")

            // test obs
            nextLeagueViewModel.text.observe(this, Observer {
                textView.text = "next $id"
            })
        }

        /**
         * declare & initialize adapter and presenter
         * for the callBack a getTeamList
         */
        rvNextMatchLeagueAdapter = activity?.applicationContext?.let {
            RvNextMatchLeague(
                it,
                eventNextMutableList
            ) {
                info(
                    """
                            date : ${it.strAwayFormation}
                    """.trimIndent()
                )
                //            startActivity<DetailActivity>(DETAIL_KEY to it) //intent with the obj
            }
        }!!
        recyclerView.adapter = rvNextMatchLeagueAdapter

        return root
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showNextLeague(data: List<EventNext>?) {
        info("try show event past list : process")
        eventNextMutableList.clear()
        data?.let {
            eventNextMutableList.addAll(it)
        }
        rvNextMatchLeagueAdapter.notifyDataSetChanged()
        info("try show event past list : done")
        info("hello yogi ${eventNextMutableList[0].idHomeTeam}")
    }
}