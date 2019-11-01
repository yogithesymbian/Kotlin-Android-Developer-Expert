package id.scode.kadeooredoo.submission.ui.layout

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import id.scode.kadeooredoo.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 2:15 PM 2019
 * idClubFootball.scode.kadeooredoo.submission.ui.layout
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */

class ItemListUI : AnkoComponent<ViewGroup>, AnkoLogger {
    /**
     * direct with param ViewGroup
     * this like only item_layout.xml (create-new layout )
     * without activity
     */

    companion object {
        const val txtNameClubFootball = 2
        const val imgClubFootball = 4
        const val btnFavClubFootball = 6
    }

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        cardView {
            lparams(matchParent, wrapContent){
                margin = dip(16)
            }
            linearLayout {

                lparams(matchParent, wrapContent)
                padding = dip(16)

                imageView {
                    id = imgClubFootball
                }.lparams(width = dip(50), height = dip(50))

                textView {
                    id = txtNameClubFootball
                    text = context.getString(R.string.item_list_footbal_name)
                }.lparams(width = wrapContent, height = wrapContent) {
                    margin = dip(10)
                    gravity = android.R.attr.layout_centerVertical
                }

                button {
                    id = btnFavClubFootball
                    backgroundColor = R.color.colorTransparent
                    setBackgroundResource(R.drawable.ic_favorite_border_red_a400_24dp)
                    setOnClickListener {
                        info("fav is clicked")
                        debug(9)
                        error(null)
                    }
                }.lparams(width= dip(30), height = dip(30)){
                    margin = dip(10)
                    gravity = rightMargin
                }

            }
        }
    }
}