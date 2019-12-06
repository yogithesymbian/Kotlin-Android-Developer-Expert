package id.scode.kadeooredoo.ui.detailLeague.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_previous_match_league.*

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
) : RecyclerView.Adapter<RvPrevMatchLeague.ViewHolder>(), Filterable {

    private var itemsInit: List<EventPrevious> = items

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: EventPrevious, listener: (EventPrevious) -> Unit) {

            txt_str_events.text = item.strEvent
            txt_str_seasons.text = item.strSeason

            txt_home_team.text = item.strHomeTeam
            txt_score_home.text = item.intHomeScore

            txt_away_team.text = item.strAwayTeam
            txt_score_away.text = item.intAwayScore

            txt_date_event.text = item.dateEvent
            txt_str_time_event.text = item.strTime
            txt_unlocked_event.text = item.strLocked


//            item.image.telet { Picasso.get().load(it).fit().into(img_main) }
            containerView.setOnClickListener { listener(item) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_previous_match_league,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener)

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
}