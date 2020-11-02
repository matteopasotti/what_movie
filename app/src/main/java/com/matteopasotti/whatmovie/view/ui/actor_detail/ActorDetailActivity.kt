package com.matteopasotti.whatmovie.view.ui.actor_detail

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityActorDetailBinding
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActorDetailActivity: AppCompatActivity(), MovieViewHolder.Delegate {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityActorDetailBinding>(this, R.layout.activity_actor_detail) }

    private val viewModel: ActorDetailViewModel by viewModel()


    companion object {
        const val INTENT_ACTOR = "intent_actor"
    }

    private lateinit var actor: ActorDomainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportPostponeEnterTransition()

            actor = intent.getParcelableExtra(INTENT_ACTOR)
            viewModel.actorId = actor.id

            binding.customLayout.setMovieDelegate(this)

            val requestOptions = RequestOptions()
            requestOptions.circleCrop()

            Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(actor.profileImage)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        supportStartPostponedEnterTransition()
                        observeViewModel()
                        return false
                    }

                }).into(binding.customLayout.actorImg)

        }

    }

    private fun observeViewModel() {
        viewModel.actorDetail.observe(this, Observer {
            binding.actor = it
            binding.customLayout.updateView(it)
            binding.customLayout.startAnimation()
        })

        viewModel.isError().observe(this, Observer {

        })

        viewModel.isLoading().observe(this, Observer {

        })

        viewModel.getActorDetails()
    }

    override fun onItemClick(movie: MovieDomainModel) {
        TODO("Not yet implemented")
    }
}