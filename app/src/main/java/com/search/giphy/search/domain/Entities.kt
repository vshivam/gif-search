package com.search.giphy.search.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gif(val id: String, val thumbnail: String, val highRes: String) : Parcelable