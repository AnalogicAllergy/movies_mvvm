package com.woken.moviesmvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.woken.moviesmvvm.data.api.MovieDBInterface
import com.woken.moviesmvvm.data.repository.MovieDetailsNetworkDataSource
import com.woken.moviesmvvm.data.repository.NetworkState
import com.woken.moviesmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService:MovieDBInterface) {
    lateinit var movieDetailsNetworkDataSource:MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId:Int):LiveData<MovieDetails>{
         movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)
        return movieDetailsNetworkDataSource.downloadedMovieDetails
    }
    fun getMovieDetailsNetworkState():LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }

}