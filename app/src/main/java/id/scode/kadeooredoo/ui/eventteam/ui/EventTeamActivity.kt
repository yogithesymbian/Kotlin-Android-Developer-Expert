package id.scode.kadeooredoo.ui.eventteam.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.synnapps.carouselview.ImageListener
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.EventNext
import id.scode.kadeooredoo.data.db.entities.EventPrevious
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.detailleague.ui.detailnextorprevandfavorite.DetailMatchLeagueActivity
import id.scode.kadeooredoo.ui.detailleague.ui.next.NextMatchLeagueFragment
import id.scode.kadeooredoo.ui.detailleague.ui.previous.PreviousMatchLeagueFragment
import id.scode.kadeooredoo.ui.eventteam.adapter.EventTeamNextAdapter
import id.scode.kadeooredoo.ui.eventteam.adapter.EventTeamPrevAdapter
import id.scode.kadeooredoo.ui.eventteam.presenter.EventTeamPresenter
import id.scode.kadeooredoo.ui.eventteam.view.EventTeamView
import id.scode.kadeooredoo.ui.home.ui.detailteamandfavorite.TeamsDetailActivity.Companion.TEAM_KEY
import kotlinx.android.synthetic.main.activity_event_team.*
import kotlinx.android.synthetic.main.content_event_team_more.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class EventTeamActivity : AppCompatActivity(), EventTeamView, AnkoLogger {

    private var listTeam: ArrayList<Team>? = null

    // load prev & next recycler
    private lateinit var eventTeamPresenter: EventTeamPresenter
    private lateinit var eventTeamPrevAdapter: EventTeamPrevAdapter
    private lateinit var eventTeamNextAdapter: EventTeamNextAdapter

    // init data prev & next
    private var eventPreviousMutableList: MutableList<EventPrevious> = mutableListOf()
    private var eventNextMutableList: MutableList<EventNext> = mutableListOf()

    //carouselView
    private lateinit var fantArt: Array<String>

    // @SEE OnCreate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_team)

        setSupportActionBar(toolbar)
        collapsing_toolbar_layout.setExpandedTitleColor(Color.TRANSPARENT)

        // set the recycler previous layout
        rv_event_prev_match_team.setHasFixedSize(true)
        rv_event_prev_match_team.layoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.HORIZONTAL, false)
        rv_event_prev_match_team.layoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.HORIZONTAL, false)

        // set the recycler next layout
        rv_event_next_match_team.setHasFixedSize(true)
        rv_event_next_match_team.layoutManager =
            GridLayoutManager(applicationContext, 1, GridLayoutManager.HORIZONTAL, false)
        rv_event_next_match_team.itemAnimator = DefaultItemAnimator()

        // getDataIntent & check it
        intent?.also {
            listTeam = it.getParcelableArrayListExtra(TEAM_KEY)
        }
        info("check getParcelableArrayListExtra : ${listTeam?.get(0)?.teamId}")
        initForTeams(listTeam?.get(0)) // init set data of intent obj

        img_team_logo_anchor.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Replace with your own action ${listTeam?.get(0)?.teamId}",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        eventTeamPresenter = EventTeamPresenter(this, request, gson)

        listTeam?.get(0)?.teamId.toString().also {
            if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                EspressoIdlingResource.increment()
            }
            eventTeamPresenter.getEventPrevTeamList(it)
            if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
                EspressoIdlingResource.increment()
            }
            eventTeamPresenter.getEventNextTeamList(it)

        }

        //listener previous event
        eventTeamPrevAdapter = EventTeamPrevAdapter(
            applicationContext,
            eventPreviousMutableList
        ) {
            info(
                """
                    [*]
                    date : ${it.strDate}
                """.trimIndent()
            )
            // using ui/detailleague/ui/detainextorprevandfavorite
            startActivity<DetailMatchLeagueActivity>(PreviousMatchLeagueFragment.DETAIL_PREV_MATCH_LEAGUE to it)
        }
        rv_event_prev_match_team.adapter = eventTeamPrevAdapter

        //listener next event
        eventTeamNextAdapter = EventTeamNextAdapter(
            applicationContext,
            eventNextMutableList
        ) {
            info(
                """
                    [*]
                    date : ${it.strDate}
                """.trimIndent()
            )
            // using ui/detailleague/ui/detainextorprevandfavorite
            startActivity<DetailMatchLeagueActivity>(NextMatchLeagueFragment.DETAIL_NEXT_MATCH_LEAGUE to it)
        }
        rv_event_next_match_team.adapter = eventTeamNextAdapter

    }

    @SuppressLint("PrivateResource")
    private fun initForTeams(get: Team?) {

        // carousel image setting init
        get?.also {

            val art1 = it.strTeamFanart1
            val art2 = it.strTeamFanart2
            val art3 = it.strTeamFanart3
            val art4 = it.strTeamFanart4

            if (art1 != null && art2 != null && art3 != null && art4 != null) {
                fantArt =
                    arrayOf(
                        art1, art2, art3, art4
                    )

                // set imageListener
                val imageListener = ImageListener { position, imageView ->
                    applicationContext?.let { ctx ->
                        Glide.with(ctx)
                            .asBitmap()
                            .load(fantArt[position])
                            .error(R.color.error_color_material_light)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(imageView)
                    }
                }
                // set carousel with imageListener
                carousel_fanart_team?.setImageListener(imageListener)
                // count them
                carousel_fanart_team?.pageCount = fantArt.size
            }
        }
        // end of carousel image setting

        Glide.with(this)
            .asBitmap()
            .load(get?.teamBadge)
            .error(R.color.error_color_material_light)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(img_team_logo_anchor)

        Glide.with(this)
            .asBitmap()
            .load(get?.strTeamFanart2)
            .error(R.color.error_color_material_light)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(img_event_fant_art_2)

        Glide.with(this)
            .asBitmap()
            .load(get?.strTeamLogo)
            .error(R.color.error_color_material_light)
            .format(DecodeFormat.PREFER_ARGB_8888)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .into(img_team_logo)

    }

    override fun showLoading() {
        progress_bar_event_prev_team.visible()
        progress_bar_event_next_team.visible()
    }

    override fun hideLoading() {
        progress_bar_event_prev_team.invisible()
        progress_bar_event_next_team.invisible()
    }

    override fun showEventTeamPrev(data: List<EventPrevious>?) {
        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }
        info("try show event team past list : process")

        eventPreviousMutableList.clear()
        data?.let { eventPreviousMutableList.addAll(it) }
        eventTeamPrevAdapter.notifyDataSetChanged()

        if (eventPreviousMutableList.isNullOrEmpty()) {

            toast(getString(R.string.event_team_activity_event_team_is_not_found_prev))
            img_exception_event_team_prev?.visible()
            rv_event_prev_match_team?.invisible()

        } else {

            img_exception_event_team_prev?.gone()
            rv_event_prev_match_team?.visible()
            info("hello prev ${eventPreviousMutableList[0].idHomeTeam}")

        }

        info("try show event team past list : done")

    }

    override fun showEventTeamNext(data: List<EventNext>?) {

        if (UJI_COBA_TESTING_FLAG == getString(R.string.isTest)){
            if (!EspressoIdlingResource.idlingresource.isIdleNow) {
                //Memberitahukan bahwa tugas sudah selesai dijalankan
                EspressoIdlingResource.decrement()
            }
        }
        info("try show event team next list : process")

        eventNextMutableList.clear()
        data?.let { eventNextMutableList.addAll(it) }
        eventTeamNextAdapter.notifyDataSetChanged()

        if (eventNextMutableList.isNullOrEmpty()) {


            toast(getString(R.string.event_team_activity_event_team_is_not_found_next))
            img_exception_event_team_next?.visible()
            rv_event_prev_match_team?.invisible()

        } else {

            img_exception_event_team_next?.gone()
            rv_event_prev_match_team?.visible()
            info("hello prev ${eventNextMutableList[0].idHomeTeam}")

        }

        info("try show event team next list : done")

    }
}
