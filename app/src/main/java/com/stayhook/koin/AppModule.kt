package com.stayhook.koin

import android.content.Context
import com.stayhook.application.StayHookApplication
import com.stayhook.preference.PREFERENCE_NAME
import com.stayhook.preference.PreferenceHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single {
        PreferenceHelper(
            (androidApplication() as StayHookApplication).getSharedPreferences(
                PREFERENCE_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}