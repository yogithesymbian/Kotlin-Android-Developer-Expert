package id.scode.thesportsdb.ui.layout

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import org.jetbrains.anko.*
import id.scode.thesportsdb.*

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 03 11/3/19 3:41 AM 2019
 * id.scode.thesportsdb.ui.layout
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class TeamUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui){
        linearLayout {
            lparams(width= matchParent, height = wrapContent)
            padding = dip(16)
            orientation = LinearLayout.HORIZONTAL

            imageView{
                id = R.id.team_badge
            }.lparams{
                height = dip(50)
                width = dip(50)
            }

            textView{
                id = R.id.team_name
                textSize = 16f
            }.lparams{
                margin = dip(15)
            }
        }
    }
}