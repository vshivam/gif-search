package com.search.giphy.search.data

import com.search.giphy.search.domain.Gif
import javax.inject.Inject

class GifDomainMapper @Inject constructor() {
    operator fun invoke(gifRaw: GifRaw) =
        Gif(gifRaw.id, gifRaw.images.downsized.url, gifRaw.images.fixed_height.url)
}
