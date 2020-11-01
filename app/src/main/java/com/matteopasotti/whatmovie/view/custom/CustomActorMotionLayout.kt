package com.matteopasotti.whatmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.ActorDetailDomainModel
import com.matteopasotti.whatmovie.model.ActorDomainModel
import com.matteopasotti.whatmovie.view.ui.actor_detail.ActorDetailActivity
import kotlinx.coroutines.launch

class CustomActorMotionLayout@JvmOverloads constructor(context: Context,
                                                       attrs: AttributeSet? = null,
                                                       defStyleAttr: Int = 0): MultiListenerMotionLayout(context, attrs, defStyleAttr) {

    private var actorImg: ImageView
    private lateinit var actorName: TextView
    private lateinit var actorSurname: TextView

    init {
        View.inflate(context, R.layout.custom_actor_motion_layout, this)
        actorImg = findViewById(R.id.actor_image_custom)
        actorName = findViewById(R.id.actor_name)
        actorSurname = findViewById(R.id.actor_surname)
    }

    fun initView(url: String) {
        val requestOptions = RequestOptions()
        requestOptions.circleCrop()

        Glide
            .with(actorImg.context)
            .load(url)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(actorImg)
    }

    fun updateView(actor: ActorDetailDomainModel) {
        actorName.text = actor.name
        actorSurname.text = actor.surname
    }

    fun startAnimation() = performAnimation {
        setTransition(R.id.start, R.id.middle)
        transitionToState(R.id.middle)
    }

    private inline fun performAnimation(crossinline block: suspend () -> Unit) {
        (context as ActorDetailActivity).lifecycleScope.launch {
            block()
        }
    }

}