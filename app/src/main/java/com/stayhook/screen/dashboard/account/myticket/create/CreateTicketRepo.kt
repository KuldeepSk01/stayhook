package com.stayhook.screen.dashboard.account.myticket.create

import androidx.lifecycle.MutableLiveData
import com.stayhook.base.BaseRepository
import com.stayhook.base.BaseResponse
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.model.request.AddTicketScheduleMeetRequest
import com.stayhook.model.request.TicketRequest
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.TicketDetailResponse
import com.stayhook.model.response.TicketResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import com.stayhook.util.MultiPartHelper
import com.stayhook.util.mLog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateTicketRepo : BaseRepository() {


    fun executeAddTicketScheduleMeet(
        request: AddTicketScheduleMeetRequest,
        responseLiveData: MutableLiveData<ApiResponse<SuccessErrorResponse>>
    ) {
        val call = apiService.addTicketScheduleMeet(request)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<SuccessErrorResponse> {
            override fun onResponse(
                call: Call<SuccessErrorResponse>,
                response: Response<SuccessErrorResponse>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<SuccessErrorResponse>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


    fun executeAddTicketComment(
        ticketId:Int,
        comment:String,
        responseLiveData: MutableLiveData<ApiResponse<SuccessErrorResponse>>
    ) {
        val call = apiService.addTicketComment(ticketId, comment)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<SuccessErrorResponse> {
            override fun onResponse(
                call: Call<SuccessErrorResponse>,
                response: Response<SuccessErrorResponse>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<SuccessErrorResponse>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }











    fun executeGetTicketDetail(
        ticketId: Int,
        responseLiveData: MutableLiveData<ApiResponse<BaseResponse<TicketDetailResponse>>>
    ) {
        val call = apiService.getTicketDetail(ticketId)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<BaseResponse<TicketDetailResponse>> {
            override fun onResponse(
                call: Call<BaseResponse<TicketDetailResponse>>,
                response: Response<BaseResponse<TicketDetailResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<BaseResponse<TicketDetailResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


    fun executeCreateTicket(
        req: TicketRequest,
        responseLiveData: MutableLiveData<ApiResponse<SuccessErrorResponse>>
    ) {

        val call = apiService.createTickets(
           MultiPartHelper.getRequestBody(req.property_id.toString()),
            MultiPartHelper.getRequestBody(req.issue_type.toString()),
            MultiPartHelper.getRequestBody(req.issue.toString()),
            MultiPartHelper.getRequestBody(req.issue_details.toString()),
            MultiPartHelper.convertImagesToMultiPart(req.upload_document,"upload_document")
            )
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<SuccessErrorResponse> {
            override fun onResponse(
                call: Call<SuccessErrorResponse>,
                response: Response<SuccessErrorResponse>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<SuccessErrorResponse>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


    fun executeGetTickets(
        status: String,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<TicketResponse>>>
    ) {
        val call = apiService.getMyTickets(status)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<TicketResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<TicketResponse>>,
                response: Response<CollectionBaseResponse<TicketResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<TicketResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }


    fun executeGetIssueType(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>
    ) {
        val call = apiService.getIssueType()
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<StateCityResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<StateCityResponse>>,
                response: Response<CollectionBaseResponse<StateCityResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<StateCityResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }



    fun executeGetIssueSubType(
        issueId:Int,
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>
    ) {
        val call = apiService.getIssuesSubType(issueId)
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<StateCityResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<StateCityResponse>>,
                response: Response<CollectionBaseResponse<StateCityResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<StateCityResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }




    fun executeGetTicketProperty(
        responseLiveData: MutableLiveData<ApiResponse<CollectionBaseResponse<StateCityResponse>>>
    ) {
        val call = apiService.getTicketProperty()
        responseLiveData.postValue(ApiResponse.loading())
        call.enqueue(object : Callback<CollectionBaseResponse<StateCityResponse>> {
            override fun onResponse(
                call: Call<CollectionBaseResponse<StateCityResponse>>,
                response: Response<CollectionBaseResponse<StateCityResponse>>
            ) {
                try {
                    if (response.body()?.success!!) {
                        responseLiveData.postValue(ApiResponse.success(response.body()!!))
                    } else {
                        responseLiveData.postValue(ApiResponse.error(Throwable(response.body()?.message!!)))
                    }
                } catch (e: Exception) {
                    mLog("CreateTicketRepo  onFailure: ${e.message}")
                    responseLiveData.postValue(ApiResponse.error(Throwable(e.message)))
                }
            }

            override fun onFailure(
                call: Call<CollectionBaseResponse<StateCityResponse>>,
                t: Throwable
            ) {
                if (t.message.equals("Software caused connection abort")) {
                    responseLiveData.postValue(ApiResponse.error(Throwable(Constants.NetworkConstant.CONNECTION_LOST)))
                } else {
                    mLog("CreateTicketRepo  onFailure: ${t.message}")
                    responseLiveData.postValue(ApiResponse.error(t))
                }
            }

        })
    }
}