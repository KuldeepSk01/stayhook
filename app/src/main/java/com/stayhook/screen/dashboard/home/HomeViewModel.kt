package com.stayhook.screen.dashboard.home

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseResponse
import com.stayhook.base.BaseViewModel
import com.stayhook.model.Recommendation
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.network.ApiResponse
import com.stayhook.permissions.MyPermissions
import com.stayhook.screen.notification.NotificationFragment
import com.stayhook.util.Constants
import com.stayhook.util.IMAGE_1
import com.stayhook.util.IMAGE_2
import com.stayhook.util.IMAGE_3
import com.stayhook.util.IMAGE_4
import com.stayhook.util.IMAGE_5

class HomeViewModel(private val homeRepo: HomeRepository) : BaseViewModel() {

    private val homeResponse = MutableLiveData<ApiResponse<HomeResponse>>()
    fun hitHomePageApi(){
        homeRepo.executeHomePageAPi(homeResponse)
    }
    fun getHomeResponse():MutableLiveData<ApiResponse<HomeResponse>>{
        return homeResponse
    }




    val recommendationList = MutableLiveData<MutableList<Recommendation>>()
    var fragmentHome: HomeFragment? = null

    fun onClickLocation() {
        if (MyPermissions.isLocationEnable) {
            Toast.makeText(fragmentHome?.requireContext(), "Granted..", Toast.LENGTH_SHORT).show()
        } else {
            MyPermissions.getLocationPermission(fragmentHome!!.requireContext())
        }
    }

    fun onClickHouse() {
        replaceFragWithB(fragmentHome!!.getString(R.string.house))
        fragmentHome?.hideTab()
    }

    fun onClickPrivateRoom() {
        replaceFragWithB(fragmentHome!!.getString(R.string.private_room))
        fragmentHome?.hideTab()
    }

    fun onClickSharedRoom() {
        replaceFragWithB(fragmentHome!!.getString(R.string.shared_room))
        fragmentHome?.hideTab()
    }

    fun onClickNotificationIcon() {
        replaceFrag(NotificationFragment())
        fragmentHome?.hideTab()
    }

    fun onClickSeeAllItems() {
        replaceFrag(SeeAllItemsFragment())
        fragmentHome?.hideTab()
    }

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
                "Men", mutableListOf(IMAGE_1, IMAGE_2, IMAGE_3, IMAGE_4, IMAGE_5)
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
                IMAGE_4, "Shared Room",
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


    private fun replaceFragWithB(title: String) {
        val b = Bundle()
        b.putString(Constants.DefaultConstants.STRING, title)
        fragmentHome?.replaceFragment(
            R.id.flMainContainer,
            HomeRoomTypeFragment(), b,
            HomeFragment().javaClass.simpleName
        )
    }

    private fun replaceFrag(f: Fragment) {
        fragmentHome?.replaceFragment(
            R.id.flMainContainer,
            f,
            HomeFragment().javaClass.simpleName
        )
    }


}