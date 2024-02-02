package com.stayhook.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.databinding.ItemReviewLayoutBinding
import com.stayhook.databinding.ItemTicketImagesLayoutBinding
import com.stayhook.model.response.getpopertydetail.PropertyReview
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.mLog

class ReviewsAdapter(private val list: MutableList<PropertyReview>, val context: Context) :
    RecyclerView.Adapter<ReviewsAdapter.ReviewVM>() {
    inner class ReviewVM(val b: ItemReviewLayoutBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewVM {
        val view = DataBindingUtil.inflate<ItemReviewLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_review_layout, parent, false
        )

        return ReviewVM(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ReviewVM, position: Int) {
        val model = list[position]

        holder.b.apply {
            Glide.with(context).load(model.image).into(ivReviewsProfile)
            tvReviewsTitle.text = model.title
            tvReviewsDate.text  = model.created_at
            tvReviewsName.text = model.name
            tvReviewsDescription.text  = model.review
            when(model.rating){
                0->{
                    tvReviewsRatings.text = context.getString(R.string.zero_star)
                }
                1->{
                    tvReviewsRatings.text = context.getString(R.string.one_star)
                }
                2->{
                    tvReviewsRatings.text = context.getString(R.string.two_star)

                }
                3->{
                    tvReviewsRatings.text = context.getString(R.string.three_star)

                }
                4->{
                    tvReviewsRatings.text = context.getString(R.string.four_star)
                }
                5->{
                    tvReviewsRatings.text = context.getString(R.string.five_star)
                }
            }

        }
    }
}