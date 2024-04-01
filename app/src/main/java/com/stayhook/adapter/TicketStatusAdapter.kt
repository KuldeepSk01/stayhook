package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.LayoutTicketCommentStatus1Binding
import com.stayhook.model.TicketImagesModel
import com.stayhook.model.response.TicketImage
import com.stayhook.model.response.TicketJourney

class TicketStatusAdapter(private val list: MutableList<TicketJourney>, val context: Context,
                          private val imageListener: TicketImagesAdapter.OnImageClickListener
) :
    RecyclerView.Adapter<TicketStatusAdapter.TicketStatusVM>() {
   /* private val LAYOUT_TOKEN_COLLECTED: Int = 1
    private val LAYOUT_VISIT: Int = 2
    private val LAYOUT_AGREEMENT: Int = 3*/


    inner class TicketStatusVM(val b: LayoutTicketCommentStatus1Binding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketStatusVM {
        return TicketStatusVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_ticket_comment_status1,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TicketStatusVM, position: Int) {
        val model = list[position]
        holder.b.apply {
          //  setTicketImage(model.ticket_journey as MutableList<TicketImage>)

            val list = mutableListOf<TicketImagesModel>()
            if (!model.ticket_image.isNullOrEmpty()){
                model.ticket_image.forEach {
                    list.add(TicketImagesModel("", it.image))
                }
                rvItemTicket.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = TicketImagesAdapter(list, context, false,imageListener)
                }
            }

            tvTokenCollectedStatus.text = model.status
            tvMeetingTC.text = model.meeting_date
            tvMeetingTimeTC.text = model.meeting_time
            tvCommentsTimeTC.text = if (model.remark.isNullOrEmpty()) ".........." else model.remark
            tvAddedBy.text = String.format("%s ( %s )",model.added_by_name, model.added_by_type)
            tvCreatedDate.text = model.created_at
            if (model.meeting_date.isEmpty()) {
                llcScheduleMeetingStatusTC.visibility = View.GONE
            } else {
                llcScheduleMeetingStatusTC.visibility = View.VISIBLE
            }
        }
    }

    /*
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val myHolder:RecyclerView.ViewHolder = when (viewType) {
                LAYOUT_TOKEN_COLLECTED -> {
                    val binding = LayoutTokenTokenCollectedBinding.inflate (
                        LayoutInflater.from ( parent.context),parent,false )
                    TokenCollectedVM(binding)
                }
                LAYOUT_VISIT -> {
                    val binding = LayoutTokenVisitBinding.inflate (
                        LayoutInflater.from ( parent.context),parent,false )
                    TokenVisitVM(binding)
                }
                LAYOUT_AGREEMENT -> {
                    val binding = LayoutTokenAgreemnetBinding.inflate (
                        LayoutInflater.from ( parent.context),parent,false )
                    LayoutTokenAgreemnetVM(binding)

                }

                else -> {
                    val binding = LayoutTokenTokenCollectedBinding.inflate (
                        LayoutInflater.from ( parent.context),parent,false )
                    TokenCollectedVM(binding)
                }
            }

            return myHolder
        }
    */

    override fun getItemCount() = list.size

    /*
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = list[position]
            when (holder.itemViewType) {
                LAYOUT_TOKEN_COLLECTED -> {
                    val myHolder = holder as TokenCollectedVM
                    myHolder.b.apply {
                        clTokenCollectedLayout.visibility=View.VISIBLE
                        tvTokenCollectedStatus.text = data.status
                        tvCreatedTimeTC.text = data.created_at
                        tvRemarkStatusTC.text = data.remark
                    }

                }
                LAYOUT_VISIT-> {
                    val myHolder = holder as TokenVisitVM
                    myHolder.b.apply {
                        tvVisitStatus.text = data.status
                        tvRemarkVisitFieldName.text = data.fieldPerson
                        tvVisitDate.text= data.visitDate
                        tvVisitTime.text = data.visitTime
                        tvRemarkVisitName.text = data.remark
                    }
                }
                LAYOUT_AGREEMENT -> {
                    val myHolder = holder as LayoutTokenAgreemnetVM
                    myHolder.b.apply {
                        tvAgreementStatus.text = data.status
                        tvRemarkAgreement.text = data.remark
                        tvCreatedTimeAgreement.text = data.created_at
                        Glide.with(context).load(data.agreement).placeholder(R.drawable.default_image).into(ivAgreement)
                        Glide.with(context).load(data.police_verification).placeholder(
                            R.drawable.default_image).into(ivPoliceVerification)
                        Glide.with(context).load(data.signatured_agreement).placeholder(
                            R.drawable.default_image).into(ivSignAgreement)

                    }
                }

            }
        }
    */

    /*  override fun getItemViewType(position: Int): Int {
          val data=list[position]
          return when(data.status){
              "Agreement"->{
                  LAYOUT_AGREEMENT
              }

              "Visits"->{
                  LAYOUT_VISIT
              }

              else->{
                  LAYOUT_TOKEN_COLLECTED
              }
          }
      }*/

    /*

        class TokenCollectedVM(val b: LayoutTokenTokenCollectedBinding) : RecyclerView.ViewHolder(b.root)

        class TokenVisitVM(val b: LayoutTokenVisitBinding) : RecyclerView.ViewHolder(b.root)
        class LayoutTokenAgreemnetVM(val b: LayoutTokenAgreemnetBinding) : RecyclerView.ViewHolder(b.root)
    */


}