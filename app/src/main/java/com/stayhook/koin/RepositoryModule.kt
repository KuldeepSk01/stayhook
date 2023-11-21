package com.stayhook.koin

import com.stayhook.base.BaseRepository
import com.stayhook.screen.dashboard.account.AccountRepo
import com.stayhook.screen.dashboard.account.editprofile.EditProfileRepo
import com.stayhook.screen.dashboard.account.mybooking.MyBookingRepo
import com.stayhook.screen.dashboard.account.myschedule.MyScheduledVisitRepo
import com.stayhook.screen.dashboard.account.myschedule.MyScheduledVisitStatusRepo
import com.stayhook.screen.dashboard.home.HomeRepository
import com.stayhook.screen.dashboard.home.SeeAllItemRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.PaymentRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.beds.BedsRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.RoomRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit.ScheduleAVisitRepo
import com.stayhook.screen.login.LoginRepo
import com.stayhook.screen.register.RegisterRepo
import com.stayhook.screen.verification.VerificationRepo
import org.koin.dsl.module

val repositoryModule = module {
    single {
        BaseRepository()
    }
    single {
        HomeRepository()
    }
    single {
        LoginRepo()
    }
    single {
        RegisterRepo()
    }
    single {
        VerificationRepo()
    }

    single {
        SeeAllItemRepo()
    }
    single {
        RecommendationRepo()
    }

    single {
        RoomRepo()
    }
    single {
        BedsRepo()
    }
    single {
        PaymentRepo()
    }

    single {
        MyBookingRepo()
    }
    single {
        MyScheduledVisitRepo()
    }
    single {
        MyScheduledVisitStatusRepo()
    }

    single {
        ScheduleAVisitRepo()
    }


    single {
        AccountRepo()
    }
    single {
        EditProfileRepo()
    }
}