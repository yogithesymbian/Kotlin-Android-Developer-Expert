package id.scode.kadeooredoo

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.data.db.pojo.ItemClubFootball
import id.scode.kadeooredoo.ui.layout.ItemListUI
import kotlinx.android.extensions.LayoutContainer
import org.jetbrains.anko.AnkoContext

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
class RvFootballAdapter (

    private val context: Context,
    private val itemClubFootball: List<ItemClubFootball>,
    private val listener: (ItemClubFootball) -> Unit

) : RecyclerView.Adapter<RvFootballAdapter.ViewHolder>(){

    inner class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        //LayoutContainer only for import kotlinx.android.synthetic then i need binding the anko-layout
        private var txtNameFootBall : TextView = containerView.findViewById(ItemListUI.txtNameClubFootball)
        private var txtDescFootBall : TextView = containerView.findViewById(ItemListUI.txtDescClubFormatError)

        private var imgFootBall : ImageView = containerView.findViewById(ItemListUI.imgClubFootball)

        fun bindItem(
            itemClubFootball: ItemClubFootball,
            listener: (ItemClubFootball) -> Unit
        ) {

            itemClubFootball.also{

                txtNameFootBall.text = it.nameClubFootball
                txtDescFootBall.text = it.descClubFootball
                it.imageClubFootball.let { img ->
                    Picasso.get()
                        .load(img)
                        .fit()
                        .into(imgFootBall)
                }
                containerView.setOnClickListener { _-> listener(it) }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
       ViewHolder(ItemListUI().createView(AnkoContext.Companion.create(context, parent))) // direct for create layout from separate file as layout | anko layout

    override fun getItemCount(): Int = itemClubFootball.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(itemClubFootball[position], listener) //send data[withPosition] and Unit to anonymous bindItem func in Holder

}