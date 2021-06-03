package com.search.giphy.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.search.giphy.search.domain.Gif
import com.search.giphy.search.domain.GifsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val gifsRepository: GifsRepository
) : ViewModel() {

    private val _navigationChannel = Channel<SearchNavigationEvent>()
    internal val navFlow = _navigationChannel.receiveAsFlow()

    internal var searchQuery: String? = null
        set(value) {
            field = value
            searchQueryStateFlow.value = value
        }

    private val searchQueryStateFlow: MutableStateFlow<String?> = MutableStateFlow(null)

    val viewStateLiveData: LiveData<ViewState> = searchQueryStateFlow
        .debounce(500)
        .map { mapToViewState(it) }
        .asLiveData()

    private fun mapToViewState(searchQuery: String?): ViewState =
        if (searchQuery.isNullOrEmpty()) {
            ViewState.Empty
        } else {
            ViewState.Results(
                gifsRepository.getPosts(searchQuery).cachedIn(viewModelScope)
            )
        }

    fun onThumbnailClick(gif: Gif) = viewModelScope.launch {
        _navigationChannel.send(SearchNavigationEvent.ToFullScreen(gif))
    }
}