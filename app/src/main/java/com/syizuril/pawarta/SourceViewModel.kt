package com.syizuril.pawarta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syizuril.pawarta.model.Source
import com.syizuril.pawarta.model.SourceList

class SourceViewModel : ViewModel() {
    private val _source = MutableLiveData<SourceList>()
    val source: LiveData<SourceList>
        get() = _source

    init {
        initSourceIndo()
    }

    private fun initSourceIndo() {
        _source.value = SourceList(
            listOf(
                Source(null,"liputan6.com"),
                Source(null,"detik.com"),
                Source(null,"cnnindonesia.com"),
                Source(null,"pikiran-rakyat.com"),
                Source(null,"suara.com"),
                Source(null,"pikiran-rakyat.com"),
                Source(null,"merdeka.com"),
                Source(null,"viva.co.id"),
                Source(null,"vice.com"),
                Source(null,"bbc.com"),
                Source(null,"kapanlagi.com"),
                Source(null,"kumparan.com"),
                Source(null,"kapanlagi.com"),
                Source(null,"tempo.co"),
                Source(null,"sindonews.co"),
            )
        )
    }
}