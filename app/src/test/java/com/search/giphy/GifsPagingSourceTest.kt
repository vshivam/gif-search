package com.search.giphy

import androidx.paging.PagingSource
import com.haroldadmin.cnradapter.NetworkResponse
import com.search.giphy.search.data.GifsPagingSource
import com.search.giphy.search.data.PaginationRaw
import com.search.giphy.search.data.SearchResponseRaw
import com.search.giphy.search.data.SearchService
import com.search.giphy.fake.GifData
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GifsPagingSourceTest {

    private val searchService: SearchService = mock()
    private val searchQuery = "interstellar"
    private val pageSize = 5

    private val tested = GifsPagingSource(searchService, searchQuery, pageSize)

    @Test
    fun `when null key is provided, verify service call with starting page`() {
        runBlocking {
            val gifs = GifData.gifRawList(15)
            val searchResponseRaw = SearchResponseRaw(
                data = gifs,
                pagination = PaginationRaw(0, 1500, 15)
            )
            val successResponse = NetworkResponse.Success(searchResponseRaw, code = 200)

            whenever(searchService.getGifs(searchQuery, 15, 0)).thenReturn(successResponse)

            val expected = PagingSource.LoadResult.Page(gifs, null, 3)
            val actual = tested.load(
                PagingSource.LoadParams.Refresh(
                    key = null,
                    loadSize = 15 /* PAGE_SIZE times 3 */,
                    placeholdersEnabled = false
                )
            )

            verify(searchService).getGifs(searchQuery, 15, 0) // STARTING_PAGE = 0
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `when service returns success, return load result with loaded gifs`() = runBlocking {
        val gifs = GifData.gifRawList(5)
        val searchResponseRaw = SearchResponseRaw(
            data = gifs,
            pagination = PaginationRaw(0, 100, 5),
        )
        val successResponse = NetworkResponse.Success(searchResponseRaw, code = 200)

        whenever(searchService.getGifs(any(), any(), any())).thenReturn(successResponse)

        val expected = PagingSource.LoadResult.Page(gifs, null, 3)
        val actual = tested.load(PagingSource.LoadParams.Refresh(null, loadSize = 15, false))
        assertEquals(expected, actual)
    }

    @Test
    fun `when all pages have been loaded, return null nextKey`() {
        runBlocking {
            val gifs = GifData.gifRawList(5)
            val searchResponseRaw = SearchResponseRaw(
                data = gifs,
                pagination = PaginationRaw(1495, 1500, 5)
            )
            val successResponse = NetworkResponse.Success(searchResponseRaw, code = 200)

            whenever(searchService.getGifs(any(), any(), any())).thenReturn(successResponse)

            val expected = PagingSource.LoadResult.Page(gifs, 296, null)
            val actual = tested.load(PagingSource.LoadParams.Refresh(297, 5, false))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `when all pages have been loaded and the number of fetched gifs is less than number of requested gifs, return null next key`() {
        runBlocking {
            val gifs = GifData.gifRawList(3)
            val searchResponseRaw = SearchResponseRaw(
                data = gifs,
                pagination = PaginationRaw(1495, 1498, 3)
            )
            val successResponse = NetworkResponse.Success(searchResponseRaw, code = 200)

            whenever(searchService.getGifs(any(), any(), any())).thenReturn(successResponse)

            val expected = PagingSource.LoadResult.Page(gifs, 296, null)
            val actual = tested.load(PagingSource.LoadParams.Refresh(297, 5, false))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `when service returns error, return error load result`() = runBlocking {
        val errorResponse = NetworkResponse.ServerError(Unit, 200)

        whenever(searchService.getGifs(any(), any(), any())).thenReturn(errorResponse)

        val actual = tested.load(PagingSource.LoadParams.Refresh(1, 20, false))
        assert(actual is PagingSource.LoadResult.Error)
    }
}