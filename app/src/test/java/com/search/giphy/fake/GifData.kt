package com.search.giphy.fake

import com.search.giphy.search.data.GifRaw
import com.search.giphy.search.data.HighRes
import com.search.giphy.search.data.Images
import com.search.giphy.search.data.LowRes

object GifData {

    fun gifRawList(count: Int): List<GifRaw> {
        val list = mutableListOf<GifRaw>()
        for (i in 1..count) {
            list.add(gifRaw(id = "$i"))
        }
        return list.toList()
    }

    fun gifRaw(
        id: String = "gif_id",
        url: String = "gif_url",
        images: Images = images()
    ) = GifRaw(
        id = id,
        url = url,
        images = images
    )

    fun images(
        downsized: LowRes = LowRes("low_res_url"),
        fixed_height: HighRes = HighRes("high_res_url")
    ) = Images(
        downsized,
        fixed_height = fixed_height
    )
}