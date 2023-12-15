package com.stayhook.network

import com.stayhook.base.BaseResponse
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.request.PropertyRoomRequest
import com.stayhook.model.request.ScheduleAVisitRequest
import com.stayhook.model.request.UserRequest
import com.stayhook.model.response.MyPaymentsResponse
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.OTPResponse
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.model.response.scheduletokendetail.ScheduleTokenDetailResponse
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

   /* @POST(Constants.NetworkConstant.getPropertyApi)
    fun getProperty(@Query("page") page:String): Call<GetPropertyBaseResponse>
*/
    @POST(Constants.NetworkConstant.getPropertyApi)
    fun getProperty(@Body body:GetPropertyRequest): Call<GetPropertyBaseResponse>


    @POST(Constants.NetworkConstant.getPropertyDetailApi)
    fun getPropertyDetail(@Query("property_id") propertyDetail: String): Call<BaseResponse<GetPropertyDetail>>

    @POST(Constants.NetworkConstant.getPropertyRoomApi)
    fun getPropertyRoom(@Query("property_id") propertyDetail: String): Call<CollectionBaseResponse<PropertyRoom>>

    @POST(Constants.NetworkConstant.getPropertyBedApi)
    fun getPropertyBed(@Query("property_id") propertyDetail: String,@Query("room_id") roomId:String): Call<CollectionBaseResponse<PropertyRoom>>

    @POST(Constants.NetworkConstant.tokenCollected)
    fun tokenCollected(@Body request: PropertyRoomRequest): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.getTokenCollected)
    fun getTokenCollected(): Call<CollectionBaseResponse<TokenCollectedResponse>>

    @POST(Constants.NetworkConstant.getScheduledToken)
    fun getScheduledToken(): Call<CollectionBaseResponse<TokenCollectedResponse>>

    @POST(Constants.NetworkConstant.getScheduledDetailToken)
    fun getScheduledDetailToken(@Query("id") tokenId:String): Call<BaseResponse<TokenCollectedResponse>>

    @POST(Constants.NetworkConstant.scheduleVisit)
    fun scheduleAVisit(@Body request: ScheduleAVisitRequest): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.myPayments)
    fun myPayments(@Query("type") type:String,@Query("page") page:String): Call<CollectionBaseResponse<MyPaymentsResponse>>


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
    ): Call<SuccessErrorResponse>


    @Multipart
    @POST(Constants.NetworkConstant.updateKYCApi)
    fun updateKYC(
        @Part("first_name") fullName: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("aadhar_number") aadhaarNo: RequestBody,
        @Part("pan_number") panNo: RequestBody,
        @Part("address") address: RequestBody,
        @Part("state") state: RequestBody,
        @Part("city") city: RequestBody,
        @Part frontAadhaar: MultipartBody.Part?,
        @Part backAadhaar: MultipartBody.Part?,
        @Part panAadhaar: MultipartBody.Part?,
        @Part policeVerificationAadhaar: MultipartBody.Part?,
    ): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.getStateApi)
    fun getStates(@Query("country_id") countryId:String): Call<CollectionBaseResponse<StateCityResponse>>

    @POST(Constants.NetworkConstant.getCityApi)
    fun getCity(@Query("state_id") stateId:String): Call<CollectionBaseResponse<StateCityResponse>>



}