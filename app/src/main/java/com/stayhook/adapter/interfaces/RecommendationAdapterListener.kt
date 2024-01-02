package com.stayhook.adapter.interfaces

import com.stayhook.model.response.home.RecommendData

interface RecommendationAdapterListener {
    fun onClickItems(model: RecommendData)
    fun addFavoriteItem(model: RecommendData)
}