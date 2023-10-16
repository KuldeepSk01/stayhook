package com.stayhook.screen.dashboard.home.recommondationdetail

import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.stayhook.R
import com.stayhook.adapter.RecommendationDetailAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentRecommendationDetailBinding
import com.stayhook.model.Recommendation
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.BookFragment
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.ScheduleVisitFragment
import com.stayhook.util.serializable

class RecommendationDetailFragment : BaseFragment() {
    private lateinit var rdBinding: FragmentRecommendationDetailBinding


    private val scheduleVisitFragment: ScheduleVisitFragment by lazy {
        ScheduleVisitFragment()
    }

    private val bookFragment: BookFragment by lazy {
        BookFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_recommendation_detail
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onInitView(binding: ViewDataBinding, view: View) {
        rdBinding = binding as FragmentRecommendationDetailBinding
        //setBackGround(rdBinding.llInteriorRD, rdBinding.tvInteriorRd)

        val model = arguments?.serializable<Recommendation>("recommendationDetail")
        Log.d("TAG", "onInitView rdetail:  $model")
        rdBinding.apply {
            toolbarRD.apply {
                ivToolBarBack.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.visibility = View.GONE
                ivToolBarRightIcon.visibility = View.VISIBLE
            }
            tvRDScheduleList.setOnClickListener {
                replaceFragment(R.id.flMainContainer,ScheduleVisitFragment(),RecommendationDetailFragment::class.simpleName)
            }
            tvReviewsWriteAReview.setOnClickListener {
             //   replaceFragment(R.id.flMainContainer,(),RecommendationDetailFragment::class.simpleName)
            }



/*
            rvRDOverviews.itemAnimator = DefaultItemAnimator()
            rvRDOverviews.layoutManager = GridLayoutManager(requireContext(), 4)
            llInteriorRD.setOnClickListener {
                setBackGround(it, rdBinding.tvInteriorRd)
            }
            llExteriorRD.setOnClickListener {
                setBackGround(it, rdBinding.tvExteriorRD)
            }
            llAreaLotRD.setOnClickListener {
                setBackGround(it, rdBinding.tvAreaLotRD)
            }*/




            btnBookDetail.setOnClickListener {
                replaceFragment(R.id.flMainContainer,BookFragment(),RecommendationDetailFragment::class.simpleName)
                Log.d(
                    "RDFragment",
                    "onInitView RDFragment: className ${this@RecommendationDetailFragment.javaClass.simpleName}"
                )
            }
            viewPagerRD.adapter = RecommendationDetailAdapter(model?.imagesUrls!!, requireContext())
            indicatorRD.setViewPager(viewPagerRD)
            tvDetailApartmentType.text = model.apartmentType
            tvDetailRoomType.text = model.name
            tvLocationDescription.text = model.location
        }


    }


    private fun setBackGround(vLayout: View, tv1: AppCompatTextView) {
      /*  rdBinding.apply {
            llInteriorRD.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            llExteriorRD.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            llAreaLotRD.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvInteriorRd.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvExteriorRD.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvAreaLotRD.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
        }
        tv1.setTextColor(resources.getColor(R.color.primary_color, null))
        (vLayout as LinearLayoutCompat).background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_bed_filter_bg, null)*/
    }


}

