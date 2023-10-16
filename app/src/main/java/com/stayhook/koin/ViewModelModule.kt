package com.stayhook.koin

import com.stayhook.base.BaseViewModel
import com.stayhook.screen.dashboard.home.HomeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        BaseViewModel()
    }
    single {
        HomeViewModel(get())
    }
}