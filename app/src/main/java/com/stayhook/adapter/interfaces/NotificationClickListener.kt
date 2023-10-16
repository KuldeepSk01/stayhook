package com.stayhook.adapter.interfaces

import com.stayhook.model.Notification

interface NotificationClickListener {
    fun onNotificationClick(model: Notification)
}