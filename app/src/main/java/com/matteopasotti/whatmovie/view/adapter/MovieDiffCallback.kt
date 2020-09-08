package com.matteopasotti.whatmovie.view.adapter

import androidx.recyclerview.widget.DiffUtil
import com.matteopasotti.whatmovie.model.MovieDomainModel

class MovieDiffCallback(private val oldList: List<MovieDomainModel>, private val newList: List<MovieDomainModel>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].equals(newList[newItemPosition])
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}