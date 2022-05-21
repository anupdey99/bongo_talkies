package com.anupdey.app.bongotalkies.ui.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anupdey.app.bongotalkies.R
import com.anupdey.app.bongotalkies.data.remote.models.movie.MovieData
import com.anupdey.app.bongotalkies.databinding.ActivityHomeBinding
import com.anupdey.app.bongotalkies.ui.movie_details.MovieDetailsActivity
import com.anupdey.app.bongotalkies.util.ext.hideError
import com.anupdey.app.bongotalkies.util.ext.showError
import com.anupdey.app.bongotalkies.util.ext.toast
import com.anupdey.app.bongotalkies.util.network.ErrorType
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    private val dataAdapter = HomeAdapter()

    private var exit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        clickListeners()
        observerViewState()
    }

    private fun initViews() {
        val gridLayout = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayout
            adapter = ScaleInAnimationAdapter(dataAdapter)
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val currentItemCount = (recyclerView.layoutManager as GridLayoutManager).itemCount
                    val lastVisibleItem = (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                    viewModel.loadMore(currentItemCount, lastVisibleItem)
                }
            }
        })
    }

    private fun clickListeners() {
        dataAdapter.setOnItemClickListener { model, _ ->
            goToMovieDetails(model)
        }
    }

    private fun observerViewState() {
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
                            dataAdapter.submitList(state.list.toList())
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

    override fun onBackPressed() {
        if (exit) {
            super.onBackPressed()
        } else {
            toast(getString(R.string.exit_msg))
            exit = true
            Handler(Looper.getMainLooper()).postDelayed({
                exit = false
            }, 2000L)
        }
    }

    private fun goToMovieDetails(model: MovieData) {
        MovieDetailsActivity.launch(this, model.id, model.title ?: "").also {
            startActivity(it)
        }
    }
}