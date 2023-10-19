package com.stayhook.screen.dashboard.home

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseViewModel
import com.stayhook.model.Recommendation
import com.stayhook.util.IMAGE_1
import com.stayhook.util.IMAGE_2
import com.stayhook.util.IMAGE_3
import com.stayhook.util.IMAGE_4
import com.stayhook.util.IMAGE_5

class HomeViewModel(val repo: HomeRepository) : BaseViewModel() {
    val recommendationList = MutableLiveData<MutableList<Recommendation>>()
    fun getRecommendationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                IMAGE_1,
                "Shared Room",
                "Primary Apartment",
                "Noida",
                "2222",
                "4.5",
                "", mutableListOf(IMAGE_1, IMAGE_2, IMAGE_3, IMAGE_4, IMAGE_5)
            )
        )
        list.add(
            Recommendation(
                IMAGE_2,
                "Private Room",
                "Primary Apartment",
                "Noida",
                "2222",
                "4.5",
                "Women",
                mutableListOf(IMAGE_1, IMAGE_2, IMAGE_3)
            )
        )
        list.add(
            Recommendation(
                IMAGE_3,
                "Shared Room",
                "Primary Apartment",
                "Noida",
                "2222",
                "4.5",
                "Men",
                mutableListOf(IMAGE_3, IMAGE_4, IMAGE_5)
            )
        )
        list.add(
            Recommendation(
                IMAGE_4,"Shared Room",
                "Primary Apartment",
                "Noida",
                "2222",
                "4.5",
                "Women",
                mutableListOf(IMAGE_1, IMAGE_3, IMAGE_4)
            )
        )
        return list
    }


}