package com.stayhook.screen.dashboard.account.myticket

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MyTicketAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentMyTicketBinding
import com.stayhook.model.Ticket
import com.stayhook.screen.dashboard.account.myticket.create.CreateTicketFragment

class MyTicketFragment : BaseFragment() {

    private lateinit var sBinding: FragmentMyTicketBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_my_ticket
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sBinding = binding as FragmentMyTicketBinding

        sBinding.apply {
            toolbarMyTicket.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.my_ticket_text)
            }

            rvMyTickets.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = MyTicketAdapter(getTicketsList(), requireContext())
            }

            btnNewTicket.setOnClickListener {
                replaceFragment(
                    R.id.flMainContainer,
                    CreateTicketFragment(),
                    MyTicketFragment::javaClass.name
                )
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


}