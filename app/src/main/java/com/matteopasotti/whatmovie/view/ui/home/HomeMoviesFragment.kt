package com.matteopasotti.whatmovie.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.Utils
import com.matteopasotti.whatmovie.view.adapter.MovieHomeAdapter
import com.matteopasotti.whatmovie.view.adapter.MovieHomeAdapterNormal
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieHomeViewHolder
import com.matteopasotti.whatmovie.view.viewholder.MovieHomeViewHolderNormal
import kotlinx.android.synthetic.main.fragment_home_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeMoviesFragment : Fragment(), MovieHomeViewHolderNormal.Delegate {

    private lateinit var moviesAdapter: MovieHomeAdapterNormal

    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

    private var section: Int? = null

    companion object {

        private const val HOME_CATEGORY = "home_category"

        fun newInstance(homeCategory: Int): HomeMoviesFragment {
            val args = Bundle()
            args.putInt(HOME_CATEGORY, homeCategory)
            val fragment = HomeMoviesFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       return  inflater.inflate(R.layout.fragment_home_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        observeViewModel()
    }

    private fun initView() {
        moviesAdapter = MovieHomeAdapterNormal(context!!, this)

        movie_list.apply {
            val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = manager
            adapter = moviesAdapter
        }

        nested_scroll_view.setOnScrollChangeListener(Utils.NestedInfiniteScrollListener {
            viewModel.getPopularMovies()
        })
    }

    private fun observeViewModel() {

        viewModel.isLoading().observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                progress.visibility = View.VISIBLE
            }
        })

        viewModel.popularMovies.observe(viewLifecycleOwner , Observer {
            it?.let {
                progress.visibility = View.GONE
                moviesAdapter.updateItems(it)
            }
        })

        viewModel.isError().observe(viewLifecycleOwner, Observer {
            if(it) {
                //error
            }
        })

        viewModel.getPopularMovies()
    }


    override fun onItemClick(movie: MovieDomainModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(MovieDetailActivity.MOVIE, movie as Parcelable)
        startActivity(intent)
    }
}