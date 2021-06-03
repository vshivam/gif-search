package com.search.giphy.search.presentation

import androidx.paging.PagingData
import com.search.giphy.search.domain.Gif
import kotlinx.coroutines.flow.Flow

sealed class ViewState {
    object Empty : ViewState()
    data class Results(val searchResults: Flow<PagingData<Gif>>) : ViewState()
}

sealed class SearchNavigationEvent {
    data class ToFullScreen(val gif: Gif) : SearchNavigationEvent()
}