package com.stayhook.screen.dashboard.home

import android.location.Geocoder
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.stayhook.R
import com.stayhook.base.BaseViewModel
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.network.ApiResponse
import com.stayhook.network.NetworkConnectionManager
import com.stayhook.screen.dashboard.home.privateroom.PrivateRoomActivity
import com.stayhook.screen.dashboard.home.sharedroom.SharedRoomActivity
import com.stayhook.screen.notification.NotificationFragment
import com.stayhook.util.Constants
import com.stayhook.util.Utility.isConnectionAvailable
import com.stayhook.util.mLog
import com.stayhook.util.mToast
import java.util.Locale

class HomeViewModel(private val homeRepo: HomeRepository) : BaseViewModel() {

    private val homeResponse = MutableLiveData<ApiResponse<HomeResponse>>()
    private val addFavoritePropertyResponse = MutableLiveData<ApiResponse<SuccessErrorResponse>>()
    lateinit var fragmentHome: HomeFragment
    fun hitHomePageApi() {
        homeRepo.executeHomePageAPi(homeResponse)
    }

    fun getHomeResponse(): MutableLiveData<ApiResponse<HomeResponse>> {
        return homeResponse
    }

    fun onClickLocationBtn() {
        // replaceFragWithB(fragmentHome!!.getString(R.string.house))
        //fragmentHome?.launchActivity(HomeRoomTypeActivity::class.java)
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


    fun getCurrentAddress(): String? {

        val lat = mPref[Constants.PreferenceConstant.LATITUDE, 0f]?.toDouble()
        val lng = mPref[Constants.PreferenceConstant.LONGITUDE, 0f]?.toDouble()
        var address: String? = null

        if (lat != 0.0 && lng != 0.0) {
            mLog("user current address $lat $lng")

            val geocoder = Geocoder(fragmentHome.requireContext(), Locale.getDefault())
            val addresses = geocoder.getFromLocation(
                lat!!,
                lng!!,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            val addressLine =
                addresses!![0].getAddressLine(1) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            mLog("user current complete address line $addressLine")

            val city = addresses[0].locality
            val state = addresses[0].adminArea
            val country = addresses[0].countryName
            val postalCode = addresses[0].postalCode
            val knownName = addresses[0].featureName

            address =  String.format("%s,%s",city,country)

        } else {
            mLog("user current null address $lat $lng")
            address = ""
        }
        return address
    }



    fun hitAddFavoritePropertyApi(propertyId:String) {
        homeRepo.executeAddFavoriteProperty(propertyId,addFavoritePropertyResponse)
    }

    fun getAddFavoritePropertyResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return addFavoritePropertyResponse
    }

}