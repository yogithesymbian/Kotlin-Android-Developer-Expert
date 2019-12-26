package id.scode.kadeooredoo.ui.classificationmatch.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import id.scode.kadeooredoo.LOOKUP_TABLE
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Table
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.invisible
import id.scode.kadeooredoo.ui.classificationmatch.adapter.RvClassificationMatch
import id.scode.kadeooredoo.ui.classificationmatch.presenter.ClassificationMatchPresenter
import id.scode.kadeooredoo.ui.classificationmatch.view.ClassificationMatchView
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY
import id.scode.kadeooredoo.visible
import kotlinx.android.synthetic.main.activity_classification_match.*
import kotlinx.android.synthetic.main.content_classification_match.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class ClassificationMatchActivity : AppCompatActivity(), ClassificationMatchView, AnkoLogger {

    private val hideHandler = Handler()
    private var visibleState: Boolean = false
    private var idLeague: String? = null
    private lateinit var leagueName: String //for spinner //declare a view for choose

    private var tableMutableList: MutableList<Table> = mutableListOf()
    private lateinit var classificationMatchPresenter: ClassificationMatchPresenter
    private lateinit var rvClassificationMatchAdapter: RvClassificationMatch

    @SuppressLint("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        btn_lbl_tag.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

    }
    private val showPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private val hideRunnable = Runnable { hide() }
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val delayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_classification_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rv_classification_match.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        // Set up the user interaction to manually show or hide the system UI.
        btn_lbl_tag.setOnClickListener { toggle() }
        visibleState = true
        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        spinner_classification.setOnTouchListener(delayHideTouchListener)

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        classificationMatchPresenter = ClassificationMatchPresenter(this, request, gson)

        intent.also {
            idLeague = it.getStringExtra(DETAIL_KEY)
//            when (idLeague) {
//                getString(R.string.league_epl_id) -> leagueName = getString(R.string.league_epl)
//                getString(R.string.league_elc_id) -> leagueName = getString(R.string.league_elc)
//                getString(R.string.league_gb_id) -> leagueName = getString(R.string.league_gb)
//                getString(R.string.league_isa_id) -> leagueName = getString(R.string.league_isa)
//                getString(R.string.league_fl1_id) -> leagueName = getString(R.string.league_fl1)
//                getString(R.string.league_sll_id) -> leagueName = getString(R.string.league_sll)
//            }
//            btn_title_sub_classification_match?.text = "$idLeague - $leagueName"
//            classificationMatchPresenter.getClassificationMatchTable(idLeague.toString())
//            info("http://$LOOKUP_TABLE WITH $idLeague")
        }
        /**
         * declare & initialize adapter and presenter
         * for the callBack a getLeagueTeamList
         */
        rvClassificationMatchAdapter = applicationContext?.let { context ->
            RvClassificationMatch(
                context,
                tableMutableList
            ) {
                info(
                    """
                        [*]
                    id   : ${it.teamId}
                    name : ${it.name} 
                    """.trimIndent()
                )
                delayHideTouchListener
            }
        }!!
        rv_classification_match.adapter = rvClassificationMatchAdapter

        // spinner listener
        spinner_classification.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    (parent.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                    (parent.getChildAt(0) as TextView).textSize = 16f

                    info("spinner selected ${spinner_classification.selectedItem}")
                    leagueName = spinner_classification.selectedItem.toString()
                    when (leagueName) {
                        getString(R.string.league_epl) -> idLeague =
                            getString(R.string.league_epl_id)
                        getString(R.string.league_elc) -> idLeague =
                            getString(R.string.league_elc_id)
                        getString(R.string.league_gb) -> idLeague = getString(R.string.league_gb_id)
                        getString(R.string.league_isa) -> idLeague =
                            getString(R.string.league_isa_id)
                        getString(R.string.league_fl1) -> idLeague =
                            getString(R.string.league_fl1_id)
                        getString(R.string.league_sll) -> idLeague =
                            getString(R.string.league_sll_id)
                    }
                    btn_title_sub_classification_match?.text = leagueName
                    idLeague?.let { classificationMatchPresenter.getClassificationMatchTable(it) }
                    info("http://$LOOKUP_TABLE WITH $idLeague")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    info("onNothingSelected")
                }
            }

    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (visibleState) {
            hide()
        } else {
            show()
        }
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        visibleState = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        hideHandler.removeCallbacks(showPart2Runnable)
        hideHandler.postDelayed(hidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        // Show the system bar
        btn_lbl_tag.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        visibleState = true

        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        hideHandler.postDelayed(showPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        hideHandler.removeCallbacks(hideRunnable)
        hideHandler.postDelayed(hideRunnable, delayMillis.toLong())
    }

    override fun showLoading() {
        progress_bar_classification.visible()
    }

    override fun hideLoading() {
        progress_bar_classification.invisible()
    }

    override fun showClassificationMatchTable(data: List<Table>?) {

        info("try show classification list : process")

        tableMutableList.clear()

        data?.let {
            tableMutableList.addAll(it)
        }

        rvClassificationMatchAdapter.notifyDataSetChanged()

        info("try show classification list : done")

    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private const val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private const val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private const val UI_ANIMATION_DELAY = 300
    }
}
