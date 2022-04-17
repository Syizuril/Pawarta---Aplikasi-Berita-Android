package com.syizuril.pawarta.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsModel (
	val status : String?,
	val totalResults : Int?,
	val articles : List<Articles>?
): Parcelable