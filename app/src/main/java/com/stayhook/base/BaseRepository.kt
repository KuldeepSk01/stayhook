package com.stayhook.base

import com.stayhook.network.ApiService
import com.stayhook.preference.reflection.ReflectionUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseRepository:KoinComponent{
    val apiService : ApiService by inject()
    val reflectionUtil : ReflectionUtil by inject()


}