package id.scode.kadeooredoo.ui.detailleague.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_previous_match_league.*
import kotlinx.android.synthetic.main.item_previous_match_league.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.text.SimpleDateFormat

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 05 12/5/19 3:54 PM 2019
 * id.scode.kadeooredoo.ui.detailLeague.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class RvPrevMatchLeague(
    private val context: Context,
    private var items: List<EventPrevious>,
    private val listener: (EventPrevious) -> Unit
) : RecyclerView.Adapter<RvPrevMatchLeague.ViewHolder>(), Filterable,
    TeamsView, AnkoLogger {

    private var itemsInit: List<EventPrevious> = items
    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
    private var teams: MutableList<Team> = mutableListOf()
    private var teamsAway: Team? = null
    private lateinit var teamsPresenter: TeamsPresenter
    private var progressBar: ProgressBar? = null
    private var progressBarAway: ProgressBar? = null
    private var imageView: ImageView? = null

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        @SuppressLint("SimpleDateFormat")
        fun bindItem(
            item: EventPrevious,
            listener: (EventPrevious) -> Unit,
            teamsPresenter: TeamsPresenter,
            teamsAway: Team?
        ) {

            // twice checking measure data is soccer
            if (item.strSport == SPORT) {
                txt_str_events.text = item.strEvent
                txt_str_seasons.text = item.strSeason

                txt_home_team.text = item.strHomeTeam
                txt_score_home.text = item.intHomeScore

                txt_away_team.text = item.strAwayTeam
                txt_score_away.text = item.intAwayScore


                item.dateEvent?.let { date ->
                    txt_date_event.text = date

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
                            txt_str_time_event.text = getTime
                        }

                    }

                }

                txt_unlocked_event.text = item.strSport

                teamsPresenter.getDetailLeagueTeamAwayList(item.idAwayTeam.toString())

                Log.d(TAG_LOG,"set badge outer ${teamsAway?.teamBadge}")

                if (teamsAway != null){

                    Log.d(TAG_LOG,"set badge deeper ${teamsAway.teamBadge}")

                    img_away_team_jersey.let { img ->
                        Glide.with(this.itemView.context)
                            .load(teamsAway.teamBadge)
                            .into(img)
                    }

                }

//                Handler().postDelayed({
//
//                    Log.d(TAG_LOG,"set badge inner ${teamsAway?.teamBadge}")
//                    if (teamsAway != null){
//
//                        Log.d(TAG_LOG,"set badge deeper ${teamsAway.teamBadge}")
//
//                        img_away_team_jersey.let { img ->
//                            Glide.with(this.itemView.context)
//                                .load(teamsAway.teamBadge)
//                                .into(img)
//                        }
//                    }
//
//
//                }, 5000)

            } else {
                Log.d(
                    TAG_LOG, """
                    the data is'nt soccer
                    name team ${item.strFilename}
                    home ${item.strHomeTeam}
                """.trimIndent()
                )
            }

            containerView.setOnClickListener { listener(item) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val onViewHolder =
            ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_previous_match_league,
                    parent,
                    false
                )
            )
        progressBar = parent.progress_load_jersey_home
        progressBarAway = parent.progress_load_jersey_away
        imageView = parent.img_away_team_jersey

        val request = ApiRepository()
        val gson = Gson()
        teamsPresenter = TeamsPresenter(this, request, gson)

        return onViewHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener, teamsPresenter, teamsAway)

    override fun getFilter(): Filter {
        return object : Filter() {

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                items = results.values as List<EventPrevious>
                notifyDataSetChanged()
            }

            @SuppressLint("DefaultLocale")
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                items = if (charString.isEmpty()) {
                    itemsInit //save before result data shown and return this for isEmpty
                } else {
                    val filterListener: List<EventPrevious> = items
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

    override fun showTeamList(data: List<Team>?) {
        info("try show jersey team LOOKUP : process")
        teams.clear()
        data?.let {
            teams.addAll(it)
        }
        info("try show jersey team LOOKUP : done")
    }

    override fun showTeamAwayList(data: List<Team>?, checkIdTeam: String) {
        info("try show jersey team away LOOKUP : process")
        listOf(teamsAway).toMutableList().clear()
        data?.let {
            listOf(teamsAway).toMutableList().addAll(it)

            teamsAway = Team(
                teamId = it[0].teamId,
                teamBadge = it[0].teamBadge,
                teamName = it[0].teamName
            )

            info("""
                
                [ ${it[0].teamName} ] match [ ${teamsAway?.teamName} ]
                [ $checkIdTeam ] match [ ${teamsAway?.teamId} ]
                
            """.trimIndent())

        }
        info("try show jersey team away LOOKUP : done")
    }

    override fun exceptionNullObject(msg: String) {
        // just for in home | search
    }

    companion object {
        val TAG_LOG = RvPrevMatchLeague::class.java.simpleName
    }
}