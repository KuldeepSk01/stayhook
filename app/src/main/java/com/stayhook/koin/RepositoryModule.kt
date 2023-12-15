package com.stayhook.koin

import com.stayhook.base.BaseRepository
import com.stayhook.screen.dashboard.account.AccountRepo
import com.stayhook.screen.dashboard.account.downloadprofile.KYCRepo
import com.stayhook.screen.dashboard.account.editprofile.EditProfileRepo
import com.stayhook.screen.dashboard.account.mybooking.MyBookingRepo
import com.stayhook.screen.dashboard.account.mypayment.MyPaymentRepo
import com.stayhook.screen.dashboard.account.myschedule.MyScheduledVisitRepo
import com.stayhook.screen.dashboard.account.myschedule.MyScheduledVisitStatusRepo
import com.stayhook.screen.dashboard.home.HomeRepository
import com.stayhook.screen.dashboard.home.SeeAllItemRepo
import com.stayhook.screen.dashboard.home.privateroom.PrivateRoomRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.PaymentRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.beds.BedsRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.RoomRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit.ScheduleAVisitRepo
import com.stayhook.screen.dashboard.home.sharedroom.SharedRoomRepo
import com.stayhook.screen.dashboard.search.SearchFilterViewModel
import com.stayhook.screen.dashboard.search.SearchRepo
import com.stayhook.screen.login.LoginRepo
import com.stayhook.screen.register.RegisterRepo
import com.stayhook.screen.verification.VerificationRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { BaseRepository() }
    single { HomeRepository() }
    single { LoginRepo() }
    single { RegisterRepo() }
    single { VerificationRepo() }
    single { SeeAllItemRepo() }
    single { PrivateRoomRepo() }
    single { SharedRoomRepo() }
    single { SearchRepo() }


    single { RecommendationRepo() }
    single { RoomRepo() }
    single { BedsRepo() }
    single { PaymentRepo() }
    single { MyBookingRepo() }
    single { MyScheduledVisitRepo() }
    single { MyScheduledVisitStatusRepo() }
    single { MyPaymentRepo() }
    single { ScheduleAVisitRepo() }
    single { AccountRepo() }
    single { EditProfileRepo() }
    single { KYCRepo() }


}