package id.scode.kadeooredoo.ui.detailleague.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.SPORT
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.gone
import id.scode.kadeooredoo.ui.home.presenter.TeamsPresenter
import id.scode.kadeooredoo.ui.home.view.TeamsView
import id.scode.kadeooredoo.visible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_next_match_league.*
import kotlinx.android.synthetic.main.item_next_match_league.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 05 12/5/19 7:02 PM 2019
 * id.scode.kadeooredoo.ui.detailLeague.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class RvNextMatchLeague(
    private val context: Context,
    private var items: List<EventNext>,
    private val listener: (EventNext) -> Unit
) : RecyclerView.Adapter<RvNextMatchLeague.ViewHolder>() ,
    TeamsView, AnkoLogger, Filterable{

    private var itemsInit: List<EventNext> = items
    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
    private var teams: MutableList<Team> = mutableListOf()
    private var teamsAway: MutableList<Team> = mutableListOf()
    private lateinit var teamsPresenter: TeamsPresenter
    private var progressBar: ProgressBar? = null
    private var progressBarAway: ProgressBar? = null

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: EventNext, listener: (EventNext) -> Unit) {

            if(item.strSport == SPORT){
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

                txt_date_event_next.text = item.dateEvent
                txt_str_time_next.text = item.strTime
                txt_unlocked_event_next.text = item.strLocked
            } else {
                Log.d(
                    TAG_LOG, """
                    the data is'nt soccer
                    name team ${item.strFilename}
                    home ${item.strHomeTeam}
                """.trimIndent())
            }
            containerView.setOnClickListener { listener(item) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val rootHolder = ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_next_match_league,
                parent,
                false
            )
        )

        progressBar = parent.progress_load_jersey_home_next
        progressBarAway = parent.progress_load_jersey_away_next

        val request = ApiRepository()
        val gson = Gson()
        teamsPresenter = TeamsPresenter(this, request, gson)

        return rootHolder
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listener)

        items[position].idHomeTeam?.let {

            info("idHomeTeam : $it")
            teamsPresenter.getDetailLeagueTeamList(it)

            if (!teams.isNullOrEmpty()) {

                info("ohJersey : ${teams[0].strTeamLogo}")
                Glide.with(holder.itemView)
                    .load(teams[0].teamBadge)
                    .into(holder.img_home_team_jersey_next)
            } else {
                info("ohJersey null, still loading")
            }
        }
        items[position].idAwayTeam?.let {

            info("idAwayTeam : $it")
            teamsPresenter.getDetailLeagueTeamAwayList(it)

            if (!teamsAway.isNullOrEmpty()) {

                info("ohJerseyAway : ${teamsAway[0].strTeamLogo}")
                Glide.with(holder.itemView)
                    .load(teamsAway[0].teamBadge)
                    .into(holder.img_away_team_jersey_next)
            } else {
                info("ohJerseyAway null, still loading")
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

    override fun showTeamList(data: List<Team>?) {
        info("try show jersey team LOOKUP : process")
        teams.clear()
        data?.let {
            teams.addAll(it)
        }
        info("try show jersey team LOOKUP : done")
    }

    override fun showTeamAwayList(data: List<Team>?) {
        info("try show jersey team away LOOKUP : process")
        teamsAway.clear()
        data?.let {
            teamsAway.addAll(it)
        }
        info("try show jersey team away LOOKUP : done")
    }

    override fun exceptionNullObject(msg: String) {
        // just for in home | search
    }

    companion object{
        val TAG_LOG = RvNextMatchLeague::class.java.simpleName
    }
}