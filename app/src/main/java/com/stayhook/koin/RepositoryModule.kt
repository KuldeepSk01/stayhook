package com.stayhook.koin

import com.stayhook.base.BaseRepositopry
import com.stayhook.screen.dashboard.home.HomeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        BaseRepositopry()
    }
    single {
        HomeRepository()
    }
}