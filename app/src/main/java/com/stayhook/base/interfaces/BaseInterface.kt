package com.stayhook.base.interfaces

import androidx.databinding.ViewDataBinding

interface BaseInterface {
    val layoutId:Int
    fun onViewInit(binding : ViewDataBinding?)
}