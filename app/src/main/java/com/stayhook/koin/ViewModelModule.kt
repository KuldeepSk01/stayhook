package com.stayhook.koin

import com.stayhook.base.BaseViewModel
import com.stayhook.screen.dashboard.account.AccountViewModel
import com.stayhook.screen.dashboard.account.editprofile.EditProfileViewModel
import com.stayhook.screen.dashboard.account.mybooking.MyBookingViewModel
import com.stayhook.screen.dashboard.account.myschedule.MyScheduleVisitStatusViewModel
import com.stayhook.screen.dashboard.account.myschedule.MyScheduleVisitViewModel
import com.stayhook.screen.dashboard.home.HomeViewModel
import com.stayhook.screen.dashboard.home.SeeAllItemViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.PaymentViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.beds.BedsViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.RoomViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit.ScheduleAVisitViewModel
import com.stayhook.screen.login.LoginViewModel
import com.stayhook.screen.register.RegisterViewModel
import com.stayhook.screen.verification.VerificationViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single {
        BaseViewModel()
    }
    single {
        HomeViewModel(get())
    }
    single {
        LoginViewModel(get())
    }

    single {
        RegisterViewModel(get())
    }
    single {
        VerificationViewModel(get())
    }

    single {
        SeeAllItemViewModel(get())
    }

    single {
        RecommendationViewModel(get())
    }
    single {
        RoomViewModel(get())
    }

    single {
        BedsViewModel(get())
    }
    single {
        PaymentViewModel(get())
    }
    single {
        MyBookingViewModel(get())
    }
    single {
        MyScheduleVisitViewModel(get())
    }
    single {
        MyScheduleVisitStatusViewModel(get())
    }
    single {
        ScheduleAVisitViewModel(get())
    }
    single {
        AccountViewModel(get())
    }
    single {
        EditProfileViewModel(get())
    }
}