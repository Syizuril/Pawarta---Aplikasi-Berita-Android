package com.syizuril.pawarta.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Syekh Syihabuddin Azmil Umri on 17/04/2022.
 */
@Parcelize
data class SourceList (
    val sources : List<Source>?
) : Parcelable