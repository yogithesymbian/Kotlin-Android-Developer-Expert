package id.scode.thesportsdb

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.scode.thesportsdb.data.db.entities.Team
import id.scode.thesportsdb.ui.layout.TeamUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.find

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 03 11/3/19 3:36 AM 2019
 * id.scode.thesportsdb
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

class MainAdapter(
    private val teams: List<Team>
) : RecyclerView.Adapter<MainAdapter.TeamViewHolder>(), AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) =
        holder.bindItem(teams[position])

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val teamBadge: ImageView = view.find(R.id.team_badge)
        private val teamName: TextView = view.find(R.id.team_name)

        fun bindItem(teams: Team) {
            Picasso.get().load(teams.teamBadge).fit().into(teamBadge)
            teamName.text = teams.teamName
        }
    }

}