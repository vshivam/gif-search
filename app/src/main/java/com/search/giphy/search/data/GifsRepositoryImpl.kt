package com.search.giphy.search.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.search.giphy.search.domain.Gif
import com.search.giphy.search.domain.GifsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GifsRepositoryImpl @Inject constructor(
    private val searchService: SearchService,
    private val gifDomainMapper: GifDomainMapper
) : GifsRepository {

    override fun getPosts(query: String): Flow<PagingData<Gif>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = MAX_SIZE
            ),
            pagingSourceFactory = { GifsPagingSource(searchService, query, PAGE_SIZE) }
        ).flow.map { pagingData -> pagingData.map { gifDomainMapper(it) } }
    }

    private companion object {
        const val PAGE_SIZE = 20
        const val MAX_SIZE = 60
    }
}