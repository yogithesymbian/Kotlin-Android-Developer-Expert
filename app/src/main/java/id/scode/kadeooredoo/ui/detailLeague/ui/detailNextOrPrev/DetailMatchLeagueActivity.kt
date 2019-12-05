package id.scode.kadeooredoo.ui.detailLeague.ui.detailNextOrPrev

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.EventDetailMatch
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.invisible
import id.scode.kadeooredoo.ui.detailLeague.presenter.DetailMatchPresenter
import id.scode.kadeooredoo.ui.detailLeague.ui.next.NextMatchLeagueFragment.Companion.DETAIL_NEXT_MATCH_LEAGUE
import id.scode.kadeooredoo.ui.detailLeague.ui.previous.PreviousMatchLeagueFragment.Companion.DETAIL_PREV_MATCH_LEAGUE
import id.scode.kadeooredoo.ui.detailLeague.view.DetailMatchView
import id.scode.kadeooredoo.visible
import kotlinx.android.synthetic.main.activity_detail_match_league.*
import kotlinx.android.synthetic.main.content_detail_match_league.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DetailMatchLeagueActivity : AppCompatActivity() , DetailMatchView, AnkoLogger{

    private var eventPrevious: EventPrevious? = null
    private var eventNext: EventNext? = null
    private lateinit var progressBar: ProgressBar

    /**
     * apply the MainPresenter and MainAdapter
     * to the this context
     */
    private var eventDetailMatchMutableList: MutableList<EventDetailMatch> = mutableListOf()
    private lateinit var detailMatchPresenter: DetailMatchPresenter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match_league)
        setSupportActionBar(toolbar)

        progressBar = findViewById(R.id.progress_detail_match_event)

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        detailMatchPresenter = DetailMatchPresenter(this, request, gson)

        // get eventId ; but this will useless, i can pass all obj , why i need call api detail ?
        intent.also {
            eventPrevious = it.getParcelableExtra(DETAIL_PREV_MATCH_LEAGUE)
            eventNext = it.getParcelableExtra(DETAIL_NEXT_MATCH_LEAGUE)
        }

        // call api detail match by eventId
        if (eventPrevious != null) {
            eventPrevious?.also {
                it.idEvent?.let { it1 -> detailMatchPresenter.getDetailMatchList(it1) }
            }
        } else if (eventNext != null) {
            eventNext?.also {
                it.idEvent?.let { it1 -> detailMatchPresenter.getDetailMatchList(it1) }
            }
        }

    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailMatch(data: List<EventDetailMatch>?) {
        info ("try show team list : process")

        eventDetailMatchMutableList.clear()
        data?.let {
            eventDetailMatchMutableList.addAll(it)
        }
        val post = 0
        setDataMatch(eventDetailMatchMutableList[post])

        info("try show team list : done")
    }

    private fun setDataMatch(item: EventDetailMatch) {
        info("i love you : ${item.strFilename}")

        txt_str_events.text = item.strEvent
        txt_str_seasons.text = item.strSeason

        txt_home_team.text = item.strHomeTeam
        val scoreHome = item.intHomeScore
        txt_score_home.also {
            when (scoreHome) {
                null -> it.text = "?"
                else -> it.text = scoreHome
            }
        }

        txt_away_team.text = item.strAwayTeam
        val scoreAway = item.intAwayScore
        txt_score_away.also {
            when (scoreAway) {
                null -> it.text = "?"
                else -> it.text = scoreAway
            }
        }
        txt_date_event.text = item.dateEvent
        txt_str_time_event.text = item.strTime
        txt_unlocked_event.text = item.strLocked

        info("forgive aku :( : ${item.strDate}")
    }
}
