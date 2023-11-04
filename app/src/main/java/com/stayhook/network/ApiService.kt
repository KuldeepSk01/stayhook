package com.stayhook.network

import com.stayhook.base.BaseResponse
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.OTPResponse
import com.stayhook.model.response.UpdateProfileResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {

    @POST(Constants.NetworkConstant.loginApi)
    fun login(@Body user: UserRequest): Call<OTPResponse>

    @POST(Constants.NetworkConstant.loginApi)
    fun verifyOTP(@Body user: UserRequest): Call<BaseResponse<UserResponse>>

    @POST(Constants.NetworkConstant.registerApi)
    fun register(@Body user: UserRequest): Call<OTPResponse>

    @POST(Constants.NetworkConstant.homeApi)
    fun homePage(): Call<HomeResponse>

    @POST(Constants.NetworkConstant.getPropertyApi)
    fun getProperty(@Query("page") page:String): Call<GetPropertyBaseResponse>

    @POST(Constants.NetworkConstant.getPropertyDetailApi)
    fun getPropertyDetail(@Query("property_id") propertyDetail: String): Call<BaseResponse<GetPropertyDetail>>

    @POST(Constants.NetworkConstant.myProfileApi)
    fun getMyProfile(): Call<BaseResponse<MyProfileResponse>>

    @Multipart
    @POST(Constants.NetworkConstant.updateApi)
    fun updateProfile(
        @Part("full_name") full_name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("mobile_no") mobile_no: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part image: MultipartBody.Part?,
    ): Call<UpdateProfileResponse>


}