package id.scode.kadeooredoo.ui.home.subfavorite.eventnext


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.entities.FavTeamJoinDetail
import id.scode.kadeooredoo.data.db.entities.Team.Companion.TABLE_FAVORITE_NEXT
import id.scode.kadeooredoo.databaseEventNextMatch
import id.scode.kadeooredoo.gone
import id.scode.kadeooredoo.ui.detailleague.ui.detailnextorprevandfavorite.DetailMatchLeagueActivity
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY_SCORE
import id.scode.kadeooredoo.ui.home.adapter.FavoriteEventAdapter
import id.scode.kadeooredoo.visible
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.onRefresh

/**
 * A simple [Fragment] subclass.
 */
class FavNextFragment : Fragment(), AnkoLogger {

    private var favoritesMutableList: MutableList<FavTeamJoinDetail> = mutableListOf()
    private lateinit var favoriteEventAdapter: FavoriteEventAdapter
    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_event_next_match)
        imageView = view.findViewById(R.id.img_exception_favorite_event_next)
        swipeRefreshLayout = view.findViewById(R.id.swrl_event_next_match)

        // set the layout
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteEventAdapter = FavoriteEventAdapter(favoritesMutableList) {
            info("move with ${it.eventId} - ${it.teamBadge}")
            context?.startActivity<DetailMatchLeagueActivity>(
                DETAIL_KEY to "${it.eventId}",
                DETAIL_KEY_SCORE to "${it.homeScore}"
            )
        }

        recyclerView.adapter = favoriteEventAdapter

        swipeRefreshLayout.onRefresh {
            info("onRefresh showFav")
            showFavorite()
        }
    }

    // show data Anko SQLite
    private fun showFavorite() {
        // clear before assign
        favoritesMutableList.clear()

        requireContext().databaseEventNextMatch.use {
            // stop refresh if do
            swipeRefreshLayout.isRefreshing = false

            // query anko SQLite select and parser the result
            val result = select(TABLE_FAVORITE_NEXT)
            val favorite = result.parseList(classParser<FavTeamJoinDetail>())

            // assign all data to favorite mutableList
            favoritesMutableList.addAll(favorite)

            // check data
            if (favoritesMutableList.isNullOrEmpty()) {
                swipeRefreshLayout.gone()
                imageView.visible()
            } else {
                info("$ showing favorite ${favoritesMutableList[0].homeTeam}")
                swipeRefreshLayout.visible()
                imageView.gone()
            }

            // getNot-if the adapter if change
            favoriteEventAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        info("onResume")
        showFavorite()

    }


}
