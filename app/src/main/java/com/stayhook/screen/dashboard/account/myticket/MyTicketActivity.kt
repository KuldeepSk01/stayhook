package com.stayhook.screen.dashboard.account.myticket

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.Api
import com.google.android.material.tabs.TabLayout
import com.stayhook.R
import com.stayhook.adapter.MyTicketAdapter
import com.stayhook.adapter.interfaces.MyTicketAdapterListener
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityMyTicketBinding
import com.stayhook.model.Ticket
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.TicketResponse
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.account.myticket.create.CreateTicketActivity
import com.stayhook.screen.dashboard.account.myticket.create.CreateTicketViewModel
import com.stayhook.screen.dashboard.account.myticket.detail.OpenTicketDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.mToast
import com.stayhook.util.serializable
import org.koin.core.component.inject

class MyTicketActivity : BaseActivity(), MyTicketAdapterListener, TabLayout.OnTabSelectedListener {

    private lateinit var sBinding: ActivityMyTicketBinding
    private val mViewModel: CreateTicketViewModel by inject()
    private var status  = "Open"

    override val layoutId: Int
        get() = R.layout.activity_my_ticket

    override fun onViewInit(binding: ViewDataBinding?) {
        sBinding = binding as ActivityMyTicketBinding

        sBinding.apply {
            tvTicketTitle.text =getString(R.string.my_ticket_text)
            ivBackBtn.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            tvAddTicketBtn.setOnClickListener {
                launchActivity(CreateTicketActivity::class.java)
            }
            tabLayoutMyTicket.addOnTabSelectedListener(this@MyTicketActivity)

        }
    }

    private val ticketResponseObserver =
        Observer<ApiResponse<CollectionBaseResponse<TicketResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()

                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val list = it.data?.data as MutableList<TicketResponse>
                    showTicket(list)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@MyTicketActivity, it.error?.message.toString())
                }
            }
        }

    private fun showTicket(list: MutableList<TicketResponse>) {
        sBinding.tvTicketTitle.text =String.format("%s %s",getString(R.string.my_ticket_text),"(${list.size})")

        sBinding.rvMyTickets.apply {
            layoutManager =
                LinearLayoutManager(this@MyTicketActivity, LinearLayoutManager.VERTICAL, false)
            adapter =
                MyTicketAdapter(list, this@MyTicketActivity, this@MyTicketActivity)
        }
    }

/*
    private fun getTicketsList(): MutableList<Ticket> {
        val list = mutableListOf<Ticket>()
        list.add(
            Ticket(
                1221,
                "B4,1004,Panchseel Greens-II,Sector 16,Noida UP",
                "Gajjendra Singh",
                "Wooden",
                "General",
                "Need the flooring to be maintained",
                "","Open"
            )
        )
        list.add(
            Ticket(
                1221,
                "B4,1004,Panchseel Greens-II,Sector 16,Noida UP",
                "Rahul Singh",
                "Wooden",
                "General",
                "Need the flooring to be maintained","","Open"
            )
        )
        list.add(
            Ticket(
                1221,
                "B4,1004,Panchseel Greens-II,Sector 16,Noida UP",
                "Kuldeep Singh",
                "AC",
                "General",
                "Need to be Repair","","In Process"
            )
        )
        list.add(
            Ticket(
                1221,
                "B4,1004,Panchseel Greens-II,Sector 16,Noida UP",
                "Kundan ",
                "Electricity",
                "General",
                "Need to be maintained","","Closed"
            )
        )
        return list
    }
*/



    override fun onTicketClick(model: TicketResponse) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(OpenTicketActivity::class.java,Constants.DefaultConstants.BUNDLE,b)

       /* when(model.status){
            "Open"->{
                launchActivity(OpenTicketActivity::class.java,Constants.DefaultConstants.BUNDLE,b)
            }
            "In Process"->{

            }
            "closed"->{

            }
        }*/
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        status = tab?.text.toString()
        hitTicketsApi(status)
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    private fun hitTicketsApi(status:String){
        mViewModel.hitMyTicketApi(status)
        mViewModel.getTicketResponse().observe(this@MyTicketActivity,ticketResponseObserver)
    }

    override fun onResume() {
        super.onResume()
        hitTicketsApi(status)
    }
}