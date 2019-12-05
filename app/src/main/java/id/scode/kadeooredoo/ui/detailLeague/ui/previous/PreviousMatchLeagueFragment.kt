package id.scode.kadeooredoo.ui.detailLeague.ui.previous

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
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.invisible
import id.scode.kadeooredoo.ui.detailLeague.adapter.RvPrevMatchLeague
import id.scode.kadeooredoo.ui.detailLeague.presenter.PreviousPresenter
import id.scode.kadeooredoo.ui.detailLeague.ui.detailNextOrPrev.DetailMatchLeagueActivity
import id.scode.kadeooredoo.ui.detailLeague.view.PreviousMatchLeagueView
import id.scode.kadeooredoo.visible
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity

class PreviousMatchLeagueFragment : Fragment() , PreviousMatchLeagueView, AnkoLogger{


    private lateinit var previousLeagueViewModel: PreviousLeagueViewModel
    private var idLeague: String? = null
    /**
     * apply the MainPresenter and MainAdapter
     * to the this context
     */
    private var eventPreviousMutableList: MutableList<EventPrevious> = mutableListOf()
    private lateinit var previousPresenter: PreviousPresenter
    private lateinit var rvPrevMatchLeagueAdapter: RvPrevMatchLeague
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        previousLeagueViewModel =
            ViewModelProviders.of(this).get(PreviousLeagueViewModel::class.java)

        // initialize binding
        val root = inflater.inflate(R.layout.fragment_previous, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        progressBar = root.findViewById(R.id.progress_detail_previous)
        recyclerView = root.findViewById(R.id.rv_prev_match_leagues)

        // set the layout
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        // get pass data args
        idLeague = arguments?.getString("ID_LEAGUE")

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        previousPresenter = PreviousPresenter(this, request, gson)

        idLeague?.let { id ->
            // call the api
            previousPresenter.getPreviousLeagueList(id)
            info ("get data with $id")

            // test obs
            previousLeagueViewModel.text.observe(this, Observer {
                textView.text = "prev $id"
            })
        }


        /**
         * declare & initialize adapter and presenter
         * for the callBack a getTeamList
         */
        rvPrevMatchLeagueAdapter = activity?.applicationContext?.let { context ->
            RvPrevMatchLeague(
                context,
                eventPreviousMutableList
            ) {
                info(
                    """
                            date : ${it.strDate}
                    """.trimIndent()
                )
                context.startActivity<DetailMatchLeagueActivity>(DETAIL_PREV_MATCH_LEAGUE to it)
            }
        }!!
        recyclerView.adapter = rvPrevMatchLeagueAdapter

        return root
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPreviousLeague(data: List<EventPrevious>?) {
        info ("try show event past list : process")
        eventPreviousMutableList.clear()
        data?.let {
            eventPreviousMutableList.addAll(it)
        }
        rvPrevMatchLeagueAdapter.notifyDataSetChanged()
        info("try show event past list : done")
        info("hello prev ${eventPreviousMutableList[0].idHomeTeam}")
    }

    companion object{
        const val DETAIL_PREV_MATCH_LEAGUE = "detail_prev_match_league"
    }
}