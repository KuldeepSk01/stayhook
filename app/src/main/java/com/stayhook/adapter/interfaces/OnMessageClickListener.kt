package com.stayhook.adapter.interfaces

import com.stayhook.model.Message

interface OnMessageClickListener {
    fun onClickMessage(model: Message)
}