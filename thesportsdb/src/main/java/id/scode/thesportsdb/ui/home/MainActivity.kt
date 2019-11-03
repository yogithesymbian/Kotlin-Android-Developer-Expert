package id.scode.thesportsdb.ui.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import id.scode.thesportsdb.*
import id.scode.thesportsdb.data.db.entities.Team
import id.scode.thesportsdb.data.network.ApiRepository
import id.scode.thesportsdb.ui.home.presenter.MainPresenter
import org.jetbrains.anko.support.v4.onRefresh


class MainActivity : AppCompatActivity(), MainView , AnkoLogger{

    // declare a view
    private lateinit var recyclerViewListTeam: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayoutListTeam: SwipeRefreshLayout
    private lateinit var spinner: Spinner

    /**
     * apply the MainPresenter and MainAdapter
     * to the this context
     */
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var mainPresenter: MainPresenter
    private lateinit var mainAdapter: MainAdapter

    //declare a view for choose
    private lateinit var leagueName: String //for spinner

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner()
            swipeRefreshLayoutListTeam = swipeRefreshLayout {
                setColorSchemeColors(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    recyclerViewListTeam = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(context)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        } //end of view

        /**
         * declare & initialize adapter and presenter
         * for the callBack a getTeamList
         */
        mainAdapter = MainAdapter(teams)
        recyclerViewListTeam.adapter = mainAdapter

        val request = ApiRepository()
        val gson = Gson()
        mainPresenter = MainPresenter(this, request, gson)

        // spiner config
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerItems
        )
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                info("spinner selected ${spinner.selectedItem}")
                leagueName = spinner.selectedItem.toString()
                mainPresenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                info("onNothingSelected")
            }
        }

        // pull-down
        swipeRefreshLayoutListTeam.onRefresh{
            info("try swipe to refresh on select ${spinner.selectedItem}")
            mainPresenter.getTeamList(spinner.selectedItem.toString())
        }

    } // end of onCreate

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>?) {
        info ("try show team list : process")
        swipeRefreshLayoutListTeam.isRefreshing = false
        teams.clear()
        data?.let {
            teams.addAll(it)
        }
        mainAdapter.notifyDataSetChanged()
        info("try show team list : done")
    }

}
