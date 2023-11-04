package com.stayhook.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MyProfileResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("aadhar_number") var aadhaarNumber: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("city_id") var cityId: String? = null,
    @SerializedName("city_name") var cityName: String? = null,
    @SerializedName("country_id") var countryId: String? = null,
    @SerializedName("dob") var dob: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("full_name") var fullName: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("mobile") var mobile: String? = null,
    @SerializedName("pan_number") var panNumber: String? = null,
    @SerializedName("pincode") var pinCode: String? = null,
    @SerializedName("police_verification") var policeVerification: String? = null,
    @SerializedName("profile") var profile: String,
    @SerializedName("state_id") var stateId: String? = null,
    @SerializedName("state_name") var stateName: String? = null,
    @SerializedName("upload_aadhaar") var uploadAadhaar: String? = null,
    @SerializedName("uploadAadhaar2") var uploadAadhaar2: String? = null,
    @SerializedName("upload_pan") var uploadPan: String? = null
) : Serializable