package id.scode.kadeooredoo.ui.detailleague.ui.detailnextorprevandfavorite

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.*
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.detailleague.adapter.RvNextMatchLeagueAdapter
import id.scode.kadeooredoo.ui.detailleague.adapter.RvPrevMatchLeagueAdapter
import id.scode.kadeooredoo.ui.detailleague.presenter.DetailMatchPresenter
import id.scode.kadeooredoo.ui.detailleague.ui.next.NextMatchLeagueFragment.Companion.DETAIL_NEXT_MATCH_LEAGUE
import id.scode.kadeooredoo.ui.detailleague.ui.previous.PreviousMatchLeagueFragment.Companion.DETAIL_PREV_MATCH_LEAGUE
import id.scode.kadeooredoo.ui.detailleague.view.DetailMatchView
import id.scode.kadeooredoo.ui.eventteam.adapter.EventTeamNextAdapter
import id.scode.kadeooredoo.ui.eventteam.adapter.EventTeamPrevAdapter
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.ui.team.TeamsFragment.Companion.DETAIL_KEY
import id.scode.kadeooredoo.ui.home.ui.team.TeamsFragment.Companion.DETAIL_KEY_SCORE
import id.scode.kadeooredoo.ui.home.view.TeamsView
import kotlinx.android.synthetic.main.activity_detail_match_league.*
import kotlinx.android.synthetic.main.content_detail_match_league_more.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.info
import java.text.SimpleDateFormat

/**
 * SCRIPT online ['api']
 * SCRIPT offline ['Anko SQLite']
 */

class DetailMatchLeagueActivity : AppCompatActivity(), DetailMatchView, AnkoLogger,
    TeamsView {

    private var eventPrevious: EventPrevious? = null
    private var eventNext: EventNext? = null

    // lookUp the teams | Logo
    private var teams: Team? = null
    private var teamsAway: Team? = null

    // for favorite | SCRIPT offline ['Anko SQLite']
    private var teamsPrevHomeFavorite: Team? = null
    private var teamsPrevAwayFavorite: Team? = null

    private var eventDetailMatchMutableListFavorite: EventDetailMatch? = null
    private var favTeamJoinDetail: FavTeamJoinDetail? = null
    private var isFavorite: Boolean = false

    private var favoriteStateDataSet: String? = null
    private var favoriteQueryState: String? = null
    private var homeScore: String? = null

    private lateinit var id: String


    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
    private var eventDetailMatchMutableList: EventDetailMatch? = null
    private lateinit var detailMatchPresenter: DetailMatchPresenter
    private lateinit var teamsPresenter: TeamsPresenter

    private lateinit var progressBar: ProgressBar

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
        intent?.also {
            eventPrevious = it.getParcelableExtra(DETAIL_PREV_MATCH_LEAGUE)
            eventNext = it.getParcelableExtra(DETAIL_NEXT_MATCH_LEAGUE)
            favTeamJoinDetail = it.getParcelableExtra(DETAIL_KEY) // SCRIPT offline ['Anko SQLite']
        }
        when {
            eventPrevious != null -> {

                favoriteQueryState =
                    getString(R.string.detail_match_league_activity_event_previous_mi)

                eventPrevious?.also {
                    it.idEvent?.let { it1 ->

                        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                            EspressoIdlingResource.increment()
                        }

                        detailMatchPresenter.getDetailMatchList(it1)
                        info("http://$LOOKUP_EVENT WITH $it1")
                        id = it1
                        favoriteState(
                            favoriteStateDataSet,
                            favoriteQueryState
                        ) // check the match prev team has been save ? return boolean true
                        setFavorite()
                    }
                }
                fab_favorite.setOnClickListener {
                    info("click fav -> $favoriteQueryState")
                    if (isFavorite) removeFromFavorite(favoriteQueryState) else addToFavorite(
                        favoriteQueryState
                    )
                    isFavorite = !isFavorite
                    setFavorite()
                }

            }
            eventNext != null -> {

                favoriteQueryState = getString(R.string.detail_match_league_activity_event_next_mi)

                eventNext?.also {
                    it.idEvent?.let { it1 ->

                        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                            EspressoIdlingResource.increment()
                        }

                        detailMatchPresenter.getDetailMatchList(it1)
                        info("http://$LOOKUP_EVENT WITH $it1")
                        id = it1
                        favoriteState(
                            favoriteStateDataSet,
                            favoriteQueryState
                        ) // check the match prev team has been save ? return boolean true
                        setFavorite()
                    }
                }

                fab_favorite.setOnClickListener {
                    info("click fav -> $favoriteQueryState")
                    if (isFavorite) removeFromFavorite(favoriteQueryState) else addToFavorite(
                        favoriteQueryState
                    )
                    isFavorite = !isFavorite
                    setFavorite()
                }

            }
            // SCRIPT offline ['Anko SQLite']
            favTeamJoinDetail == null -> { // null logic

                info("load fav match detail : ${favTeamJoinDetail?.eventId}")

                intent?.also {

                    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                    id = it.getStringExtra(DETAIL_KEY)
                    homeScore = it.getStringExtra(DETAIL_KEY_SCORE)
                    favoriteStateDataSet =
                        getString(R.string.detail_match_league_activity_favorite_state)


                    info(
                        """
                        what ur event id i would load on anko sqlLite $id
                        score $homeScore 
                    """.trimIndent()
                    )

                    // setPosQuery
                    favoriteQueryState = if (homeScore == "null") {
                        info("score not yet")
                        getString(R.string.detail_match_league_activity_event_next_mi)
                    } else {
                        info("score [have]")
                        getString(R.string.detail_match_league_activity_event_previous_mi)
                    }



                    info("user lookDetail and the query state is $favoriteQueryState")
                    favoriteState(
                        favoriteStateDataSet,
                        favoriteQueryState
                    ) // check the match prev team has been save ? return boolean true
                    setFavorite()

                }

                fab_favorite.setOnClickListener {
                    if (isFavorite) removeFromFavorite(favoriteQueryState)
                    isFavorite = !isFavorite
                    setFavorite()
                }

            }
            // check the match prev team has been save ? return boolean true
        }

    }

    // SCRIPT offline ['Anko SQLite']
    private fun favoriteState(favoriteStateDataSet: String?, favoriteQueryState: String?) {

        info("favoriteQueryState is $favoriteQueryState")

        when (favoriteQueryState) {

            getString(R.string.detail_match_league_activity_event_previous_mi) -> {

                databaseEventPrevMatch.use {
                    val result =
                        select(Team.TABLE_FAVORITE_PREV)
                            .whereArgs(
                                "(EVENT_ID = {id})",
                                "id" to id
                            )
                    val favorite = result.parseList(classParser<FavTeamJoinDetail>())
                    val zero = 0
                    if (favorite.isNotEmpty()) {
                        info(
                            """
                    this match have been added on favorite PREV list with id $id
                    nameEvent ${favorite[zero].event}
                """.trimIndent()
                        )
                        isFavorite = true

                        if (favoriteStateDataSet == getString(R.string.detail_match_league_activity_favorite_state)) setDataMatchFavorite(
                            favorite[zero]
                        )

                    } else info("this match have not been add on favorite PREV list the id $id")
                }

            }
            getString(R.string.detail_match_league_activity_event_next_mi) -> {

                databaseEventNextMatch.use {
                    val result =
                        select(Team.TABLE_FAVORITE_NEXT)
                            .whereArgs(
                                "(EVENT_ID = {id})",
                                "id" to id
                            )
                    val favorite = result.parseList(classParser<FavTeamJoinDetail>())
                    val zero = 0
                    if (favorite.isNotEmpty()) {
                        info(
                            """
                    this match have been added on favorite NEXT list with id $id
                    nameEvent ${favorite[zero].event}
                """.trimIndent()
                        )
                        isFavorite = true

                        if (favoriteStateDataSet == getString(R.string.detail_match_league_activity_favorite_state)) setDataMatchFavorite(
                            favorite[zero]
                        )

                    } else info("this match have not been add on favorite NEXT list the id $id")
                }

            }

        }

    }

    // SCRIPT offline ['Anko SQLite']
    private fun setFavorite() {
        if (isFavorite) {
            info("set icon fab_favorite R.drawable.ic_added_to_favorites")
            fab_favorite?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        } else {
            info("set icon fab_favorite R.drawable.ic_add_to_favorites")
            fab_favorite?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        }
    }

    // !long code CTRL + [dot] fold | SCRIPT offline ['Anko SQLite']
    private fun addToFavorite(favoriteQueryState: String?) {

        when (favoriteQueryState) {
            getString(R.string.detail_match_league_activity_event_previous_mi) -> {

                try {
                    databaseEventPrevMatch.use {
                        info(
                            """
                        inserting data 
                        ${eventDetailMatchMutableListFavorite?.idEvent} - 
                        ${eventDetailMatchMutableListFavorite?.strEvent} -
                        ${teamsPrevHomeFavorite?.teamBadge}
                    """.trimIndent()
                        )
                        insert(
                            Team.TABLE_FAVORITE_PREV,
                            Team.TEAM_ID to teamsPrevHomeFavorite?.teamId,
                            Team.TEAM_ID_AWAY to teamsPrevAwayFavorite?.teamId,
                            Team.TEAM_BADGE to teamsPrevHomeFavorite?.teamBadge,
                            Team.TEAM_BADGE_AWAY to teamsPrevAwayFavorite?.teamBadge,

                            EventDetailMatch.EVENT_ID to eventDetailMatchMutableListFavorite?.idEvent,
                            EventDetailMatch.EVENT to eventDetailMatchMutableListFavorite?.strEvent,
                            EventDetailMatch.SEASON to eventDetailMatchMutableListFavorite?.strSeason,

                            EventDetailMatch.HOME_TEAM to eventDetailMatchMutableListFavorite?.strHomeTeam,
                            EventDetailMatch.HOME_SCORE to eventDetailMatchMutableListFavorite?.intHomeScore,

                            EventDetailMatch.AWAY_TEAM to eventDetailMatchMutableListFavorite?.strAwayTeam,
                            EventDetailMatch.AWAY_SCORE to eventDetailMatchMutableListFavorite?.intAwayScore,

                            EventDetailMatch.DATE_EVENT to eventDetailMatchMutableListFavorite?.dateEvent,
                            EventDetailMatch.TIME_EVENT to eventDetailMatchMutableListFavorite?.strTime,

                            EventDetailMatch.LOCKED to eventDetailMatchMutableListFavorite?.strLocked,
                            EventDetailMatch.SPORT_STR to eventDetailMatchMutableListFavorite?.strSport,

                            EventDetailMatch.HOME_FORMATION to eventDetailMatchMutableListFavorite?.strHomeFormation,
                            EventDetailMatch.AWAY_FORMATION to eventDetailMatchMutableListFavorite?.strAwayFormation,

                            EventDetailMatch.HOME_GOALS_DETAIL to eventDetailMatchMutableListFavorite?.strHomeGoalDetails,
                            EventDetailMatch.AWAY_GOALS_DETAIL to eventDetailMatchMutableListFavorite?.strAwayGoalDetails,

                            EventDetailMatch.HOME_SHOTS to eventDetailMatchMutableListFavorite?.intHomeShots,
                            EventDetailMatch.AWAY_SHOTS to eventDetailMatchMutableListFavorite?.intAwayShots,

                            EventDetailMatch.HOME_RED_CARD to eventDetailMatchMutableListFavorite?.strHomeRedCards,
                            EventDetailMatch.AWAY_RED_CARD to eventDetailMatchMutableListFavorite?.strAwayRedCards,

                            EventDetailMatch.HOME_YL_CARD to eventDetailMatchMutableListFavorite?.strHomeYellowCards,
                            EventDetailMatch.AWAY_YL_CARD to eventDetailMatchMutableListFavorite?.strAwayYellowCards,

                            EventDetailMatch.HOME_GK_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupGoalkeeper,
                            EventDetailMatch.AWAY_GK_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupGoalkeeper,

                            EventDetailMatch.HOME_DEF_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupDefense,
                            EventDetailMatch.AWAY_DEF_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupDefense,
                            EventDetailMatch.HOME_MID_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupMidfield,
                            EventDetailMatch.AWAY_MID_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupMidfield,

                            EventDetailMatch.HOME_FW_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupForward,
                            EventDetailMatch.AWAY_FW_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupForward,
                            EventDetailMatch.HOME_SUBST to eventDetailMatchMutableListFavorite?.strHomeLineupSubstitutes,
                            EventDetailMatch.AWAY_SUBST to eventDetailMatchMutableListFavorite?.strAwayLineupSubstitutes,

                            EventDetailMatch.LINK_TW to eventDetailMatchMutableListFavorite?.strTweet1

                        )
                        fab_favorite.snackbar("Added to favorite").show()
                    }
                } catch (e: SQLiteConstraintException) {
                    fab_favorite.snackbar("error ${e.localizedMessage}").show()
                }

            }
            getString(R.string.detail_match_league_activity_event_next_mi) -> {

                try {
                    databaseEventNextMatch.use {
                        info(
                            """
                        inserting data 
                        ${eventDetailMatchMutableListFavorite?.idEvent} - 
                        ${eventDetailMatchMutableListFavorite?.strEvent} -
                        ${teamsPrevHomeFavorite?.teamBadge}
                    """.trimIndent()
                        )
                        insert(
                            Team.TABLE_FAVORITE_NEXT,
                            Team.TEAM_ID to teamsPrevHomeFavorite?.teamId,
                            Team.TEAM_ID_AWAY to teamsPrevAwayFavorite?.teamId,
                            Team.TEAM_BADGE to teamsPrevHomeFavorite?.teamBadge,
                            Team.TEAM_BADGE_AWAY to teamsPrevAwayFavorite?.teamBadge,

                            EventDetailMatch.EVENT_ID to eventDetailMatchMutableListFavorite?.idEvent,
                            EventDetailMatch.EVENT to eventDetailMatchMutableListFavorite?.strEvent,
                            EventDetailMatch.SEASON to eventDetailMatchMutableListFavorite?.strSeason,

                            EventDetailMatch.HOME_TEAM to eventDetailMatchMutableListFavorite?.strHomeTeam,
                            EventDetailMatch.HOME_SCORE to eventDetailMatchMutableListFavorite?.intHomeScore,

                            EventDetailMatch.AWAY_TEAM to eventDetailMatchMutableListFavorite?.strAwayTeam,
                            EventDetailMatch.AWAY_SCORE to eventDetailMatchMutableListFavorite?.intAwayScore,

                            EventDetailMatch.DATE_EVENT to eventDetailMatchMutableListFavorite?.dateEvent,
                            EventDetailMatch.TIME_EVENT to eventDetailMatchMutableListFavorite?.strTime,

                            EventDetailMatch.LOCKED to eventDetailMatchMutableListFavorite?.strLocked,
                            EventDetailMatch.SPORT_STR to eventDetailMatchMutableListFavorite?.strSport,

                            EventDetailMatch.HOME_FORMATION to eventDetailMatchMutableListFavorite?.strHomeFormation,
                            EventDetailMatch.AWAY_FORMATION to eventDetailMatchMutableListFavorite?.strAwayFormation,

                            EventDetailMatch.HOME_GOALS_DETAIL to eventDetailMatchMutableListFavorite?.strHomeGoalDetails,
                            EventDetailMatch.AWAY_GOALS_DETAIL to eventDetailMatchMutableListFavorite?.strAwayGoalDetails,

                            EventDetailMatch.HOME_SHOTS to eventDetailMatchMutableListFavorite?.intHomeShots,
                            EventDetailMatch.AWAY_SHOTS to eventDetailMatchMutableListFavorite?.intAwayShots,

                            EventDetailMatch.HOME_RED_CARD to eventDetailMatchMutableListFavorite?.strHomeRedCards,
                            EventDetailMatch.AWAY_RED_CARD to eventDetailMatchMutableListFavorite?.strAwayRedCards,

                            EventDetailMatch.HOME_YL_CARD to eventDetailMatchMutableListFavorite?.strHomeYellowCards,
                            EventDetailMatch.AWAY_YL_CARD to eventDetailMatchMutableListFavorite?.strAwayYellowCards,

                            EventDetailMatch.HOME_GK_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupGoalkeeper,
                            EventDetailMatch.AWAY_GK_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupGoalkeeper,

                            EventDetailMatch.HOME_DEF_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupDefense,
                            EventDetailMatch.AWAY_DEF_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupDefense,
                            EventDetailMatch.HOME_MID_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupMidfield,
                            EventDetailMatch.AWAY_MID_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupMidfield,

                            EventDetailMatch.HOME_FW_LINE to eventDetailMatchMutableListFavorite?.strHomeLineupForward,
                            EventDetailMatch.AWAY_FW_LINE to eventDetailMatchMutableListFavorite?.strAwayLineupForward,
                            EventDetailMatch.HOME_SUBST to eventDetailMatchMutableListFavorite?.strHomeLineupSubstitutes,
                            EventDetailMatch.AWAY_SUBST to eventDetailMatchMutableListFavorite?.strAwayLineupSubstitutes,

                            EventDetailMatch.LINK_TW to eventDetailMatchMutableListFavorite?.strTweet1

                        )
                        fab_favorite.snackbar("Added to favorite").show()
                    }
                } catch (e: SQLiteConstraintException) {
                    fab_favorite.snackbar("error ${e.localizedMessage}").show()
                }

            }
        }
    }

    // SCRIPT offline ['Anko SQLite']
    private fun removeFromFavorite(favoriteQueryState: String?) {

        when (favoriteQueryState) {
            getString(R.string.detail_match_league_activity_event_previous_mi) -> {

                try {
                    info("try remove event prev id : $id, process")
                    databaseEventPrevMatch.use {
                        delete(
                            Team.TABLE_FAVORITE_PREV,
                            "(EVENT_ID = {id})",
                            "id" to id
                        )
                    }
                    info("try remove event prev id : $id, success")
                    finish()
                } catch (e: SQLiteConstraintException) {
                    fab_favorite.snackbar("error ${e.localizedMessage}").show()
                }

            }
            getString(R.string.detail_match_league_activity_event_next_mi) -> {

                try {
                    info("try remove event next id : $id, process")
                    databaseEventNextMatch.use {
                        delete(
                            Team.TABLE_FAVORITE_NEXT,
                            "(EVENT_ID = {id})",
                            "id" to id
                        )
                    }
                    info("try remove event next id : $id, success")
                    finish()
                } catch (e: SQLiteConstraintException) {
                    fab_favorite.snackbar("error ${e.localizedMessage}").show()
                }

            }
        }

    }

    override fun showTeamList(
        data: List<Team>?,
        checkIdTeamHome: String?,
        position: Int?,
        holderRvPrevMatchLeagueAdapter: RvPrevMatchLeagueAdapter.ViewHolder?,
        holderRvNextMatchLeagueAdapter: RvNextMatchLeagueAdapter.ViewHolder?,
        holderEventTeamPrevAdapter: EventTeamPrevAdapter.ViewHolder?,
        holderEventTeamNextAdapter: EventTeamNextAdapter.ViewHolder?
    ) {

        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }

        info("try show jersey team list : process")

        val zero = 0
        listOf(teams).toMutableList().clear()

        data?.let {

            listOf(teams).toMutableList().addAll(it)
            // SCRIPT offline ['Anko SQLite']
            teamsPrevHomeFavorite = Team(
                teamId = it[zero].teamId,
                teamBadge = it[zero].teamBadge
            )
        }

        Glide.with(this)
            .load(data?.get(0)?.teamBadge)
            .into(img_home_team_jersey)

        info("try show jersey team LOOKUP : done")
    }

    override fun showTeamAwayList(
        data: List<Team>?,
        checkIdTeamAway: String?,
        position: Int?,
        holderRvPrevMatchLeagueAdapter: RvPrevMatchLeagueAdapter.ViewHolder?,
        holderRvNextMatchLeagueAdapter: RvNextMatchLeagueAdapter.ViewHolder?,
        holderEventTeamPrevAdapter: EventTeamPrevAdapter.ViewHolder?,
        holderEventTeamNextAdapter: EventTeamNextAdapter.ViewHolder?
    ) {

        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }
        info("try show jersey team away LOOKUP : process")

        val zero = 0
        listOf(teamsAway).toMutableList().clear()
        data?.let {
            listOf(teamsAway).toMutableList().addAll(it)
            // SCRIPT offline ['Anko SQLite']
            teamsPrevAwayFavorite = Team(
                teamId = it[zero].teamId,
                teamBadge = it[zero].teamBadge
            )
        }

        Glide.with(this)
            .load(data?.get(0)?.teamBadge)
            .into(img_away_team_jersey)

        info("try show jersey team away list : done")
    }

    override fun exceptionNullObject(msg: String) {
        // just for in home | search
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showDetailMatch(data: List<EventDetailMatch>?) {

        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }
        info("try show team list : process")

        val zero = 0

        listOf(eventDetailMatchMutableList).toMutableList().clear()

        data?.let {

            listOf(eventDetailMatchMutableList).toMutableList().addAll(it)

            setDataMatch(it[zero])
            // SCRIPT offline ['Anko SQLite']
            eventDetailMatchMutableListFavorite = EventDetailMatch(
                idEvent = it[zero].idEvent,
                strEvent = it[zero].strEvent,
                strSeason = it[zero].strSeason,

                strHomeTeam = it[zero].strHomeTeam,
                intHomeScore = it[zero].intHomeScore,

                strAwayTeam = it[zero].strAwayTeam,
                intAwayScore = it[zero].intAwayScore,

                dateEvent = it[zero].dateEvent,
                strTime = it[zero].strTime,
                strLocked = it[zero].strLocked,

                strHomeFormation = it[zero].strHomeFormation,
                strAwayFormation = it[zero].strAwayFormation,

                strHomeGoalDetails = it[zero].strHomeGoalDetails,
                strAwayGoalDetails = it[zero].strAwayGoalDetails,

                intHomeShots = it[zero].intHomeShots,
                intAwayShots = it[zero].intAwayShots,

                strHomeRedCards = it[zero].strHomeRedCards,
                strAwayRedCards = it[zero].strAwayRedCards,

                strHomeYellowCards = it[zero].strHomeYellowCards,
                strAwayYellowCards = it[zero].strAwayYellowCards,

                strHomeLineupGoalkeeper = it[zero].strHomeLineupGoalkeeper,
                strAwayLineupGoalkeeper = it[zero].strAwayLineupGoalkeeper,

                strHomeLineupDefense = it[zero].strHomeLineupDefense,
                strAwayLineupDefense = it[zero].strAwayLineupDefense,
                strHomeLineupMidfield = it[zero].strHomeLineupMidfield,
                strAwayLineupMidfield = it[zero].strAwayLineupMidfield,

                strHomeLineupForward = it[zero].strHomeLineupForward,
                strAwayLineupForward = it[zero].strAwayLineupForward,
                strHomeLineupSubstitutes = it[zero].strHomeLineupSubstitutes,
                strAwayLineupSubstitutes = it[zero].strAwayLineupSubstitutes,

                strTweet1 = it[zero].strTweet1

            )
        }
        info("try show team list : done")
    }

    // !long code CTRL + [dot] fold code
    @SuppressLint("SimpleDateFormat")
    private fun setDataMatch(
        item: EventDetailMatch
    ) {

        info("test : ${item.strFilename}")

        // get Logo
        item.idHomeTeam?.let {

            if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                EspressoIdlingResource.increment()
            }

            teamsPresenter.getDetailLeagueTeamList(it)
            info("looking for logo $it")
        }
        item.idAwayTeam?.let {

            if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                EspressoIdlingResource.increment()
            }

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

    // detail scroll a textView | !long code CTRL + [dot] fold
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

    // !long code CTRL + [dot] fold code
    @SuppressLint("SimpleDateFormat")
    private fun setDataMatchFavorite(item: FavTeamJoinDetail) {
        progressBar.gone()
        // set Logo
        Glide.with(this)
            .load(item.teamBadge)
            .into(img_home_team_jersey)

        Glide.with(this)
            .load(item.teamBadgeAway)
            .into(img_away_team_jersey)

        txt_str_events.text = item.event
        txt_str_seasons.text = item.season

        txt_home_team.text = item.homeTeam
        val scoreHome = item.homeScore
        txt_score_home.also {
            when (scoreHome) {
                null -> it.text = "-"
                else -> it.text = scoreHome
            }
        }

        txt_away_team.text = item.awayTeam
        val scoreAway = item.awayScore
        txt_score_away.also {
            when (scoreAway) {
                null -> it.text = "-"
                else -> it.text = scoreAway
            }
        }

        item.dateEvent?.let { date ->
            txt_date_event.text = date

            item.timeEvent?.let { time ->

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

        txt_unlocked_event.text = item.locked


        // description match
        txt_formation.also { txt ->
            txt.text = item.homeFormation
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_formation_aw.also { txt ->
            txt.text = item.awayFormation
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_goals_detail.also { txt ->
            txt.text = item.homeGoalsDetail
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_goals_detail_aw.also { txt ->
            txt.text = item.awayGoalsDetail
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_shots.also { txt ->
            txt.text = item.hmShot
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_shots_aw.also { txt ->
            txt.text = item.awShot
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_red.also { txt ->
            txt.text = item.hmRedCard
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_red_aw.also { txt ->
            txt.text = item.awRedCard
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_yl.also { txt ->
            txt.text = item.hmYlCard
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_yl_aw.also { txt ->
            txt.text = item.awYlCard
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }



        txt_gk.also { txt ->
            txt.text = item.hmGkLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_gk_aw.also { txt ->
            txt.text = item.awGkLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }


        txt_def.also { txt ->
            txt.text = item.hmDefLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_def_aw.also { txt ->
            txt.text = item.awDefLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_md.also { txt ->
            txt.text = item.hmMidLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_md_aw.also { txt ->
            txt.text = item.awMidLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_fw.also { txt ->
            txt.text = item.hmFwLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_fw_aw.also { txt ->
            txt.text = item.awFwLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }

        txt_sb.also { txt ->
            txt.text = item.hmSubstLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_sb_aw.also { txt ->
            txt.text = item.awSubstLine
            txt.setOnClickListener {
                it.snackbar("${txt.text}")
            }
            if (txt.text.isNullOrEmpty()) {
                txt.text = getString(R.string.empty_strips)
            }
        }
        txt_twitter1.text = item.linkTw

        scrollMethodText()
    }
}