package id.scode.kadeooredoo.ui.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.EN_LANG
import id.scode.kadeooredoo.JP_LANG
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.ui.layout.ItemListUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 2:52 PM 2019
 * idClubFootball.scode.kadeooredoo.submission.ui
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class TeamsAdapter(

    private val context: Context,
    private val teams: List<Team>,
    private val listener: (Team) -> Unit

) : RecyclerView.Adapter<TeamsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //LayoutContainer only for import kotlinx.android.synthetic then i need binding the anko-layout
        private var txtNameFootBall: TextView = view.find(R.id.txt_name_club_football)
        private var txtDescFootBall: TextView = view.find(R.id.txt_desc_club_football)
        private var imgFootBall: ImageView = view.find(R.id.img_club_football)

        private val language = context.resources.getString(R.string.app_language)

        fun bindItem(
            team: Team,
            listener: (Team) -> Unit
        ) {


            team.also {

                txtNameFootBall.text = it.teamName

                txtDescFootBall.also { desc ->
                    when (language) {
                        EN_LANG -> desc.text = it.strDescriptionEN
                        JP_LANG -> desc.text = it.strDescriptionJP
                    }
                }

                it.teamBadge.let { img ->
                    Picasso.get()
                        .load(img)
                        .fit()
                        .into(imgFootBall)
                }
                itemView.setOnClickListener { _ -> listener(it) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemListUI().createView(
                AnkoContext.Companion.create(
                    context,
                    parent
                )
            )
        ) // direct for create layout from separate file as layout | anko layout

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(
            teams[position],
            listener
        ) //send data[withPosition] and Unit to anonymous bindItem func in Holder

}