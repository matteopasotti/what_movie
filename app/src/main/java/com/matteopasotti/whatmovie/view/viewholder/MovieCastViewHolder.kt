package com.matteopasotti.whatmovie.view.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import com.matteopasotti.whatmovie.databinding.MovieCastItemLayoutBinding
import com.matteopasotti.whatmovie.model.ActorDomainModel

class MovieCastViewHolder(view: View): BaseViewHolder(view) {

    private val binding by lazy { DataBindingUtil.bind<MovieCastItemLayoutBinding>(view) }

    override fun bindData(data: Any?) {

        if(data is ActorDomainModel) {
            binding.apply {
                binding?.actor = data
                binding?.executePendingBindings()
            }
        }


    }

    override fun onClick(v: View?) {}
}