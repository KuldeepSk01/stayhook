package com.stayhook.screen.dashboard.account.myticket

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MyTicketAdapter
import com.stayhook.adapter.interfaces.MyTicketAdapterListener
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityMyTicketBinding
import com.stayhook.model.Ticket
import com.stayhook.screen.dashboard.account.myticket.create.CreateTicketActivity
import com.stayhook.screen.dashboard.account.myticket.detail.OpenTicketDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.serializable

class MyTicketActivity : BaseActivity(), MyTicketAdapterListener {

    private lateinit var sBinding: ActivityMyTicketBinding
    override val layoutId: Int
        get() = R.layout.activity_my_ticket

    override fun onViewInit(binding: ViewDataBinding?) {
        sBinding = binding as ActivityMyTicketBinding

        sBinding.apply {
            tvTicketTitle.text =String.format("%s %s",getString(R.string.my_ticket_text),"(${getTicketsList().size})")
            ivBackBtn.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            tvAddTicketBtn.setOnClickListener {
                launchActivity(CreateTicketActivity::class.java)

            }

            rvMyTickets.apply {
                layoutManager =
                    LinearLayoutManager(this@MyTicketActivity, LinearLayoutManager.VERTICAL, false)
                adapter =
                    MyTicketAdapter(getTicketsList(), this@MyTicketActivity, this@MyTicketActivity)
            }


        }
    }


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



    override fun onTicketClick(model: Ticket) {
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        when(model.status){
            "Open"->{
                launchActivity(OpenTicketActivity::class.java,Constants.DefaultConstants.BUNDLE,b)
            }
            "In Process"->{}
            "closed"->{}
        }
    }


   /* override fun onTicketClick(model: Ticket) {
        //launch open ticket activity
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(OpenTicketActivity::class.java,Constants.DefaultConstants.BUNDLE,b)
    }*/


}