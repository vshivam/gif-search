package com.search.giphy.search.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface GifsRepository {
    fun getPosts(query: String): Flow<PagingData<Gif>>
}