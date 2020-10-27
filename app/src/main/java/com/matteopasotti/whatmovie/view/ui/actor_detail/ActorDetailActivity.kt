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
import com.bumptech.glide.request.target.Target
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.databinding.ActivityActorDetailBinding
import com.matteopasotti.whatmovie.model.ActorDomainModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActorDetailActivity: AppCompatActivity() {

    private val binding by lazy { DataBindingUtil.setContentView<ActivityActorDetailBinding>(this, R.layout.activity_actor_detail) }

    private val viewModel: ActorDetailViewModel by viewModel()


    companion object {

        const val TAG = "ActorDetailActivity"

        const val INTENT_ACTOR = "intent_actor"

        const val IMAGE_TYPE = "."
    }

    private lateinit var actor: ActorDomainModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            supportPostponeEnterTransition()

            actor = intent.getParcelableExtra(INTENT_ACTOR)
            viewModel.actorId = actor.id

            val requestOptions = RequestOptions()
            requestOptions.circleCrop()

            Glide.with(this)
                .asBitmap()
                .apply(requestOptions)
                .load(actor.profileImage)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        //observeViewModel()
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()

                        return false
                    }
                }).into(binding.actorImg)

            observeViewModel()
        }

    }

    private fun observeViewModel() {
        viewModel.actorDetail.observe(this, Observer {
            binding.actor = it
        })

        viewModel.isError().observe(this, Observer {

        })

        viewModel.isLoading().observe(this, Observer {

        })

        viewModel.getActorDetails()
    }
}