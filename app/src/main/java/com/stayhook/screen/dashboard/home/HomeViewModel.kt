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

    val nearbyLocationList = MutableLiveData<MutableList<Recommendation>>()


    fun getRecommendationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                IMAGE_1,
                "Abok hook",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "", mutableListOf(IMAGE_1, IMAGE_2, IMAGE_3, IMAGE_4, IMAGE_5)
            )
        )
        list.add(
            Recommendation(
                IMAGE_2,
                "Kuldeep's hotel",
                "single Room",
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
                "Raju's",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "Men",
                mutableListOf(IMAGE_3, IMAGE_4, IMAGE_5)
            )
        )
        list.add(
            Recommendation(
                IMAGE_4,
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "Women",
                mutableListOf(IMAGE_1, IMAGE_3, IMAGE_4)
            )
        )
        return list
    }

    fun getNearByLocationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                "https://images.oyoroomscdn.com/uploads/hotel_image/168652/c4f2cc00b8fb02ba.jpg",
                "Abok hook",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://i0.wp.com/stanzaliving.wpcomstaging.com/wp-content/uploads/2022/04/d601b-hostels-vs-pgs-min.jpg?fit=4000%2C3000&ssl=1",
                "Kuldeep's hotel",
                "single Room",
                "Greater Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://content3.jdmagicbox.com/comp/chennai/g8/044pxx44.xx44.180125140725.t6g8/catalogue/victorias-in-ladies-and-pg-hostel-sirucheri-chennai-hostel-for-girl-students-c72xw2x314.jpg?clr=",
                "Raju's",
                "single Room",
                "Noida,Sector 4",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://www.thehivehostels.com/uploads/images/1658301040_7796f3aa4d7819a2f5d5.jpeg",
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        return list
    }

}