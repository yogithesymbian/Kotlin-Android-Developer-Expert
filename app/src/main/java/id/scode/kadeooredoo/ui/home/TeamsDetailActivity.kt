package id.scode.kadeooredoo.ui.home

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.Favorite
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY_FAV_TEAM
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

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

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamDescription: TextView

    private lateinit var teamsPresenter: TeamsPresenter
    private var teams: Team? = null

    private lateinit var id: String
    private var idOnline: String? = null

    private var favoriteStateDataSet: String? = null

    //menu favorite
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    // description language
    private lateinit var language: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.also {
            idOnline = intent.getStringExtra(DETAIL_KEY)
        }

        supportActionBar?.title = getString(R.string.title_team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        verticalLayout {
            padding = dip(16)

            cardView {
                padding = dip(8)
                teamName = textView {
                    padding = dip(16)
                    textSize = 18f
                }.lparams(wrapContent, wrapContent) {
                    margin = dip(16)
                    gravity = Gravity.CENTER
                }
            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }

            teamBadge = imageView()
                .lparams(width = dip(125), height = dip(125)) {
                    margin = dip(8)
                }

            cardView {
                padding = dip(8)
                teamDescription = textView {
                    padding = dip(16)
                    textSize = 16f
                }

                progressBar = progressBar()

            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }

        }

        language = resources.getString(R.string.app_language)


        val request = ApiRepository()
        val gson = Gson()
        teamsPresenter = TeamsPresenter(this, request, gson)

        when {
            idOnline != null -> {
                id = idOnline as String
//                EspressoIdlingResource.increment()
                teamsPresenter.getDetailLeagueTeamList(id)
                info("http://$LOOKUP_TEAM")
                favoriteState(favoriteStateDataSet) // check the team has been save ? return boolean true
            }
            idOnline == null -> {
                @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
                id = intent.getStringExtra(DETAIL_KEY_FAV_TEAM)
                favoriteStateDataSet =
                    getString(R.string.detail_match_league_activity_favorite_state)
                favoriteState(favoriteStateDataSet) // check the team has been save ? return boolean true
            }
        }
    }

    private fun favoriteState(fromState: String?) {
        databaseTeams.use {
            val result =
                select(Favorite.TABLE_FAVORITE)
                    .whereArgs(
                        "(TEAM_ID = {id})",
                        "id" to id
                    )
            val favorite = result.parseList(classParser<Favorite>())
            val zero = 0
            if (favorite.isNotEmpty()) {
                isFavorite = true
                if (fromState == getString(R.string.detail_match_league_activity_favorite_state)) setDataTeamFavorite(
                    favorite[zero]
                )
            }
        }
    }

    private fun setDataTeamFavorite(item: Favorite) {

        Picasso.get().load(item.teamBadge).into(teamBadge)
        teamName.text = item.teamName
        teamDescription.text = item.teamName
//        teamDescription.also { desc ->
//            when (language) {
//                EN_LANG -> desc.text = item.teamDescEn
//                JP_LANG -> desc.text = item.teamDescJp
//            }
//        }
        progressBar.gone()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun addToFavorite() {
        if (teams?.teamId.isNullOrEmpty()) {
            teamBadge.snackbar("Please wait data loads, and try against")
                .show() // debug with low connection can insert null obj
            isFavorite = !isFavorite
        } else {
            try {
                databaseTeams.use {
                    info("inserting data ${teams?.teamId} - ${teams?.teamName}")
                    insert(
                        Favorite.TABLE_FAVORITE,
                        Favorite.TEAM_ID to teams?.teamId,
                        Favorite.TEAM_NAME to teams?.teamName,
                        Favorite.TEAM_BADGE to teams?.teamBadge
//                        Favorite.TEAM_DESC_EN to teams?.strDescriptionEN,
//                        Favorite.TEAM_DESC_JP to teams?.strDescriptionJP
                    )
                    teamBadge.snackbar("Added to favorite").show()
                }
            } catch (e: SQLiteConstraintException) {
                teamBadge.snackbar("error ${e.localizedMessage}").show()
            }
        }
    }

    private fun removeFromFavorite() {
        try {
            databaseTeams.use {
                delete(
                    Favorite.TABLE_FAVORITE,
                    "(TEAM_ID = {id})",
                    "id" to id
                )
            }
            finish()
        } catch (e: SQLiteConstraintException) {
            teamBadge.snackbar("error ${e.localizedMessage}").show()
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.gone()
    }

    override fun showTeamList(data: List<Team>?) {
//        if (!EspressoIdlingResource.idlingresource.isIdleNow) {
//            //Memberitahukan bahwa tugas sudah selesai dijalankan
//            EspressoIdlingResource.decrement()
//        }
        info("try show team list : process")
        val zero = 0

        data?.let {
            listOf(teams).toMutableList().addAll(it)

            Picasso.get().load(it[zero].teamBadge).into(teamBadge)

            teamName.text = it[zero].teamName
            teamDescription.also { desc ->
                when (language) {
                    EN_LANG -> desc.text = it[zero].strDescriptionEN
                    JP_LANG -> desc.text = it[zero].strDescriptionJP
                }
            }

            teams = Team(
                teamId = it[zero].teamId,
                teamName = it[zero].teamName,
                teamBadge = it[zero].teamBadge
//                strDescriptionEN = it[zero].strDescriptionEN,
//                strDescriptionJP = it[zero].strDescriptionJP
            )

            info("try show team list : done")
            info("hello teams -> ${it[zero].teamName}")

        }
    }

    override fun showTeamAwayList(data: List<Team>?) {
        // just for inside adapter previous and next
    }
}
