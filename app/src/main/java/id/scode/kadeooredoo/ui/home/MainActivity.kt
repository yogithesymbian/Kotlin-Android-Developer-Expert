package id.scode.kadeooredoo.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.RvFootballAdapter
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.invisible
import id.scode.kadeooredoo.ui.home.presenter.MainPresenter
import id.scode.kadeooredoo.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.floatingActionButton
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 1:31 PM 2019
 * idClubFootball.scode.kadeooredoo.submission.ui
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

class MainActivity : AppCompatActivity(), AnkoLogger, MainView {

    companion object {
        const val DETAIL_KEY = "detail_key" //PAIR key for getParcelAble data obj
    }

    /**
     * Declare recycler and mutableList
     */
//    private var itemClubFootballs: MutableList<ItemClubFootball> = mutableListOf()
//    private lateinit var recyclerViewFootball: RecyclerView

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
    private lateinit var mainAdapter: RvFootballAdapter

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

            toolbar {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    logo = resources.getDrawable(R.drawable.ic_home_green_400_24dp, context.theme)
                    title = context.getString(R.string.main_activity_title_for_layout)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        titleMarginStart = dip(32)
                    }
                    elevation = 12f
                    backgroundColor = R.color.colorPrimary
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setBackgroundColor(resources.getColor(R.color.colorPrimary, context.theme))
                    }
                }
            }.lparams(matchParent, wrapContent) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    margin = dip(16)
                }
            }

            spinner = spinner()
            button("Look League") {

            }.lparams(width = matchParent, height = wrapContent){
                gravity = Gravity.BOTTOM.and(Gravity.END)
                margin = dip(8)
            }
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
        mainAdapter = RvFootballAdapter(this,teams){
            info("""
                recycle got clicked
                id league ${it.idLeague}
                |
                id team ${it.teamId}
                |
                id Soccer XML ${it.idSoccerXML}
                |
                team name ${it.teamName}
            """.trimIndent())
            debug(8)
            error(null)
            startActivity<DetailActivity>(DETAIL_KEY to it) //intent with the obj
        }
        recyclerViewListTeam.adapter = mainAdapter

        val request = ApiRepository()
        val gson = Gson()
        mainPresenter = MainPresenter(this, request, gson)

        // spinner config
        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_dropdown_item,
            spinnerItems
        )
        spinner.adapter = spinnerAdapter

        // spinner listener
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
        swipeRefreshLayoutListTeam.onRefresh {
            info("try swipe to refresh on select ${spinner.selectedItem}")
            mainPresenter.getTeamList(spinner.selectedItem.toString())
        }

    } //end of onCreate


    // implement of , MainView
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
