package id.scode.kadeooredoo.ui.classificationmatch.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Table
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_classification_match.*
import org.jetbrains.anko.AnkoLogger

/**
 * @Authors scodeid | Yogi Arif Widodo
 * Created on 26 12/26/19 8:45 AM 2019
 * id.scode.kadeooredoo.ui.classificationmatch.adapter
 * https://github.com/yogithesymbian
 * Android Studio 3.5.3
 * Build #AI-191.8026.42.35.6010548, built on November 15, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.3.0-kali3-amd64
 */
class RvClassificationMatch(
    private val context: Context,
    private var items: List<Table>,
    private val listener: (Table) -> Unit
) : RecyclerView.Adapter<RvClassificationMatch.ViewHolder>(),
    AnkoLogger {

    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(
            item: Table,
            listener: (Table) -> Unit,
            position: Int,
            context: Context
        ) {


            val num: Int = position + 1 // start on 1
            val threeWhatColor: Int
            txt_num.text = num.toString()

            // visual set STYLE | ctrl + dot[.] | should fold
            when (num) {
                1 -> {

                    threeWhatColor = Color.GREEN

                    txt_team_name.also {
                        threeColor(it, threeWhatColor,"")
                    }
                    txt_num.also {
                        threeColor(it, threeWhatColor,"")
                    }
                    txt_team_total.also {
                        threeColor(it, threeWhatColor, "")
                    }

                }
                2 -> {

                    threeWhatColor = Color.BLUE

                    txt_team_name.also {
                        threeColor(it, threeWhatColor, "")
                    }
                    txt_num.also {
                        threeColor(it, threeWhatColor, "")
                    }
                    txt_team_total.also {
                        threeColor(it, threeWhatColor, "")
                    }

                }
                3 -> {

                    threeWhatColor = Color.RED

                    txt_team_name.also {
                        threeColor(it, threeWhatColor, "")
                    }
                    txt_num.also {
                        threeColor(it, threeWhatColor, "")
                    }
                    txt_team_total.also {
                        threeColor(it, threeWhatColor, "")
                    }

                }
                else -> {
                    threeWhatColor = Color.BLACK

                    txt_team_name.also {
                        threeColor(it, threeWhatColor, "yogi")
                    }
                    txt_num.also {
                        threeColor(it, threeWhatColor, "arif")
                    }
                    txt_team_total.also {
                        threeColor(it, threeWhatColor, "widodo")
                    }
                }
            }

            txt_team_name.text = item.name
            txt_team_goals_against.text = item.goalsagainst
            txt_team_goals_difference.text = item.goalsdifference
            txt_team_goals_for.text = item.goalsfor
            txt_team_win.text = item.win
            txt_team_loss.text = item.loss
            txt_team_draw.text = item.draw
            txt_team_total.text = item.total
            txt_team_played.text = item.played

            containerView.setOnClickListener { listener(item) }

        }

        private fun threeColor(
            it: MaterialTextView,
            color: Int,
            stateTypeFace: String
        ) {

            if (stateTypeFace == "")
                it.typeface = Typeface.DEFAULT_BOLD
            else
                it.typeface = Typeface.DEFAULT

            it.setTextColor(color)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_classification_match,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener, position, context)

}