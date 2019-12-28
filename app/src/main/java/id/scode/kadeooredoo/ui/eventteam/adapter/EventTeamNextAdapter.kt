package id.scode.kadeooredoo.ui.eventteam.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.detailleague.adapter.RvNextMatchLeagueAdapter
import id.scode.kadeooredoo.ui.detailleague.adapter.RvNextMatchLeagueAdapter.Companion.logicOne
import id.scode.kadeooredoo.ui.detailleague.adapter.RvNextMatchLeagueAdapter.Companion.logicTwo
import id.scode.kadeooredoo.ui.detailleague.adapter.RvPrevMatchLeagueAdapter
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_next_match_league.*
import kotlinx.android.synthetic.main.item_next_match_league.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.text.SimpleDateFormat

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 27 12/27/19 6:21 AM 2019
 * id.scode.kadeooredoo.ui.eventteam.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */

// this class is mirror in package id.scode.kadeooredoo.ui.detailleague.adapter
class EventTeamNextAdapter(
    private val context: Context,
    private var items: List<EventNext>,
    private val listener: (EventNext) -> Unit
) : RecyclerView.Adapter<EventTeamNextAdapter.ViewHolder>(), Filterable,
    TeamsView, AnkoLogger {

    private var itemsInit: List<EventNext> = items
    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
    private var teams: Team? = null // teamsHome
    private var teamsAway: Team? = null // teamsAway

    private lateinit var teamsPresenter: TeamsPresenter
    private var progressBar: ProgressBar? = null
    private var progressBarAway: ProgressBar? = null

    // logic for badge saving 2 array with same id
    private var mutableListEventNextOne: MutableList<EventNext> = mutableListOf()
    private var mutableListEventNextTwo: MutableList<EventNext> = mutableListOf()

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {


        @SuppressLint("SimpleDateFormat", "PrivateResource")
        fun bindItem(
            item: EventNext,
            listener: (EventNext) -> Unit,
            teamsBadgeYoBall: Team?,
            logicPostBadge: Int,
            progressBar: ProgressBar?
        ) {
            // twice checking measure data is soccer
            if (item.strSport == SPORT) {
                Log.d(TAG_LOG, "first setData with ${item.strFilename}")
                txt_str_events_next.text = item.strEvent
                txt_str_seasons_next.text = item.strSeason

                txt_home_team_next.text = item.strHomeTeam
                val scoreHome = item.intHomeScore
                txt_score_home_next.also {
                    when (scoreHome) {
                        null -> it.text = "-"
                        else -> it.text = scoreHome
                    }
                }

                txt_away_team_next.text = item.strAwayTeam
                val scoreAway = item.intAwayScore
                txt_score_away_next.also {
                    when (scoreAway) {
                        null -> it.text = "-"
                        else -> it.text = scoreAway
                    }
                }

                item.dateEvent?.let { date ->
                    txt_date_event_next.text = date

                    item.strTime?.let { time ->

                        val timeEvent = toGMTFormat(date, time)
                        Log.d(
                            TAG_LOG,
                            """
                            GMT+7 event   : $timeEvent
                            DEFAULT event : $date $time
                            """.trimIndent()
                        ) //GMT+8 ?

                        val timeFormat = SimpleDateFormat("HH:mm:ss")

                        timeEvent?.let {
                            val getTime = timeFormat.format(it)
                            txt_str_time_next.text = getTime
                        }

                    }

                }

                txt_unlocked_event_next.text = item.strLocked

                when (logicPostBadge) {
                    1 -> img_home_team_jersey_next.let { img ->
                        Glide.with(this.itemView.context)
                            .asBitmap()
                            .load(teamsBadgeYoBall?.teamBadge)
                            .error(R.color.error_color_material_light)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(img)
                    }
                    2 -> img_away_team_jersey_next.let { img ->
                        Glide.with(this.itemView.context)
                            .asBitmap()
                            .load(teamsBadgeYoBall?.teamBadge)
                            .error(R.color.error_color_material_light)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(img)
                    }
                }

            } else {
                Log.d(
                    TAG_LOG, """
                    the data is'nt soccer
                    name team ${item.strFilename}
                    home ${item.strHomeTeam}
                """.trimIndent()
                )
            }

            progressBar?.let{
                if (it.isVisible){
                    containerView.setOnClickListener{con ->
                        Toast.makeText(con.context,"Please wait", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    containerView.setOnClickListener { listener(item) }
                }
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val onViewHolder =
            ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_next_match_league,
                    parent,
                    false
                )
            )
        progressBar = parent.progress_load_jersey_home_next
        progressBarAway = parent.progress_load_jersey_away_next
//        imageView = parent.img_away_team_jersey

        val request = ApiRepository()
        val gson = Gson()
        teamsPresenter = TeamsPresenter(this, request, gson)

        return onViewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        info(
            """
            [*]
            ${items.size}
        """.trimIndent()
        )

        mutableListEventNextOne.clear()//clear arr[beforeAdded]
        mutableListEventNextTwo.clear()

        for (i in items.indices) { //add item to anArray

            info("hello home ${items[i].idHomeTeam} | away ${items[i].idAwayTeam}")
            mutableListEventNextOne.add(items[i]) // assign items to arrayList[home]
            mutableListEventNextTwo.add(items[i]) // away

        }

        // call api with position and holder for re-bind , and measure item is match [check twice]
        for (i in mutableListEventNextTwo.indices) {

            val item1 = items[i].idAwayTeam
            val item2 = mutableListEventNextTwo[i].idAwayTeam

            val item3 = items[i].idHomeTeam
            val item4 = mutableListEventNextOne[i].idHomeTeam

            if (item1 == item2) { // re-check match or nah then presenter the data

                info(
                    """
                    [*] awayTeam
                    [$item1] match [$item2]
                """.trimIndent()
                )

                if (UJI_COBA_TESTING_FLAG == context.getString(R.string.isTest)){
                    EspressoIdlingResource.increment()
                }

                teamsPresenter.getDetailLeagueTeamAwayList(item1.toString(), position, holder3 = holder)

            }

            if (item3 == item4) { // re-check match or nah then presenter the data

                info(
                    """
                    [*] homeTeam
                    [$item3] match [$item4]
                """.trimIndent()
                )

                if (UJI_COBA_TESTING_FLAG == context.getString(R.string.isTest)){
                    EspressoIdlingResource.increment()
                }

                teamsPresenter.getDetailLeagueTeamList(item3.toString(), position, holder3 = holder)

            }

        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                items = results.values as List<EventNext>
                notifyDataSetChanged()
            }

            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                items = if (charString.isEmpty()) {
                    itemsInit //save before result data shown and return this for isEmpty
                } else {
                    val filterListener: List<EventNext> = items
                    for (row in items) {
                        row.let {
                            if (it.strHomeTeam?.toLowerCase()?.contains(charString.toLowerCase())!!
                                ||
                                it.strAwayTeam.toString().contains(charString.toLowerCase())
                            ) {
                                filterListener.toMutableList().add(row)
                            }
                        }
                    }
                    filterListener // set item from result filter
                }
                val filterResults = FilterResults()
                filterResults.values = items
                return filterResults
            }
        }
    }

    override fun showLoading() {
        progressBar?.visible()
        progressBarAway?.visible()
    }

    override fun hideLoading() {
        progressBar?.gone()
        progressBarAway?.gone()
    }

    override fun showTeamList(
        data: List<Team>?,
        checkIdTeamHome: String?,
        position: Int?,
        holderRvPrevMatchLeagueAdapter: RvPrevMatchLeagueAdapter.ViewHolder?,
        holderRvNextMatchLeagueAdapter: RvNextMatchLeagueAdapter.ViewHolder?,
        holderEventTeamPrevAdapter: EventTeamPrevAdapter.ViewHolder?,
        holderEventTeamNextAdapter: ViewHolder?
    ) {

        info("try show jersey team LOOKUP : process")
        listOf(teams).toMutableList().clear()

        data?.let {

            listOf(teams).toMutableList().addAll(it)

            teams = Team(
                teamId = it[0].teamId,
                teamBadge = it[0].teamBadge,
                teamName = it[0].teamName
            )

            // leak memory \ this can only solved by backEnd API | join badgeUrl into event | #lumenAPI
            for (i in mutableListEventNextOne.indices)
                reBindingAnEvent(
                    arrayListBind = mutableListEventNextOne,
                    listTeam = it,
                    forIterate = i,
                    viewHolder = holderEventTeamNextAdapter,
                    dataPosition = position,
                    teamBind = teams,
                    logicPostBadge = logicOne
                )
        }
        info("try show jersey team LOOKUP : done")
    }

    @SuppressLint("PrivateResource")
    override fun showTeamAwayList(
        data: List<Team>?,
        checkIdTeamAway: String?,
        position: Int?,
        holderRvPrevMatchLeagueAdapter: RvPrevMatchLeagueAdapter.ViewHolder?,
        holderRvNextMatchLeagueAdapter: RvNextMatchLeagueAdapter.ViewHolder?,
        holderEventTeamPrevAdapter: EventTeamPrevAdapter.ViewHolder?,
        holderEventTeamNextAdapter: ViewHolder?
    ) {

        info("try show jersey team away LOOKUP : process")
        listOf(teamsAway).toMutableList().clear()

        data?.let {

            listOf(teamsAway).toMutableList().addAll(it)

            teamsAway = Team(
                teamId = it[0].teamId,
                teamBadge = it[0].teamBadge,
                teamName = it[0].teamName
            )

            // leak memory \ this can only solved by backEnd API | join badgeUrl into event | #lumenAPI
            for (i in mutableListEventNextTwo.indices)
                reBindingAnEvent(
                    arrayListBind = mutableListEventNextTwo,
                    listTeam = it,
                    forIterate = i,
                    viewHolder = holderEventTeamNextAdapter,
                    dataPosition = position,
                    teamBind = teamsAway,
                    logicPostBadge = logicTwo
                )

        }
        info("try show jersey team away LOOKUP : done")
    }

    override fun exceptionNullObject(msg: String) {
        // just for in home | search
    }

    private fun reBindingAnEvent(
        arrayListBind: MutableList<EventNext>,
        listTeam: List<Team>,
        forIterate: Int,
        viewHolder: ViewHolder?,
        dataPosition: Int?,
        teamBind: Team?,
        logicPostBadge: Int
    ) {
        when (logicPostBadge) {
            1 -> {
                dataPosition?.let { post ->
                    if (arrayListBind[post].idHomeTeam == listTeam[0].teamId) {
                        info(
                            """
                        [*]
                        alright set badge here
                        [${listTeam[0].teamId}] == [${arrayListBind[forIterate].idHomeTeam}]
                    """.trimIndent()
                        )

                        // try re-bind for items
                        viewHolder?.bindItem(
                            arrayListBind[post],
                            listener,
                            teamBind,
                            logicPostBadge,
                            progressBar
                        )

                    }

                }
            }
            2 -> {
                dataPosition?.let { post ->
                    if (arrayListBind[post].idAwayTeam == listTeam[0].teamId) {
                        info(
                            """
                        [*]
                        alright set badge here
                        [${listTeam[0].teamId}] == [${arrayListBind[forIterate].idAwayTeam}]
                    """.trimIndent()
                        )

                        // try re-bind for items
                        viewHolder?.bindItem(
                            arrayListBind[post],
                            listener,
                            teamBind,
                            logicPostBadge,
                            progressBar
                        )

                    }

                }
            }
        }
    }

    companion object {
        val TAG_LOG = EventTeamNextAdapter::class.java.simpleName
    }
}