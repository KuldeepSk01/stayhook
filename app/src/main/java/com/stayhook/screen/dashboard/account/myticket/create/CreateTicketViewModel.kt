package com.stayhook.screen.dashboard.account.myticket.create

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseResponse
import com.stayhook.base.BaseViewModel
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.request.AddTicketScheduleMeetRequest
import com.stayhook.model.request.TicketRequest
import com.stayhook.model.response.IssueTypeResponse
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.TicketDetailResponse
import com.stayhook.model.response.TicketResponse
import com.stayhook.network.ApiResponse

class CreateTicketViewModel(private val repo: CreateTicketRepo) : BaseViewModel() {
    private val ticketDetail =
        MutableLiveData<ApiResponse<BaseResponse<TicketDetailResponse>>>()

    private val getIssueType =
        MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>()

    private val getIssueSubType =
        MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>()


    private val getTicketProperty =
        MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>()

    private val getMyTicket =
        MutableLiveData<ApiResponse<CollectionBaseResponse<TicketResponse>>>()

    private val createTicket =
        MutableLiveData<ApiResponse<SuccessErrorResponse>>()


    private val addTicketComment =
        MutableLiveData<ApiResponse<SuccessErrorResponse>>()

    private val addTicketScheduleMeet =
        MutableLiveData<ApiResponse<SuccessErrorResponse>>()





    fun hitIssueTypeApi() {
        repo.executeGetIssueType(getIssueType)
    }

    fun getIssueTypeResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>> {
        return getIssueType
    }



    fun hitIssueSubTypeApi(issueId:Int) {
        repo.executeGetIssueSubType(issueId,getIssueSubType)
    }

    fun getIssueSubTypeResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>> {
        return getIssueSubType
    }


    fun hitTicketPropertyApi() {
        repo.executeGetTicketProperty(getTicketProperty)
    }

    fun getTicketPropertyResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>> {
        return getTicketProperty
    }



    fun hitMyTicketApi(status:String) {
        repo.executeGetTickets(status,getMyTicket)
    }

    fun getTicketResponse(): MutableLiveData<ApiResponse<CollectionBaseResponse<TicketResponse>>> {
        return getMyTicket
    }


    fun hitCreateTicketApi(req:TicketRequest) {
        repo.executeCreateTicket(req,createTicket)
    }

    fun getCreateTicketResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return createTicket
    }


    fun hitGetTicketDetailApi(ticketId:Int) {
        repo.executeGetTicketDetail(ticketId,ticketDetail)
    }

    fun getTicketDetailResponse(): MutableLiveData<ApiResponse<BaseResponse<TicketDetailResponse>>> {
        return ticketDetail
    }




    fun hitAddTicketCommentApi(ticketId:Int,comment:String) {
        repo.executeAddTicketComment(ticketId,comment,addTicketComment)
    }

    fun getAddTicketCommentResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return addTicketComment
    }


    fun hitAddTicketScheduleMeetApi(request: AddTicketScheduleMeetRequest) {
        repo.executeAddTicketScheduleMeet(request,addTicketScheduleMeet)
    }

    fun getAddTicketScheduleMeetResponse(): MutableLiveData<ApiResponse<SuccessErrorResponse>> {
        return addTicketScheduleMeet
    }




}