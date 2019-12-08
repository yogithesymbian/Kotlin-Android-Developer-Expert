package id.scode.kadeooredoo.ui.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import id.scode.kadeooredoo.R
import id.scode.kadeooredoo.data.db.database
import id.scode.kadeooredoo.data.db.entities.Favorite
import id.scode.kadeooredoo.ui.home.TeamsFragment.Companion.DETAIL_KEY
import id.scode.kadeooredoo.ui.home.adapter.FavoriteTeamsAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

/**
 * A simple [Fragment] subclass.
 */
class FavoriteTeamsFragment : Fragment() ,AnkoComponent<Context> , AnkoLogger{

    private var favoritesMutableList: MutableList<Favorite> = mutableListOf()
    private lateinit var favoriteTeamsAdapter: FavoriteTeamsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        favoriteTeamsAdapter = FavoriteTeamsAdapter(favoritesMutableList){
            context?.startActivity<TeamsDetailActivity>(DETAIL_KEY to "${it.teamId}")
        }

        recyclerView.adapter = favoriteTeamsAdapter
        swipeRefreshLayout.onRefresh {
            info("onRefresh showFav")
            showFavorite()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(requireContext()))
    }


    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(
                    R.color.colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                recyclerView = recyclerView {
                    lparams (width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                }
            }
        }
    }

    // show data Anko SQLite
    private fun showFavorite(){

        favoritesMutableList.clear()
        context?.applicationContext?.database?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            favoritesMutableList.addAll(favorite)
            info("$ showing favorite ${favoritesMutableList[0].teamName}")
            favoriteTeamsAdapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        info("onResume")
        showFavorite()
    }

}
