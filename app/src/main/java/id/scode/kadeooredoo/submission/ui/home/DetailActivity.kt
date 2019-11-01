package id.scode.kadeooredoo.submission.ui.home

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.submission.data.db.pojo.ItemClubFootball
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
    private var itemClubFootball: ItemClubFootball? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verticalLayout {
            padding = dip(16)

            cardView {
                padding = dip(8)
                txtNameClubFootball = textView {
                    textSize = 18f

                }
            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }

            imgClubFootball = imageView().lparams(width = dip(75), height = dip(75))

            cardView {
                padding = dip(8)
                txtDetailClubFootBall = textView {
                    textSize = 16f
                    maxLines = 5
                }
            }.lparams(matchParent, wrapContent) {
                margin = dip(8)
            }
        }

        // get Data from intent(#MainActivity)
        intent.also {
            itemClubFootball = it.getParcelableExtra("detail")
        }
        // set Data for layout
        itemClubFootball.also {
            txtNameClubFootball.text = it?.nameClubFootball
            txtDetailClubFootBall.text = it?.descClubFootball
            it?.imageClubFootball?.also { img ->
                Picasso.get()
                    .load(img)
                    .fit()
                    .into(imgClubFootball)
            }
        }
    }
}