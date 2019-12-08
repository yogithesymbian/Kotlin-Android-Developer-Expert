package id.scode.kadeooredoo.ui.detailLeague.ui.detailNextOrPrev

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.databasePrevMatch
import id.scode.kadeooredoo.data.db.entities.EventDetailMatch
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.invisible
import id.scode.kadeooredoo.toGMTFormat
import id.scode.kadeooredoo.ui.detailLeague.presenter.DetailMatchPresenter
import id.scode.kadeooredoo.ui.detailLeague.ui.next.NextMatchLeagueFragment.Companion.DETAIL_NEXT_MATCH_LEAGUE
import id.scode.kadeooredoo.ui.detailLeague.ui.previous.PreviousMatchLeagueFragment.Companion.DETAIL_PREV_MATCH_LEAGUE
import id.scode.kadeooredoo.ui.detailLeague.view.DetailMatchView
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import id.scode.kadeooredoo.visible
import kotlinx.android.synthetic.main.activity_detail_match_league.*
import kotlinx.android.synthetic.main.content_detail_match_league_more.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.info
import java.text.SimpleDateFormat

class DetailMatchLeagueActivity : AppCompatActivity(), DetailMatchView, AnkoLogger,
    TeamsView {

    private var eventPrevious: EventPrevious? = null
    private var eventNext: EventNext? = null

    // lookUp the teams | Logo
//    private var teams: MutableList<Team> = mutableListOf()
    private var teams: Team? = null
    private var teamsAway: MutableList<Team> = mutableListOf()

    // for favorite
    private var teamsPrevFavorite: Team? = null
    private var eventDetailMatchMutableListFavorite: EventDetailMatch? = null
    private var isFavorite: Boolean = false


    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
//    private var eventDetailMatchMutableList: MutableList<EventDetailMatch> = mutableListOf()
    private var eventDetailMatchMutableList: EventDetailMatch? = null
    private lateinit var detailMatchPresenter: DetailMatchPresenter
    private lateinit var teamsPresenter: TeamsPresenter

    private lateinit var progressBar: ProgressBar


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
        teamsPresenter = TeamsPresenter(this, request, gson)


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

            fab_favorite.setOnClickListener{
                if (isFavorite) info("soon") else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
            }
        } else if (eventNext != null) {
            eventNext?.also {
                it.idEvent?.let { it1 -> detailMatchPresenter.getDetailMatchList(it1) }
            }

            fab_favorite.setOnClickListener{
                info("soon")
            }
        }


    }


    private fun setFavorite() {
        if (isFavorite)
            fab_favorite?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            fab_favorite?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun addToFavorite() {
        try {
            databasePrevMatch.use {
                info("inserting data ${eventDetailMatchMutableListFavorite?.strEvent} - ${teamsPrevFavorite?.teamBadge}")
                insert(
                    Team.TABLE_FAVORITE_PREV,
                    Team.TEAM_ID to teamsPrevFavorite?.teamId,
                    Team.TEAM_BADGE to teamsPrevFavorite?.teamBadge,

                    EventDetailMatch.EVENT to eventDetailMatchMutableListFavorite?.strEvent,
                    EventDetailMatch.SEASON to eventDetailMatchMutableListFavorite?.strSeason,
                    EventDetailMatch.HOME_TEAM to eventDetailMatchMutableListFavorite?.strHomeTeam,
                    EventDetailMatch.HOME_SCORE to eventDetailMatchMutableListFavorite?.intHomeScore,
                    EventDetailMatch.AWAY_TEAM to eventDetailMatchMutableListFavorite?.strAwayTeam,
                    EventDetailMatch.AWAY_SCORE to eventDetailMatchMutableListFavorite?.intAwayScore
                )
                fab_favorite.snackbar("Added to favorite").show()
            }
        } catch (e: SQLiteConstraintException) {
            fab_favorite.snackbar("error ${e.localizedMessage}").show()
        }
    }

    override fun showTeamList(data: List<Team>?) {
        info("try show jersey team list : process")

        val zero = 0
        listOf(teams).toMutableList().clear()

        data?.let {

            listOf(teams).toMutableList().addAll(it)
            teamsPrevFavorite = Team(
                teamId = it[zero].teamId,
                teamBadge = it[zero].teamBadge
            )
        }

        Glide.with(this)
            .load(data?.get(0)?.teamBadge)
            .into(img_home_team_jersey)

        info("try show jersey team LOOKUP : done")
    }

    override fun showTeamAwayList(data: List<Team>?) {
        info("try show jersey team away LOOKUP : process")
        teamsAway.clear()
        data?.let {
            teamsAway.addAll(it)
        }

        Glide.with(this)
            .load(data?.get(0)?.teamBadge)
            .into(img_away_team_jersey)

        info("try show jersey team away list : done")
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailMatch(data: List<EventDetailMatch>?) {
        info("try show team list : process")

        val zero = 0

        listOf(eventDetailMatchMutableList).toMutableList().clear()

        data?.let {

            listOf(eventDetailMatchMutableList).toMutableList().addAll(it)

            setDataMatch(it[zero])
            eventDetailMatchMutableListFavorite = EventDetailMatch(
                strEvent = it[zero].strEvent,
                strSeason = it[zero].strSeason,

                strHomeTeam = it[zero].strHomeTeam,
                intHomeScore = it[zero].intHomeScore,

                strAwayTeam = it[zero].strAwayTeam,
                intAwayScore = it[zero].intAwayScore
//
//                dateEvent = it.dateEvent,
//                strTime = it.strTime,
//                strLocked = it.strLocked,
//
//                strHomeFormation = it.strHomeFormation,
//                strAwayFormation = it.strAwayFormation,
//
//                strHomeGoalDetails = it.strHomeGoalDetails,
//                strAwayGoalDetails = it.strAwayGoalDetails

            )
        }
        info("try show team list : done")
    }

    // !long code
    @SuppressLint("SimpleDateFormat")
    private fun setDataMatch(item: EventDetailMatch) {

        info("test : ${item.strFilename}")

        // get Logo
        item.idHomeTeam?.let {
            teamsPresenter.getDetailLeagueTeamList(it)
            info("looking for logo $it")
        }
        item.idAwayTeam?.let {
            teamsPresenter.getDetailLeagueTeamAwayList(it)
            info("looking for logo $it")
        }

        txt_str_events.text = item.strEvent
        txt_str_seasons.text = item.strSeason

        txt_home_team.text = item.strHomeTeam
        val scoreHome = item.intHomeScore
        txt_score_home.also {
            when (scoreHome) {
                null -> it.text = "-"
                else -> it.text = scoreHome
            }
        }

        txt_away_team.text = item.strAwayTeam
        val scoreAway = item.intAwayScore
        txt_score_away.also {
            when (scoreAway) {
                null -> it.text = "-"
                else -> it.text = scoreAway
            }
        }

        item.dateEvent?.let { date ->
            txt_date_event.text = date

            item.strTime?.let { time ->

                val timeEvent = toGMTFormat(date, time)
                info(
                    """
                    GMT+7 event   : $timeEvent
                    DEFAULT event : $date $time
                """.trimIndent()
                ) //GMT+8 ?

                val timeFormat = SimpleDateFormat("HH:mm:ss")
                timeEvent?.let {
                    val getTime = timeFormat.format(it)
                    txt_str_time_event.text = getTime
                }

            }
        }

        txt_unlocked_event.text = item.strLocked


        // description match
        txt_formation.also { txt ->
            txt.text = item.strHomeFormation
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_formation_aw.also { txt ->
            txt.text = item.strAwayFormation
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_goals_detail.also { txt ->
            txt.text = item.strHomeGoalDetails
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_goals_detail_aw.also { txt ->
            txt.text = item.strAwayGoalDetails
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_shots.also { txt ->
            txt.text = item.intHomeShots
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_shots_aw.also { txt ->
            txt.text = item.intAwayShots
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_red.also { txt ->
            txt.text = item.strHomeRedCards
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_red_aw.also { txt ->
            txt.text = item.strAwayRedCards
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_yl.also { txt ->
            txt.text = item.strHomeYellowCards
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_yl_aw.also { txt ->
            txt.text = item.strAwayYellowCards
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }



        txt_gk.also { txt ->
            txt.text = item.strHomeLineupGoalkeeper
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_gk_aw.also { txt ->
            txt.text = item.strAwayLineupGoalkeeper
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }


        txt_def.also { txt ->
            txt.text = item.strHomeLineupDefense
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_def_aw.also { txt ->
            txt.text = item.strAwayLineupDefense
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_md.also { txt ->
            txt.text = item.strHomeLineupMidfield
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_md_aw.also { txt ->
            txt.text = item.strAwayLineupMidfield
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_fw.also { txt ->
            txt.text = item.strHomeLineupForward
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_fw_aw.also { txt ->
            txt.text = item.strAwayLineupForward
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_sb.also { txt ->
            txt.text = item.strHomeLineupSubstitutes
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_sb_aw.also { txt ->
            txt.text = item.strAwayLineupSubstitutes
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_twitter1.text = item.strTweet1

        info("forgive aku :( : ${item.strDate}")
        scrollMethodText()

    }

    // detail scroll a textView
    private fun scrollMethodText() {
        txt_goals_detail.movementMethod = ScrollingMovementMethod()
        txt_formation.movementMethod = ScrollingMovementMethod()
        txt_formation_aw.movementMethod = ScrollingMovementMethod()

        txt_goals_detail.movementMethod = ScrollingMovementMethod()
        txt_goals_detail_aw.movementMethod = ScrollingMovementMethod()

        txt_red.movementMethod = ScrollingMovementMethod()
        txt_red_aw.movementMethod = ScrollingMovementMethod()

        txt_yl.movementMethod = ScrollingMovementMethod()
        txt_yl_aw.movementMethod = ScrollingMovementMethod()

        txt_gk.movementMethod = ScrollingMovementMethod()
        txt_gk_aw.movementMethod = ScrollingMovementMethod()

        txt_def.movementMethod = ScrollingMovementMethod()
        txt_def_aw.movementMethod = ScrollingMovementMethod()

        txt_md.movementMethod = ScrollingMovementMethod()
        txt_md_aw.movementMethod = ScrollingMovementMethod()

        txt_fw.movementMethod = ScrollingMovementMethod()
        txt_fw_aw.movementMethod = ScrollingMovementMethod()

        txt_sb.movementMethod = ScrollingMovementMethod()
        txt_sb_aw.movementMethod = ScrollingMovementMethod()
    }
}

