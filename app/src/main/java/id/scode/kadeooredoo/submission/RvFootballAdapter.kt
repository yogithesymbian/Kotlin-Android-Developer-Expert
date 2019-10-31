package id.scode.kadeooredoo.submission

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.submission.data.db.pojo.ItemClubFootball
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 2:52 PM 2019
 * id.scode.kadeooredoo.submission.ui
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class RvFootballAdapter (

    private val context: Context,
    private val items: List<ItemClubFootball>,
    private val listener: (ItemClubFootball) -> Unit

) : RecyclerView.Adapter<RvFootballAdapter.ViewHolder>(){

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(
            itemClubFootball: ItemClubFootball,
            listener: (ItemClubFootball) -> Unit
        ) {

            txt_name.text = itemClubFootball.nameClubFootball
            itemClubFootball.imageClubFootball.let { Picasso.get().load(it).fit().into(img_main) }
            containerView.setOnClickListener { listener(itemClubFootball) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener)

}