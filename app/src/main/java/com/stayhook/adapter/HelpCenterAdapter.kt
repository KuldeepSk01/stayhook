package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.stayhook.R
import com.stayhook.databinding.ItemHelpCenterQuestionLayoutBinding
import com.stayhook.model.HelpCenter

class HelpCenterAdapter(val list: MutableList<HelpCenter>, val context: Context) :
    RecyclerView.Adapter<HelpCenterAdapter.HelpCenterVM>() {

    inner class HelpCenterVM(val b: ItemHelpCenterQuestionLayoutBinding) : ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpCenterVM {
        return HelpCenterVM(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_help_center_question_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HelpCenterVM, position: Int) {
        val model = list[position]
        holder.b.apply {
            tvQuestionHC.text = model.question
            tvQAnswerHC.text = model.answer
            val isExpand = model.isExpanded
            if (isExpand) {
                ivItemHCQBtn.setBackgroundResource(R.drawable.ic_hide_question)
                tvQAnswerHC.visibility = View.VISIBLE
            } else {
                ivItemHCQBtn.setBackgroundResource(R.drawable.ic_show_question)
                tvQAnswerHC.visibility = View.GONE
            }

            ivItemHCQBtn.setOnClickListener {
                model.isExpanded = !model.isExpanded
                if (isExpand) {
                    ivItemHCQBtn.setBackgroundResource(R.drawable.ic_hide_question)
                } else {
                    ivItemHCQBtn.setBackgroundResource(R.drawable.ic_show_question)
                }
                notifyDataSetChanged()
            }
        }
    }
}