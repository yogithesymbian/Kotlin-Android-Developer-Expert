package id.scode.kadeooredoo.ui.home.adapter

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.EN_LANG
import id.scode.kadeooredoo.JP_LANG
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Favorite
import org.jetbrains.anko.*

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 08 12/8/19 4:27 PM 2019
 * id.scode.kadeooredoo.ui.home.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class FavoriteTeamsAdapter(
    private val favorite: List<Favorite>,
    private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position], listener)
    }

}

class TeamUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.team_badge
                }.lparams {
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.team_name
                    textSize = 16f
                }.lparams {
                    margin = dip(15)
                }
                textView {

                    id = R.id.team_desc
                    textSize = 16f
                    maxLines = 1
                    maxEms = 11
                    ellipsize = TextUtils.TruncateAt.END

                }.lparams {
                    margin = dip(15)
                }

            }
        }
    }
}

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamBadge: ImageView = view.find(R.id.team_badge)
    private val teamName: TextView = view.find(R.id.team_name)
    private val teamDesc: TextView = view.find(R.id.team_desc)
    private val language = view.context.resources.getString(R.string.app_language)

    fun bindItem(
        favorite: Favorite,
        listener: (Favorite) -> Unit
    ) {
        Picasso.get()
            .load(favorite.teamBadge)
            .fit()
            .into(teamBadge)

        teamName.text = favorite.teamName
        teamDesc.also { desc ->
            when (language) {
                EN_LANG -> desc.text = favorite.teamDescEn
                JP_LANG -> desc.text = favorite.teamDescJp
            }
        }

        itemView.setOnClickListener { listener(favorite) }
    }

}
