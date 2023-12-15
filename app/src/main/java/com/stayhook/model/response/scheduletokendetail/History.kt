package com.stayhook.model.response.scheduletokendetail

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class History(
    @SerializedName("created_at")
    val created_at: String?=null,
    @SerializedName("id")
    val id: Int,
    @SerializedName("remark")
    val remark: String?=null,
    @SerializedName("status")
    val status: String?=null,
    @SerializedName("agreement")
    val agreement: String?=null,
    @SerializedName("police_verification")
    val police_verification: String?=null,
    @SerializedName("signatured_agreement")
    val signatured_agreement: String?=null,
    @SerializedName("visit_time")
    val visitTime: String?=null,
    @SerializedName("visit_date")
    val visitDate: String?=null,
    @SerializedName("field_person")
    val fieldPerson: String?=null


):Serializable