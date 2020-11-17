package com.matteopasotti.whatmovie.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.Utils
import com.matteopasotti.whatmovie.view.adapter.MovieHomeAdapterNormal
import com.matteopasotti.whatmovie.view.ui.HomeGalleryMoviesViewModel
import com.matteopasotti.whatmovie.view.ui.HomeMoviesGalleryFragment
import com.matteopasotti.whatmovie.view.ui.movie_detail.MovieDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieHomeViewHolderNormal
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.fragment_home_movies.*

class HomeMoviesFragment : Fragment(), MovieHomeViewHolderNormal.Delegate {

    private lateinit var moviesAdapter: MovieHomeAdapterNormal

    private val viewModel: HomeGalleryMoviesViewModel by viewModel()

    private var section: Int? = null

    companion object {

        private const val HOME_CATEGORY = "home_category"

        fun newInstance(homeCategory: Int): HomeMoviesGalleryFragment {
            val args = Bundle()
            args.putInt(HOME_CATEGORY, homeCategory)
            val fragment = HomeMoviesGalleryFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_home_movies, container, false)

        initView()

        return v.rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()
    }

    private fun initView() {
        moviesAdapter = MovieHomeAdapterNormal(context!!, this)

        movie_list.apply {
            setHasFixedSize(true)
            val columnWidth = context.resources.getDimension(R.dimen.image_width).toInt()

            val manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = manager
            adapter = moviesAdapter
        }

        nested_scroll_view.setOnScrollChangeListener(Utils.NestedInfiniteScrollListener {
            viewModel.getPopularMovies()
        })


        section = arguments?.getInt(HomeMoviesFragment.HOME_CATEGORY)
    }

    private fun observeViewModel() {

        viewModel.isLoading().observe(this, Observer { isLoading ->
            isLoading?.let {
                progress.visibility = View.VISIBLE
            }
        })

        viewModel.getMovies().observe(viewLifecycleOwner, Observer {
            it?.let {
                progress.visibility = View.GONE
                movie_list.visibility = View.VISIBLE
                moviesAdapter.updateItems(it)
            }
        })

        viewModel.isError().observe(viewLifecycleOwner, Observer {
            if(it) {
                //error
            }
        })

        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner , Observer {
            if(!it.isNullOrEmpty()) {
                moviesAdapter.updateItems(it)
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