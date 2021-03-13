package com.woken.moviesmvvm.ui.single_movie_details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.woken.moviesmvvm.R
import com.woken.moviesmvvm.data.api.MovieDBClient
import com.woken.moviesmvvm.data.api.MovieDBInterface
import com.woken.moviesmvvm.data.api.POSTER_BASE_URL
import com.woken.moviesmvvm.data.repository.NetworkState
import com.woken.moviesmvvm.data.vo.MovieDetails
import kotlinx.android.synthetic.main.activity_single_movie.*
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieRepository: MovieDetailsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)
        val movieId = intent.getIntExtra("id", 1)

        val apiService :MovieDBInterface = MovieDBClient.getClient()
        movieRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })
        viewModel.networkState.observe(this, Observer {
            pb_progress.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            tv_error_loading.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE

        })
    }

    private fun bindUI(it: MovieDetails) {
        tv_movie_title.text = it.title
        tv_movie_tagline.text = it.tagline
        tv_movie_release_date.text = it.releaseDate
        tv_movie_rating.text = it.rating.toString()
        tv_movie_runtime.text = it.runtime.toString()+" minutes"
        tv_movie_overview.text = it.overview

        val formatCurrency = NumberFormat.getCurrencyInstance(Locale.US)
        tv_movie_budget.text = formatCurrency.format(it.budget)
        tv_movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePoster = POSTER_BASE_URL+it.posterPath
        Glide.with(this).load(moviePoster).into(iv_movie_poster)

    }

    private fun getViewModel(movieId:Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return SingleMovieViewModel(movieRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}