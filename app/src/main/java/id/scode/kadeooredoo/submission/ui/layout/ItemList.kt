package id.scode.kadeooredoo.submission.ui.layout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.*
import id.scode.kadeooredoo.*

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 2:15 PM 2019
 * id.scode.kadeooredoo.submission.ui.layout
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class ItemList : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ItemListUI().setContentView(this)
    }

    class ItemListUI: AnkoComponent<ItemList>{

        override fun createView(ui: AnkoContext<ItemList>): View = with(ui) {

            linearLayout {

                lparams(matchParent, wrapContent)
                padding = dip(16)

                var imageFootball = imageView{
                    // layoutParams = LinearLayout.LayoutParams(dip(50), dip(50))
                    backgroundColor = R.color.colorAccent
                }.lparams(width= dip(50), height = dip(50))

                var textFootballName= textView {
                    // tools:text
                    text = context.getString(R.string.item_list_footbal_name)
                }.lparams(width= wrapContent, height = wrapContent){
                    margin = dip(10)
                    gravity = android.R.attr.layout_centerVertical
                }
                
            }
        }
    }
}