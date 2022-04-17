package com.syizuril.pawarta

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syizuril.pawarta.model.Category
import com.syizuril.pawarta.model.CategoryList
import com.syizuril.pawarta.model.SourceList

class CategoryViewModel : ViewModel() {
    private val _category = MutableLiveData<CategoryList>()
    val category: LiveData<CategoryList>
        get() = _category

    init {
        initCategory()
    }

    private fun initCategory(){
        _category.value = CategoryList(
            listOf(
                Category(R.drawable.ic_baseline_business_24, "Business"),
                Category(R.drawable.ic_baseline_ondemand_video_24, "Entertainment"),
                Category(R.drawable.ic_baseline_local_hospital_24, "Heatlh"),
                Category(R.drawable.ic_baseline_science_24, "Science"),
                Category(R.drawable.ic_baseline_sports_volleyball_24, "Sports"),
                Category(R.drawable.ic_baseline_settings_24, "Technology"),
            )
        )
    }
}