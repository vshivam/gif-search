package com.search.giphy.search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.search.giphy.search.domain.Gif
import com.search.giphy.databinding.ItemGifBinding

class GifsAdapter(private val clickListener: ThumbnailClickListener) :
    PagingDataAdapter<Gif, GifsAdapter.GifViewHolder>(GIF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding = ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let { clickListener.onClick(it) }
            }
        }

        fun bind(gif: Gif) {
            binding.thumbnail.load(gif.thumbnail)
        }
    }

    interface ThumbnailClickListener {
        fun onClick(gif: Gif)
    }

    companion object {
        private val GIF_COMPARATOR = object : DiffUtil.ItemCallback<Gif>() {
            override fun areItemsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Gif, newItem: Gif): Boolean {
                return oldItem == newItem
            }
        }
    }
}