package com.stayhook.base

import com.stayhook.network.ApiService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseRepository:KoinComponent{
    val apiService : ApiService by inject()


}