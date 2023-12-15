package com.stayhook.adapter.interfaces

import com.stayhook.model.response.MyPaymentsResponse

interface  OnPaymentListener {
    fun onPayClickListener(model:MyPaymentsResponse)
}