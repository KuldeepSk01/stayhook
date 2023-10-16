package com.stayhook.screen.notification

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.NotificationAdapter
import com.stayhook.adapter.interfaces.NotificationClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentNotificationBinding
import com.stayhook.model.Notification

class NotificationFragment : BaseFragment(), NotificationClickListener {
    private lateinit var nBinding: FragmentNotificationBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_notification
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        nBinding = binding as FragmentNotificationBinding
        nBinding.apply {
            toolbarNotification.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.title_notifications)
            }

            rvNotification.itemAnimator = DefaultItemAnimator()
            rvNotification.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            rvNotification.adapter = NotificationAdapter(getList(),requireContext(),this@NotificationFragment)

        }

    }


    private fun getList(): MutableList<Notification> {
        val list = mutableListOf<Notification>()
        list.add(
            Notification(
                R.drawable.ic_notify_1,
                getString(R.string.there_are_good_deal_text),
                getString(R.string.lots_of_great_deals_around_new_york_that_you_should_check_out_text)
            )
        )
        list.add(
            Notification(
                R.drawable.ic_notify_2,
                getString(R.string.there_are_good_deal_text),
                getString(R.string.lots_of_great_deals_around_new_york_that_you_should_check_out_text)
            )
        )
        list.add(
            Notification(
                R.drawable.ic_notify_3,
                getString(R.string.there_are_good_deal_text),
                getString(R.string.lots_of_great_deals_around_new_york_that_you_should_check_out_text)
            )
        )
        list.add(
            Notification(
                R.drawable.ic_notify_2,
                getString(R.string.there_are_good_deal_text),
                getString(R.string.lots_of_great_deals_around_new_york_that_you_should_check_out_text)
            )
        )
        list.add(
            Notification(
                R.drawable.ic_notify_1,
                getString(R.string.there_are_good_deal_text),
                getString(R.string.lots_of_great_deals_around_new_york_that_you_should_check_out_text)
            )
        )
        return list

    }

    override fun onNotificationClick(model: Notification) {
        Log.d("notificationFragment", "onNotificationClick: model $model")
    }


}