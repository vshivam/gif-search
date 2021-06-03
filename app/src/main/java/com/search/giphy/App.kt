package com.search.giphy

import android.app.Application
import android.os.Build.VERSION.SDK_INT
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupImageLoader()
    }

    private fun setupImageLoader() {
        Coil.setImageLoader(
            ImageLoader.Builder(applicationContext)
                .componentRegistry {
                    if (SDK_INT >= 28) {
                        add(ImageDecoderDecoder(applicationContext))
                    } else {
                        add(GifDecoder())
                    }
                }
                .build()
        )
    }
}