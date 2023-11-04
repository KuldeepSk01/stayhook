package com.stayhook.adapter.interfaces

import com.stayhook.model.response.home.RecommendData

interface OnItemsClickListener {
    fun onCLickItems(model: RecommendData)
}