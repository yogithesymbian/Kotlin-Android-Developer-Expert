package id.scode.kadeooredoo.ui.eventteam.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.Team
import id.scode.kadeooredoo.ui.home.ui.detailteamandfavorite.TeamsDetailActivity.Companion.TEAM_KEY
import kotlinx.android.synthetic.main.activity_event_team.*
import kotlinx.android.synthetic.main.content_event_team_more.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EventTeamActivity : AppCompatActivity() , AnkoLogger{

    private var listTeam : ArrayList<Team> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_team)
        setSupportActionBar(toolbar)
        collapsing_toolbar_layout.setExpandedTitleColor(Color.TRANSPARENT)

        intent?.also {
           listTeam = it.getParcelableArrayListExtra(TEAM_KEY)
        }
        info("check getParcelableArrayListExtra : ${listTeam?.get(0)?.teamId}")
        initForTeams(listTeam?.get(0))

        img_team_logo_anchor.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action ${listTeam?.get(0)?.teamId}", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    @SuppressLint("PrivateResource")
    private fun initForTeams(get: Team?) {

        Picasso.get()
            .load(get?.strTeamFanart1)
            .fit()
            .into(img_collapsing_event_team)

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
}
