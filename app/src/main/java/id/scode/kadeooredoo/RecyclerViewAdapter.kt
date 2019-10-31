package id.scode.kadeooredoo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*

/**
 * @Authors scode
 * Created on 30 10/30/19 9:27 PM 2019
 * id.scode.kadeooredoo
 * East Borneo
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class RecyclerViewAdapter (
    private val context: Context,
    private val items: List<Item>,
    private val listener: (Item) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    class ViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindItem(item: Item, listener: (Item) -> Unit) {

            txt_name.text = item.name
            item.image.let { Picasso.get().load(it).fit().into(img_main) }
            containerView.setOnClickListener { listener(item) }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindItem(items[position], listener)

}