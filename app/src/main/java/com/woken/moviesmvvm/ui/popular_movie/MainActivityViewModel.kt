package com.woken.moviesmvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.woken.moviesmvvm.data.repository.NetworkState
import com.woken.moviesmvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val moviePagedListRepository: MoviePagedListRepository) :ViewModel(){
    private val compositeDisposable = CompositeDisposable()

    val moviePageList: LiveData<PagedList<Movie>> by lazy {
        moviePagedListRepository.fetchLiveMoviePagedList(compositeDisposable)
    }
    val networkState: LiveData<NetworkState> by lazy {
        moviePagedListRepository.getNetworkState()
    }
    fun listIsEmpty(): Boolean{
        return moviePageList.value?.isEmpty()?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}