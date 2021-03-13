package com.woken.moviesmvvm.data.repository

//rxJava component disposes anything (api call for instance)
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.woken.moviesmvvm.data.api.MovieDBInterface
import com.woken.moviesmvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
const val TAG = "MovieDetailsNetworkDataSource"

class MovieDetailsNetworkDataSource(private val apiService: MovieDBInterface, private val compositeDisposable: CompositeDisposable) {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedMovieDetails = MutableLiveData<MovieDetails>()
    val downloadedMovieDetails: LiveData<MovieDetails>
    get() = _downloadedMovieDetails

    fun fetchMovieDetails(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getMovieDetails(movieId).subscribeOn(Schedulers.io()).subscribe(
                    {
                        _downloadedMovieDetails.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },{
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e(TAG, it.message!!)
                    }
                )
            )

        }catch (e:Exception){
            Log.e(TAG, e.message!!)
        }

    }
}
