package id.scode.kadeooredoo.ui.home


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
import id.scode.kadeooredoo.data.db.entities.Favorite
import id.scode.kadeooredoo.databaseTeams
import id.scode.kadeooredoo.gone
import id.scode.kadeooredoo.ui.home.adapter.FavoriteTeamsAdapter
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
class TeamsFavoriteFragment : Fragment(), AnkoLogger {

    private var favoritesMutableList: MutableList<Favorite> = mutableListOf()
    private lateinit var favoriteTeamsAdapter: FavoriteTeamsAdapter
    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rv_teams)
        imageView = view.findViewById(R.id.img_exception_favorite)
        swipeRefreshLayout = view.findViewById(R.id.swrl_teams)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // set the layout
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            GridLayoutManager(activity?.applicationContext, 1, GridLayoutManager.HORIZONTAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()

        favoriteTeamsAdapter = FavoriteTeamsAdapter(favoritesMutableList) {
            context?.startActivity<TeamsDetailActivity>(TeamsFragment.DETAIL_KEY_FAV_TEAM to "${it.teamId}")
        }

        recyclerView.adapter = favoriteTeamsAdapter

        swipeRefreshLayout.onRefresh {
            info("onRefresh showFav")
            showFavorite()
        }

    }


    // show data Anko SQLite
    private fun showFavorite() {
        // clear before assign
        favoritesMutableList.clear()

        context?.applicationContext?.databaseTeams?.use {
            // stop refresh if do
            swipeRefreshLayout.isRefreshing = false

            // query anko SQLite select and parser the result
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())

            // assign all data to favorite mutableList
            favoritesMutableList.addAll(favorite)

            // check data
            if (favoritesMutableList.isNullOrEmpty()) {
                swipeRefreshLayout.gone()
                imageView.visible()
            } else {
                info("$ showing favorite ${favoritesMutableList[0].teamName}")
                swipeRefreshLayout.visible()
                imageView.gone()
            }

            // getNot-if the adapter if change
            favoriteTeamsAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        info("onResume")
        showFavorite()

    }

}
