package id.scode.kadeooredoo.ui.detailLeague.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_next_match_league.*
import kotlinx.android.synthetic.main.item_previous_match_league.*

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
    private val items: List<EventNext>,
    private val listener: (EventNext) -> Unit
) : RecyclerView.Adapter<RvNextMatchLeague.ViewHolder>() {

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: EventNext, listener: (EventNext) -> Unit) {

            txt_str_events_next.text = item.strEvent
            txt_str_seasons_next.text = item.strSeason

            txt_home_team_next.text = item.strHomeTeam
            val scoreHome = item.intHomeScore
            txt_score_home_next.also {
                when (scoreHome) {
                    null -> it.text = "?"
                    else -> it.text = scoreHome
                }
            }

            txt_away_team_next.text = item.strAwayTeam
            val scoreAway = item.intAwayScore
            txt_score_away_next.also {
                when (scoreAway) {
                    null -> it.text = "?"
                    else -> it.text = scoreAway
                }
            }

            txt_date_event_next.text = item.dateEvent
            txt_str_time_next.text = item.strTime
            txt_unlocked_event_next.text = item.strLocked
//            item.image.let { Picasso.get().load(it).fit().into(img_main) }
            containerView.setOnClickListener { listener(item) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_next_match_league,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener)

}