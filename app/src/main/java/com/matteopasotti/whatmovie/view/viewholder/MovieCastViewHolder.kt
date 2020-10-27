package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.matteopasotti.whatmovie.databinding.MovieCastItemLayoutBinding
import com.matteopasotti.whatmovie.model.ActorDomainModel

class MovieCastViewHolder(view: View, private val delegate: Delegate): BaseViewHolder(view) {

    interface Delegate {
        fun onActorClicked(actor: ActorDomainModel, view: View)
    }

    private val binding by lazy { DataBindingUtil.bind<MovieCastItemLayoutBinding>(view) }

    private lateinit var actor: ActorDomainModel

    override fun bindData(data: Any?) {

        if(data is ActorDomainModel) {
            binding.apply {
                actor = data
                binding?.actor = actor
                binding?.executePendingBindings()
            }
        }


    }

    override fun onClick(v: View?) {
        delegate.onActorClicked(actor, itemView)
    }
}