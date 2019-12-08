package id.scode.kadeooredoo.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.gone
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import id.scode.kadeooredoo.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 08 12/8/19 2:16 PM 2019
 * id.scode.kadeooredoo.ui.home
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

class TeamsDetailActivity : AppCompatActivity(), TeamsView, AnkoLogger {


    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var teamsPresenter: TeamsPresenter
    private var teamsMutableList: MutableList<Team> = mutableListOf()
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
                )

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge = imageView {}.lparams(height = dip(75))

                            teamName = textView {
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, R.color.colorAccent)
                            }.lparams {
                                topMargin = dip(5)
                            }

                            teamFormedYear = textView {
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView {
                                this.gravity = Gravity.CENTER
                                textColor =
                                    ContextCompat.getColor(context, R.color.colorPrimaryText)
                            }

                            teamDescription = textView().lparams {
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        supportActionBar?.title = getString(R.string.title_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        id = intent.getStringExtra(DETAIL_KEY)

        val request = ApiRepository()
        val gson = Gson()

        teamsPresenter = TeamsPresenter(this, request, gson)
        id?.let {
            info("call api with team id : $it")
            teamsPresenter.getDetailLeagueTeamList(it)
        }

        swipeRefreshLayout.onRefresh {
            id?.let { teamsPresenter.getDetailLeagueTeamList(it) }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

    override fun showTeamList(data: List<Team>?) {

        info("try show team list : process")

        swipeRefreshLayout.isRefreshing = false

        data?.let {
            teamsMutableList.addAll(it)
        }

        Picasso.get().load(data?.get(0)?.teamBadge).into(teamBadge)

        teamName.text = data?.get(0)?.teamName
        teamDescription.text = data?.get(0)?.strDescriptionEN
        teamFormedYear.text = data?.get(0)?.intFormedYear
        teamStadium.text = data?.get(0)?.strStadium

        info("try show team list : done")
    }

    override fun showTeamAwayList(data: List<Team>?) {
        // just for inside adapter previous and next
    }
}
