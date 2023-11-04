package com.stayhook.koin

import com.stayhook.base.BaseRepository
import com.stayhook.screen.dashboard.account.AccountRepo
import com.stayhook.screen.dashboard.account.editprofile.EditProfileRepo
import com.stayhook.screen.dashboard.home.HomeRepository
import com.stayhook.screen.dashboard.home.SeeAllItemRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationRepo
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationViewModel
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
        AccountRepo()
    }
    single {
        EditProfileRepo()
    }
}