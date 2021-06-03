package com.search.giphy.search.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponseRaw(
    val data: List<GifRaw>,
    val pagination: PaginationRaw,
)

@JsonClass(generateAdapter = true)
data class GifRaw(
    val id: String,
    val url: String,
    val images: Images
)

@JsonClass(generateAdapter = true)
data class Images(
    val downsized: LowRes,
    val fixed_height: HighRes
)

@JsonClass(generateAdapter = true)
data class LowRes(val url: String)

@JsonClass(generateAdapter = true)
data class HighRes(val url: String)

@JsonClass(generateAdapter = true)
data class PaginationRaw(
    val offset: Int,
    val total_count: Int,
    val count: Int
)