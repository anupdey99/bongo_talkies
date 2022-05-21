package com.anupdey.app.bongotalkies.ui.movie_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.anupdey.app.bongotalkies.data.remote.models.movie_details.MovieDetailsResponse
import com.anupdey.app.bongotalkies.databinding.ActivityMovieDetailsBinding
import com.anupdey.app.bongotalkies.util.AppConstant
import com.anupdey.app.bongotalkies.util.ext.*
import com.anupdey.app.bongotalkies.util.network.ErrorType
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsActivity: AppCompatActivity() {

    companion object {
        fun launch(context: Context, id: Int, title: String): Intent {
            return Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra("id", id)
                putExtra("title", title)
            }
        }
    }

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = intent?.getStringExtra("title") ?: ""
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val id = intent?.getIntExtra("id", 0) ?: 0

        if (id == 0) {
            toast("Invalid movie")
            binding.emptyView.isVisible = true
        } else {
            viewModel.fetchMovieDetails(id)
        }

        observeViewState()
    }

    private fun observeViewState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateEvent.collect { state ->
                    when (state) {
                        is ViewState.ProgressState -> {
                            binding.progressBar.isVisible = state.isShow
                        }
                        is ViewState.EmptyState -> {
                            binding.emptyView.isVisible = state.isShow
                        }
                        is ViewState.InitData -> {
                            bindViewData(state.model)
                            hideError(binding.errorView)
                        }
                        is ViewState.ShowError -> {
                            toast(state.error.message)
                            showError(binding.errorView, state.error) {
                                viewModel.retry()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun bindViewData(model: MovieDetailsResponse) {
        binding.apply {
            parent.isVisible = true
            Glide.with(poster)
                .load(AppConstant.BASE_URL_IMAGE + model.posterPath)
                .into(poster)
            title.text = model.title
            tag.text = model.tagline
            description.text = model.overview
            genres.text = model.genres?.map { it.name }?.joinToString(" ● ") ?: "-"
            if (model.belongsToCollection != null) {
                collectionLayout.isVisible = true
                collection.text = model.belongsToCollection!!.name
            }
            releaseData.text = formatDate(model.releaseDate)
            languageData.text = model.spokenLanguages?.map { it.englishName }?.joinToString(" ● ") ?: "-"
            lengthData.text = formatToHHMM(model.runtime)
            production.text = model.productionCompanies?.map { it.name }?.joinToString(" ● ") ?: "-"
        }
    }

}