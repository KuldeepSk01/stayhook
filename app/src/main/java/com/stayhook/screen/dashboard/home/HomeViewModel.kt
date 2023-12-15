package com.stayhook.screen.dashboard.home

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseViewModel
import com.stayhook.model.Recommendation
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.privateroom.PrivateRoomActivity
import com.stayhook.screen.dashboard.home.sharedroom.SharedRoomActivity
import com.stayhook.screen.notification.NotificationFragment
import com.stayhook.util.Constants
import com.stayhook.util.IMAGE_1
import com.stayhook.util.IMAGE_2
import com.stayhook.util.IMAGE_3
import com.stayhook.util.IMAGE_4
import com.stayhook.util.IMAGE_5

class HomeViewModel(private val homeRepo: HomeRepository) : BaseViewModel() {

    private val homeResponse = MutableLiveData<ApiResponse<HomeResponse>>()
    lateinit var fragmentHome: HomeFragment
    fun hitHomePageApi() {
        homeRepo.executeHomePageAPi(homeResponse)
    }

    fun getHomeResponse(): MutableLiveData<ApiResponse<HomeResponse>> {
        return homeResponse
    }


    fun onClickHouse() {
        // replaceFragWithB(fragmentHome!!.getString(R.string.house))
        //fragmentHome?.launchActivity(HomeRoomTypeActivity::class.java)
    }

    fun onClickPrivateRoom() {

        fragmentHome?.launchActivity(
            PrivateRoomActivity::class.java,
            Constants.DefaultConstants.STRING,
            fragmentHome!!.getString(R.string.private_room)
        )
    }

    fun onClickSharedRoom() {
        replaceFrag(SharedRoomActivity())
        fragmentHome?.hideTab()
        //replaceFragWithB(fragmentHome!!.getString(R.string.shared_room))
        /* fragmentHome?.launchActivity(
             SharedRoomActivity::class.java,Constants.DefaultConstants.STRING,
             fragmentHome!!.getString(R.string.shared_room))*/
    }

    fun onClickNotificationIcon() {
        replaceFrag(NotificationFragment())
        fragmentHome?.hideTab()
    }

    fun onClickSeeAllItems() {
        replaceFrag(SeeAllItemsFragment())
        fragmentHome?.hideTab()
    }


    /* private fun replaceFragWithB(title: String) {
         val b = Bundle()
         b.putString(Constants.DefaultConstants.STRING, title)
         fragmentHome?.replaceFragment(
             R.id.flMainContainer,
             HomeRoomTypeFragment(), b,
             HomeFragment().javaClass.simpleName
         )
     }*/

    private fun replaceFrag(f: Fragment) {
        fragmentHome?.replaceFragment(
            R.id.flMainContainer, f, HomeFragment().javaClass.simpleName
        )
    }


}