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
import com.stayhook.util.Constants
import com.stayhook.util.serializable

class MyTicketActivity : BaseActivity(), MyTicketAdapterListener {

    private lateinit var sBinding: ActivityMyTicketBinding
    override val layoutId: Int
        get() = R.layout.activity_my_ticket

    override fun onViewInit(binding: ViewDataBinding?) {
        sBinding = binding as ActivityMyTicketBinding

        sBinding.apply {
            toolbarMyTicket.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.my_ticket_text)
            }

            rvMyTickets.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(this@MyTicketActivity, LinearLayoutManager.VERTICAL, false)
                adapter =
                    MyTicketAdapter(getTicketsList(), this@MyTicketActivity, this@MyTicketActivity)
            }

            btnNewTicket.setOnClickListener {
                launchActivity(CreateTicketActivity::class.java)

//                replaceFragment(
//                    R.id.flMainContainer,
//                    CreateTicketFragment(),
//                    MyTicketFragment::javaClass.name
//                )
            }

        }
    }


    private fun getTicketsList(): MutableList<Ticket> {
        val list = mutableListOf<Ticket>()
        list.add(
            Ticket(
                1221,
                "Booking maintenance",
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )
        list.add(
            Ticket(
                863,
                "Booking maintenance",
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )
        list.add(
            Ticket(
                6223,
                "Booking maintenance",
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )
        list.add(
            Ticket(
                298,
                "Booking maintenance",
                getString(R.string.lorem_ipsum_dolor_sit_amet_consectetur_adipiscing_elit_ut_aliquam_purus_sit_amet_luctus_venenatis_lectus_magna_fringilla_urna_porttitor_text)
            )
        )

        return list
    }

    override fun onTicketClick(model: Ticket) {
        //launch open ticket activity
        val b = Bundle()
        b.putSerializable(Constants.DefaultConstants.MODEL_DETAIL,model)
        launchActivity(OpenTicketActivity::class.java,Constants.DefaultConstants.BUNDLE,b)
    }


}