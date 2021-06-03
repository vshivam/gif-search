package com.search.giphy.search.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse

class GifsPagingSource(
    private val searchService: SearchService,
    private val query: String,
    private val pageSize: Int
) : PagingSource<Int, GifRaw>() {

    override fun getRefreshKey(state: PagingState<Int, GifRaw>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifRaw> {
        val position = params.key ?: STARTING_PAGE
        return when (val responseRaw = searchService.getGifs(
            query = query,
            limit = params.loadSize,
            offset = position * pageSize
        )) {
            is NetworkResponse.Success -> {
                with(responseRaw) {
                    LoadResult.Page(
                        body.data,
                        prevKey = if (position == STARTING_PAGE) null else position - 1,
                        nextKey = if (body.pagination.offset + body.pagination.count == body.pagination.total_count)
                            null
                        else
                            position + (params.loadSize / pageSize)
                    )
                }
            }
            is NetworkResponse.ServerError -> LoadResult.Error(responseRaw.error)
            is NetworkResponse.NetworkError -> LoadResult.Error(responseRaw.error)
            is NetworkResponse.UnknownError -> LoadResult.Error(responseRaw.error)
        }
    }

    companion object {
        private const val STARTING_PAGE = 0
    }
}
