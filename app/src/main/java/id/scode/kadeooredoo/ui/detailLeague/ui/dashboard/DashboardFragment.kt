package id.scode.kadeooredoo.ui.detailLeague.ui.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import id.scode.kadeooredoo.*
import id.scode.kadeooredoo.data.db.entities.League
import id.scode.kadeooredoo.data.db.network.ApiRepository
import id.scode.kadeooredoo.ui.detailLeague.presenter.DetailLeaguePresenter
import id.scode.kadeooredoo.ui.detailLeague.view.DetailLeagueView
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DashboardFragment : Fragment(), DetailLeagueView, AnkoLogger {


    private lateinit var dashboardViewModel: DashboardViewModel
    private var idLeague: String? = null
    /**
     * apply the TeamsPresenter and MainAdapter
     * to the this context
     */
    private var leaguesMutableList: MutableList<League> = mutableListOf()
    private lateinit var detailLeaguePresenter: DetailLeaguePresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var carouselView: CarouselView
    private lateinit var fantArt: Array<String>

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        // initialize binding
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        progressBar = root.findViewById(R.id.progress_detail_dashboard)
        carouselView = root.findViewById(R.id.carousel_fanart)

        // get pass data args
        val idLeagueKey = resources.getString(R.string.key_id_league)
        idLeague = arguments?.getString(idLeagueKey)

        // test obs
        dashboardViewModel.text.observe(this, Observer {
            textView.text = "dashboard $idLeague"
        })

        // init the presenter for injecting the constructor
        val request = ApiRepository()
        val gson = Gson()
        detailLeaguePresenter = DetailLeaguePresenter(this, request, gson)

        // call the data api
        idLeague?.let {
            detailLeaguePresenter.getDetailLeagueList(it)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        float_social_media?.setOnClickListener{

            if (!float_social_media_yt.isShown){
                float_social_media_yt.visible()
                float_social_media_tw.visible()
                float_social_media_fb.visible()
                float_social_media_web.visible()
                float_social_media_rss.visible()

                activity?.applicationContext.let {
                    float_social_media_yt.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_in
                        )
                    )
                    float_social_media_tw.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_in
                        )
                    )
                    float_social_media_fb.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_in
                        )
                    )
                    float_social_media_web.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_in
                        )
                    )
                    float_social_media_rss.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_in
                        )
                    )
                }

            } else {
                float_social_media_yt.gone()
                float_social_media_tw.gone()
                float_social_media_fb.gone()
                float_social_media_web.gone()
                float_social_media_rss.gone()
                activity?.applicationContext.let {
                    float_social_media_yt.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_out
                        )
                    )
                    float_social_media_tw.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_out
                        )
                    )
                    float_social_media_fb.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_out
                        )
                    )
                    float_social_media_web.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_out
                        )
                    )
                    float_social_media_rss.startAnimation(
                        AnimationUtils.loadAnimation(
                            it,
                            R.anim.slide_out
                        )
                    )
                }
            }

        }


    }
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    @SuppressLint("SetTextI18n")
    override fun showDetailLeague(data: List<League>?) {
        info("try show detail leaguesMutableList : process")
        leaguesMutableList.clear()
        data?.let {
            leaguesMutableList.addAll(it)
        }


        // carousel image setting init
        val i = 0
        fantArt =
            arrayOf(
                leaguesMutableList[i].strFanart1,
                leaguesMutableList[i].strFanart2,
                leaguesMutableList[i].strFanart3,
                leaguesMutableList[i].strFanart4
            )

        // set imageListener
       val imageListener = ImageListener { position, imageView ->
           activity?.applicationContext?.let {
               Glide.with(it)
                   .load(fantArt[position])
                   .error(R.color.design_default_color_error)
                   .into(imageView)
           }
        }
        // set carousel with imageListener
        carouselView.setImageListener(imageListener)
        // count them
        carouselView.pageCount = fantArt.size
        // end of carousel image setting

        // set detail of league data
        activity?.applicationContext?.let {

            Glide.with(it)
                .load(leaguesMutableList[i].strPoster)
                .into(img_poster_league)

            Glide.with(it)
                .load(leaguesMutableList[i].strTrophy)
                .into(img_trophy)

            txt_str_league.text = leaguesMutableList[i].strLeague
            txt_str_soccer.text = leaguesMutableList[i].strSport
            txt_str_gender.text = leaguesMutableList[i].strGender
            txt_str_first_event.text = leaguesMutableList[i].dateFirstEvent
            txt_str_country.text = "Country : ${leaguesMutableList[i].strCountry}"

            val language = it.resources.getString(R.string.app_language)
            txt_desc_league.also { desc ->
                when(language){
                    EN_LANG -> desc.text = leaguesMutableList[i].strDescriptionEN
                    JP_LANG -> desc.text = leaguesMutableList[i].strDescriptionJP
                }
            }

            float_social_media_yt.setOnClickListener{
                intentSocial(leaguesMutableList[i].strYoutube)
            }
            float_social_media_tw.setOnClickListener{
                intentSocial(leaguesMutableList[i].strTwitter)
            }
            float_social_media_fb.setOnClickListener{
                intentSocial(leaguesMutableList[i].strFacebook)
            }
            float_social_media_web.setOnClickListener{
                intentSocial(leaguesMutableList[i].strWebsite)
            }
            float_social_media_rss.setOnClickListener{
                intentSocial(leaguesMutableList[i].strRSS)
            }

        }

        info("try show leaguesMutableList : done ")
    }

    private fun intentSocial(urlMedia: String) {
        info("url link clicked $urlMedia")
        val intent : Intent = if (urlMedia.contains("http://")){
            Intent(Intent.ACTION_VIEW, Uri.parse(urlMedia))
        }else {
            Intent(Intent.ACTION_VIEW, Uri.parse("http://$urlMedia"))
        }
        startActivity(intent)
    }

    companion object
}