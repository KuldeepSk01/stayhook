package com.stayhook.network

import com.stayhook.base.BaseResponse
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.request.AddTicketScheduleMeetRequest
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.request.MoveOutBookingRequest
import com.stayhook.model.request.PropertyRoomRequest
import com.stayhook.model.request.ScheduleAVisitRequest
import com.stayhook.model.request.UserRequest
import com.stayhook.model.request.WriteReviewRequest
import com.stayhook.model.response.MyBookingResponse
import com.stayhook.model.response.MyPaymentsResponse
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.OTPResponse
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.TicketDetailResponse
import com.stayhook.model.response.TicketResponse
import com.stayhook.model.response.TokenCollectedResponse
import com.stayhook.model.response.UserResponse
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    fun getProperty(@Body body: GetPropertyRequest): Call<GetPropertyBaseResponse>


    @POST(Constants.NetworkConstant.getPropertyDetailApi)
    fun getPropertyDetail(@Query("property_id") propertyDetail: String): Call<BaseResponse<GetPropertyDetail>>

    @POST(Constants.NetworkConstant.getPropertyRoomApi)
    fun getPropertyRoom(@Query("property_id") propertyDetail: String): Call<CollectionBaseResponse<PropertyRoom>>

    @POST(Constants.NetworkConstant.getPropertyBedApi)
    fun getPropertyBed(
        @Query("property_id") propertyDetail: String,
        @Query("room_id") roomId: String
    ): Call<CollectionBaseResponse<PropertyRoom>>

    @POST(Constants.NetworkConstant.tokenCollected)
    fun tokenCollected(@Body request: PropertyRoomRequest): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.getTokenCollected)
    fun getTokenCollected(): Call<CollectionBaseResponse<TokenCollectedResponse>>


    @POST(Constants.NetworkConstant.getMyBooking)
    fun getMyBooking(): Call<CollectionBaseResponse<MyBookingResponse>>

    @POST(Constants.NetworkConstant.requestMoveout)
    fun requestMoveOutBooking(@Body req: MoveOutBookingRequest): Call<CollectionBaseResponse<SuccessErrorResponse>>


    @FormUrlEncoded
    @POST(Constants.NetworkConstant.getScheduledToken)
    fun getScheduledToken(@Field("time_status") status: String): Call<CollectionBaseResponse<TokenCollectedResponse>>

    @POST(Constants.NetworkConstant.getScheduledDetailToken)
    fun getScheduledDetailToken(@Query("id") tokenId: String): Call<BaseResponse<TokenCollectedResponse>>

    @POST(Constants.NetworkConstant.scheduleVisit)
    fun scheduleAVisit(@Body request: ScheduleAVisitRequest): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.myPayments)
    fun myPayments(
        @Query("type") type: String,
        @Query("page") page: String
    ): Call<CollectionBaseResponse<MyPaymentsResponse>>


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
    fun getStates(@Query("country_id") countryId: String): Call<CollectionBaseResponse<StateCityResponse>>

    @POST(Constants.NetworkConstant.getCityApi)
    fun getCity(@Query("state_id") stateId: String): Call<CollectionBaseResponse<StateCityResponse>>


    @POST(Constants.NetworkConstant.addReview)
    fun addReview(@Body request: WriteReviewRequest): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.addRemoveFavorite)
    fun addORRemoveFavorite(@Query("property_id") propertyId: String): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.getIssueType)
    fun getIssueType(): Call<CollectionBaseResponse<StateCityResponse>>

    @POST(Constants.NetworkConstant.getIssueSubType)
    fun getIssuesSubType(@Query("issue_type") issueType: Int): Call<CollectionBaseResponse<StateCityResponse>>

    @POST(Constants.NetworkConstant.getTicketProperty)
    fun getTicketProperty(): Call<CollectionBaseResponse<StateCityResponse>>

    @FormUrlEncoded
    @POST(Constants.NetworkConstant.myTicket)
    fun getMyTickets(@Field("status") status: String): Call<CollectionBaseResponse<TicketResponse>>


    /*  @Multipart
      @POST(Constants.NetworkConstant.createTicket)
      fun createTickets(
          @Query("property_id") propertyId: Int,
          @Query("issue_type") issueType: Int,
          @Query("issue") issue: Int,
          @Query("issue_details") issueDetail: String,
          @Query ("upload_document[]") imagesList: List<String>,

      ): Call<SuccessErrorResponse>*/


    @Multipart
    @POST(Constants.NetworkConstant.createTicket)
    fun createTickets(
        @Part("property_id") propertyId: RequestBody,
        @Part("issue_type") issueType: RequestBody,
        @Part("issue") issue: RequestBody,
        @Part("issue_details") issueDetail: RequestBody,
        @Part uploadList: Array<MultipartBody.Part?>?,
    ): Call<SuccessErrorResponse>

    @POST(Constants.NetworkConstant.getTicketDetails)
    fun getTicketDetail(@Query("ticket_id") ticketId: Int): Call<BaseResponse<TicketDetailResponse>>

    @POST(Constants.NetworkConstant.addTicketComment)
    fun addTicketComment(
        @Query("ticket_id") ticketId: Int,
        @Query("comment") comment: String
    ): Call<SuccessErrorResponse>

    /*@FormUrlEncoded
    @POST(Constants.NetworkConstant.getTicketDetails)
    fun addTicketScheduleMeet(
        @Field("ticket_id") ticketId:Int,
        @Field("meeting_date") meetingDate:String,
        @Field("meeting_time") meetingTime:String,
        @Field("comment") comment: String,
    ): Call<SuccessErrorResponse>*/


    @POST(Constants.NetworkConstant.addTicketMeeting)
    fun addTicketScheduleMeet(
        @Body req: AddTicketScheduleMeetRequest
    ): Call<SuccessErrorResponse>

}