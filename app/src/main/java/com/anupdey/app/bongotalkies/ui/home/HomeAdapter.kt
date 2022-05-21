package com.anupdey.app.bongotalkies.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anupdey.app.bongotalkies.R
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieData
import com.anupdey.app.bongotalkies.databinding.ItemViewMovieBinding
import com.anupdey.app.bongotalkies.util.AppConstant
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import timber.log.Timber

class HomeAdapter : ListAdapter<MovieData, HomeAdapter.ViewHolder>(AsyncDifferConfig.Builder(DiffCallback()).build()) {

    private val radius = 16f
    private val options = RequestOptions()
        .placeholder(R.drawable.ic_bongo_placeholder)
        .error(R.drawable.ic_bongo_placeholder)
        .transform(CenterCrop(), GranularRoundedCorners(radius, radius, radius, radius))

    private var onItemClickListener: ((model: MovieData, position: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: ((model: MovieData, position: Int) -> Unit)? = null) {
        this.onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val binding = ItemViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        val model = currentList[position]
        val binding = holder.binding

        val url = AppConstant.BASE_URL_IMAGE + model.posterPath

        val thumbnailRequest = Glide.with(binding.thumbnail)
            .asDrawable()
            .sizeMultiplier(0.4f)
        Glide.with(binding.thumbnail)
            .load(url)
            .thumbnail(thumbnailRequest)
            .apply(options)
            .into(binding.thumbnail)
    }

    inner class ViewHolder(val binding: ItemViewMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (absoluteAdapterPosition != RecyclerView.NO_POSITION) {
                    val position = absoluteAdapterPosition
                    val model = currentList[position]
                    onItemClickListener?.invoke(model, position)
                }
            }
        }
    }

    internal class DiffCallback : DiffUtil.ItemCallback<MovieData>() {

        override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            Timber.d("areItemsTheSame oldItem ${oldItem.id} newItem ${newItem.id}")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
            Timber.d("areContentsTheSame oldItem ${oldItem.id} newItem ${newItem.id}")
            return oldItem.id == newItem.id
        }
    }
}