package com.stayhook.util

import com.stayhook.model.response.UserResponse
import com.stayhook.preference.PreferenceHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object Constants {

    object PreferenceConstant : KoinComponent{
        private val mPref: PreferenceHelper by inject()
        const val PREFERENCE_NAME = "StayHookApplication"
        const val IS_LOGIN = "IS_LOGIN"
        const val TOKEN = "TOKEN"
        const val LATITUDE = "LATITUDE"
        const val LONGITUDE = "LONGITUDE"
        const val AUTHORIZATION = "Authorization"
        const val USER_DETAIL = "USER_DETAIL"
        const val API_TIMEOUT = 6000L
        fun updateUserDetail(response: UserResponse) {
            val updateResponse = UserResponse().apply {
                id = response.id
                firstName = response.firstName
                mobileNo = response.mobileNo
                profile = response.profile
                gender = response.gender
                fullName = response.fullName
                token = response.token
            }
          //  mPref.setUserDetail(updateResponse)
        }
    }

    class NetworkConstant{
        companion object{
            const val API_SUCCESS=200
            const val API_AUTH_ERROR=403
            const val BAD_REQUEST = 401
            const val API_TIME_OUT = 60000
            const val NO_INTERNET_AVAILABLE = "Oops! No Internet"
            const val CONNECTION_LOST = "Oops! Connection lost! "
            const val API_INTERNAL_SERVER_ERROR=500
            const val BASE_URL = "https://stayhook.com/api/"
            const val loginApi = "userLogin"
            const val registerApi = "userRegister"
            const val homeApi = "homePage"
            const val getPropertyApi = "getProperty"
            const val getPropertyDetailApi = "getPropertyDetails"
            const val getPropertyRoomApi = "getPropertyRoom"
            const val getPropertyBedApi = "getRoomBed"
            const val tokenCollected = "tokenCollected"
            const val getTokenCollected = "getTokenCollected"
            const val getScheduledToken = "getScheduleToken"
            const val getScheduledDetailToken = "getScheduleTokenDetails"
            const val scheduleVisit = "scheduleVisit"
            const val myPayments = "myPayment"

            const val updateApi = "updateProfile"
            const val updateKYCApi = "updateKyc"
            const val myProfileApi = "myProfile"
            const val getStateApi = "getState"
            const val getCityApi = "getCity"

        }
    }
    class DefaultConstants{
        companion object{
            const val BUNDLE="bundle"
            const val MODEL_DETAIL="MODEL_DETAIL"
            const val SELECT_PROPERTY_ID="property_id"
            const val SELECT_PROPERTY_ROOM_ID="property_room_id"
            const val SELECT_PROPERTY_ROOM_BED_ID="property_room_bed_id"
            const val STRING = "title"
            const val ROOM_TYPE = "title"

        }
    }
}