package id.scode.kadeooredoo.ui.home.tmp

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.ui.home.tmp.TeamsActivity.Companion.DETAIL_KEY
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

/**
 * @Authors scode | Yogi Arif Widodo
 * Created on 31 10/31/19 8:19 PM 2019
 * idClubFootball.scode.kadeooredoo.submission.ui.home
 * https://github.com/yogithesymbian
 * Android Studio 3.5.1
 * Build #AI-191.8026.42.35.5900203, built on September 26, 2019
 * JRE: 1.8.0_202-release-1483-b49-5587405 amd64
 * JVM: OpenJDK 64-Bit Server VM by JetBrains s.r.o
 * Linux 5.2.0-kali3-amd64
 */
class DetailActivity : AppCompatActivity() {

    /**
     * declare for init the layout component and
     * POJO class for casting | as the intent with object
     */
    private lateinit var txtDetailClubFootBall: TextView
    private lateinit var txtNameClubFootball: TextView
    private lateinit var imgClubFootball: ImageView

    private var teams: Team? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            padding = dip(16)

            cardView {
                padding = dip(8)
                txtNameClubFootball = textView {
                    padding = dip(16)
                    textSize = 18f
                }.lparams(wrapContent, wrapContent){
                    margin = dip(16)
                    gravity = Gravity.CENTER
                }
            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }

            imgClubFootball = imageView()
                .lparams(width = dip(125), height = dip(125)){
                margin = dip(8)
            }

            cardView {
                padding = dip(8)
                txtDetailClubFootBall = textView {
                    padding = dip(16)
                    textSize = 16f
                }
            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }
        }

        // get Data from intent(#TeamsActivity)
        intent.also {
            teams = it.getParcelableExtra(DETAIL_KEY)
        }
        // set Data for layout
        teams.also {
            txtNameClubFootball.text = it?.teamName
            txtDetailClubFootBall.text = it?.teamName
            it?.teamBadge?.also { img ->
                Picasso.get()
                    .load(img)
                    .fit()
                    .into(imgClubFootball)
            }
        }
    }
}