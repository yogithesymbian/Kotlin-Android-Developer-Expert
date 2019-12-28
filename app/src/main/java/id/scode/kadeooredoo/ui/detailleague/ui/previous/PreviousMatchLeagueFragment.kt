package id.scode.kadeooredoo.ui.detailleague.ui.previous

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.detailleague.adapter.RvPrevMatchLeagueAdapter
import id.scode.kadeooredoo.ui.detailleague.presenter.PreviousPresenter
import id.scode.kadeooredoo.ui.detailleague.ui.detailnextorprevandfavorite.DetailMatchLeagueActivity
import id.scode.kadeooredoo.ui.detailleague.view.PreviousMatchLeagueView
import kotlinx.android.synthetic.main.fragment_previous.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.toast

class PreviousMatchLeagueFragment : Fragment(), PreviousMatchLeagueView, AnkoLogger {

    private lateinit var previousLeagueViewModel: PreviousLeagueViewModel
    private var idLeague: String? = null
    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
    private var eventPreviousMutableList: MutableList<EventPrevious> = mutableListOf()
    private lateinit var previousPresenter: PreviousPresenter
    private lateinit var rvPrevMatchLeagueAdapterAdapter: RvPrevMatchLeagueAdapter
    private lateinit var toolbarPrev: Toolbar
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var textView: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // initialize binding
        return inflater.inflate(R.layout.fragment_previous, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbarPrev = view.findViewById(R.id.toolbar_previous)
        textView = view.findViewById(R.id.text_home)
        progressBar = view.findViewById(R.id.progress_detail_previous)
        recyclerView = view.findViewById(R.id.rv_prev_match_leagues)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ((activity as AppCompatActivity)).setSupportActionBar(toolbarPrev)
        setHasOptionsMenu(true)

        previousLeagueViewModel =
            ViewModelProviders.of(this).get(PreviousLeagueViewModel::class.java)
        // set the layout
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext)

        // get pass data args
        val idLeagueKey = resources.getString(R.string.key_id_league)
        idLeague = arguments?.getString(idLeagueKey)

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        previousPresenter = PreviousPresenter(this, request, gson)

        idLeague?.let { id ->

            if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                EspressoIdlingResource.increment()
            }
            // call the api
            previousPresenter.getPreviousLeagueList(id)
            info("http://$EVENT_PAST_LEAGUE $id")
            info("get data with $id")

            // test obs
            previousLeagueViewModel.text.observe(this, Observer {
                textView.text = context?.resources?.getString(R.string.title_previous_pl)
                    ?.let { String.format(it, id) }
            })
        }

        /**
         * declare & initialize adapter and presenter
         * for the callBack a getLeagueTeamList
         */
        rvPrevMatchLeagueAdapterAdapter = activity?.applicationContext?.let { context ->
            RvPrevMatchLeagueAdapter(
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
        recyclerView.adapter = rvPrevMatchLeagueAdapterAdapter

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_previous, menu)

        // search view with
        val searchManager =
            activity?.applicationContext?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.option_search_previous)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.queryHint = resources.getString(R.string.option_search_previous)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                info("Search View onQueryTextSubmit")
                toast(query)
                resultSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                info("Search View onQueryTextChange")
//                resultSearch(newText)
                return false
            }
        })
        searchView.setOnCloseListener {
            rvPrevMatchLeagueAdapterAdapter.filter.filter("")
            true
        }
    }

    private fun resultSearch(query: String) {
        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            EspressoIdlingResource.increment()
        }
        previousPresenter.getSearchPreviousLeagueList(query)
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showPreviousLeague(data: List<EventPrevious>?) {
        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }
        info("try show event past list : process")
        eventPreviousMutableList.clear()
        data?.let {
            eventPreviousMutableList.addAll(it)
        }

        rvPrevMatchLeagueAdapterAdapter.notifyDataSetChanged()
        info("try show event past list : done")
        if (eventPreviousMutableList.isNullOrEmpty()) {
            toast(getString(R.string.exception_search_not_found))
            img_exception_search_nf_fp?.visible()
            rv_prev_match_leagues?.gone()
        } else {
            img_exception_search_nf_fp?.gone()
            rv_prev_match_leagues?.visible()
            info("hello prev ${eventPreviousMutableList[0].idHomeTeam}")
        }
    }

    override fun exceptionNullObject(msg: String) {
        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }
        toast("$msg ${getString(R.string.exception_search_not_found)}")
        img_exception_search_nf_fp?.visible()
        rv_prev_match_leagues?.gone()
    }

    companion object {
        const val DETAIL_PREV_MATCH_LEAGUE = "detail_prev_match_league"
    }
}