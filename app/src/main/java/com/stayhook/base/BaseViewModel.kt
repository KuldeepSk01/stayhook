package com.stayhook.base

import androidx.lifecycle.ViewModel
import com.stayhook.validation.Validator
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel : ViewModel(),KoinComponent {
    val validator: Validator by inject()

}