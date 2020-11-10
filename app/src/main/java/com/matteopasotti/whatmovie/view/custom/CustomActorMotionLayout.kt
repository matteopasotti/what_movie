package com.matteopasotti.whatmovie.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.matteopasotti.whatmovie.R
import com.matteopasotti.whatmovie.model.ActorDetailDomainModel
import com.matteopasotti.whatmovie.model.MovieDomainModel
import com.matteopasotti.whatmovie.util.CustomLabelListView
import com.matteopasotti.whatmovie.view.adapter.KnownForMoviesAdapter
import com.matteopasotti.whatmovie.view.ui.actor_detail.ActorDetailActivity
import com.matteopasotti.whatmovie.view.viewholder.MovieViewHolder
import kotlinx.coroutines.launch

class CustomActorMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MultiListenerMotionLayout(context, attrs, defStyleAttr) {

    var actorImg: ImageView
    private var actorName: TextView
    private var actorSurname: TextView

    private var knownFor: CustomLabelListView
    private lateinit var adapter: KnownForMoviesAdapter
    private lateinit var delegate: MovieViewHolder.Delegate

    private lateinit var knownForMovies: List<MovieDomainModel>

    init {
        View.inflate(context, R.layout.custom_actor_motion_layout, this)
        actorImg = findViewById(R.id.actor_image_custom)
        actorName = findViewById(R.id.actor_name)
        actorSurname = findViewById(R.id.actor_surname)
        knownFor = findViewById(R.id.known_for)
    }

    fun setMovieDelegate(delegate: MovieViewHolder.Delegate) {
        this.delegate = delegate
        adapter = KnownForMoviesAdapter(context, delegate)
        knownFor.setCustomLabelListView("Known for", adapter)
    }

    fun updateView(actor: ActorDetailDomainModel) {
        actorName.text = actor.name
        actorSurname.text = actor.surname
        actor.knownFor?.let {
            knownForMovies = it
        }
    }

    fun startAnimation() = performAnimation {
        setTransition(R.id.start, R.id.middle)
        transitionToState(R.id.middle)
        awaitTransitionComplete(R.id.middle)

        transitionToState(R.id.middle_02)
        awaitTransitionComplete(R.id.middle_02)

        adapter.addItems(knownForMovies)
    }

    private inline fun performAnimation(crossinline block: suspend () -> Unit) {
        (context as ActorDetailActivity).lifecycleScope.launch {
            block()
        }
    }

}