package id.scode.kadeooredoo.ui.layout

import android.graphics.Color
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import id.scode.kadeooredoo.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.snackbar

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

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {

        cardView {
            lparams(matchParent, height = dip(110)) {
                margin = dip(16)
            }
            // for image , title, and favorite
            linearLayout {

                lparams(matchParent, wrapContent)
                padding = dip(16)

                imageView {
                    id = R.id.img_club_football
                }.lparams(width = dip(50), height = dip(50))

                val footBallName = textView {
                    id = R.id.txt_name_club_football
                    text = context.getString(R.string.item_list_footbal_name)
                }.lparams(width = wrapContent, height = wrapContent) {
                    margin = dip(10)
                    gravity = android.R.attr.layout_centerVertical
                }

                button {
                    id = R.id.btn_fav_club_football
                    backgroundColor = R.color.colorTransparent
                    textColor = Color.BLACK
                    setBackgroundResource(R.drawable.ic_favorite_border_red_a400_24dp)
                    setOnClickListener {
                        info("fav is clicked")
                        debug(9)
                        error(null)
                        alert(
                            "Add to Favorite ${footBallName.text} ?",
                            "Favorite"
                        ) {
                            yesButton { snackbar("coming soon") }
                            noButton { } //it.dismiss
                        }.show()
                    }
                }.lparams(width = dip(30), height = dip(30)) {
                    margin = dip(10)
                    gravity = rightMargin
                }

            }
            // for describeSet
            verticalLayout {

                textView {
                    id = R.id.txt_desc_club_football
                    maxLines = 2
                    ellipsize = TextUtils.TruncateAt.END
                }.lparams { margin = dip(8) }

                view {
                    backgroundColor = R.color.colorPrimaryDark
                }.lparams(width = matchParent, height = dip(2)){

                }

            }.lparams(wrapContent, wrapContent) {
                gravity = Gravity.BOTTOM
            }

        }
    }
}