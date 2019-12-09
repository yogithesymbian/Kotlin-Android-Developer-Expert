package id.scode.kadeooredoo.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.FavTeamJoinDetail
import id.scode.kadeooredoo.gone
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_previous_match_league.*
import org.jetbrains.anko.AnkoLogger

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 09 12/9/19 5:51 PM 2019
 * id.scode.kadeooredoo.ui.home.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class FavoriteEventPrevAdapter(
    private var items: List<FavTeamJoinDetail>,
    private val listener: (FavTeamJoinDetail) -> Unit
) : RecyclerView.Adapter<FavoriteEventPrevAdapter.ViewHolder>(), AnkoLogger {


    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(
            item: FavTeamJoinDetail,
            listener: (FavTeamJoinDetail) -> Unit,
            context: Context
        ) {

            //loading logo only on !favorite
            progress_load_jersey_home.gone()
            progress_load_jersey_away.gone()

            txt_str_events.text = item.event
            txt_str_seasons.text = item.season

            txt_home_team.text = item.homeTeam
            txt_score_home.text = item.homeScore

            txt_away_team.text = item.awayTeam
            txt_score_away.text = item.awayScore

            txt_date_event.text = item.dateEvent
            txt_str_time_event.text = item.timeEvent
            txt_unlocked_event.text = item.sportStr

            Glide.with(context)
                .load(item.teamBadge)
                .into(img_home_team_jersey)

            Glide.with(context)
                .load(item.teamBadgeAway)
                .into(img_away_team_jersey)


            containerView.setOnClickListener { listener(item) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_previous_match_league,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener, holder.itemView.context)

}