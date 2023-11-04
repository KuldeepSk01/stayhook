package com.stayhook.screen.dashboard.home.recommondationdetail

import android.app.ActionBar.LayoutParams
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.stayhook.R
import com.stayhook.adapter.AmenitiesAdapter
import com.stayhook.adapter.RecommendationDetailAdapter
import com.stayhook.adapter.interfaces.AdapterRoomTypes
import com.stayhook.base.BaseFragment
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.FragmentRecommendationDetailBinding
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getpopertydetail.PropertyImage
import com.stayhook.model.response.getpopertydetail.PropertyInventory
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.BookFragment
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.ScheduleVisitFragment
import com.stayhook.screen.dashboard.home.recommondationdetail.writeareview.WriteAReviewFragment
import com.stayhook.util.CustomDialogs.showErrorMessage
import org.koin.core.component.inject


class RecommendationDetailFragment : BaseFragment() {
    private lateinit var rdBinding: FragmentRecommendationDetailBinding
    private val mViewModel: RecommendationViewModel by inject()
    private lateinit var propertyDetail: GetPropertyDetail
    private var inventorylist = mutableListOf<PropertyInventory>()
    private var roomList = mutableListOf<PropertyRoom>()


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
        val propertyId = arguments?.getString ("propertyId")

        mViewModel.hitPropertyDetail(propertyId!!)
        mViewModel.getPropertyDetailResponse()
            .observe(this@RecommendationDetailFragment, propertyDetailReponseObserver)


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
                replaceFragment(
                    R.id.flMainContainer,
                    ScheduleVisitFragment(),
                    RecommendationDetailFragment::class.simpleName
                )
            }
            tvViewMoreBtn.setOnClickListener {
                binding.rvFeaturesAmenity.apply {
                    layoutParams.width = LayoutParams.MATCH_PARENT
                    layoutParams.height = LayoutParams.MATCH_PARENT
                }
                setInventoryAdapterWithSpanCount(5,inventorylist)
                tvViewMoreBtn.visibility = View.GONE
            }
            tvReviewsWriteAReview.setOnClickListener {
                replaceFragment(
                    R.id.flMainContainer,
                    WriteAReviewFragment(),
                    RecommendationDetailFragment::class.simpleName
                )
            }


            btnBookDetail.setOnClickListener {
                replaceFragment(
                    R.id.flMainContainer,
                    BookFragment(),
                    RecommendationDetailFragment::class.simpleName
                )
                Log.d(
                    "RDFragment",
                    "onInitView RDFragment: className ${this@RecommendationDetailFragment.javaClass.simpleName}"
                )
            }

        }


    }

    private fun setInventoryAdapterWithSpanCount(i: Int,inventoryList:MutableList<PropertyInventory>) {
        if (inventoryList.isEmpty()){
            rdBinding.tvViewMoreBtn.visibility = View.GONE
            rdBinding.rlBuildingAmenities.visibility = View.GONE
        }else {
            if(inventoryList.size > 4 ){
                rdBinding.rlBuildingAmenities.visibility = View.VISIBLE
                rdBinding.tvViewMoreBtn.visibility = View.VISIBLE
            }else{
                rdBinding.tvViewMoreBtn.visibility = View.GONE
            }
        }
        rdBinding.rvFeaturesAmenity.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), i)
            adapter = AmenitiesAdapter(inventoryList, requireContext())
        }
    }

    private val propertyDetailReponseObserver: Observer<ApiResponse<BaseResponse<GetPropertyDetail>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    propertyDetail = it.data?.data!!
                    setDetail(propertyDetail)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(requireActivity(), it.error?.message.toString())
                }
            }
        }
    }

    private fun setDetail(getPropertyDetail: GetPropertyDetail) {
        rdBinding.apply {
            getPropertyDetail.let {
                inventorylist = it.property_inventory as MutableList<PropertyInventory>
                roomList = it.property_room as MutableList<PropertyRoom>

                viewPagerRD.adapter = RecommendationDetailAdapter(
                    it.property_images!! as MutableList<PropertyImage>,
                    requireContext()
                )
                indicatorRD.setViewPager(viewPagerRD)
                tvDetailApartmentType.text = it.property_name
                tvDetailPageRatings.text = "${it.rating} (${it.total_review} Reviews)"
                tvDetailRoomType.text = it.property_type
                tvLocationDescription.text = it.area
                tvBeds.text = "${it.total_bed.toString()} Bed"
                tvBath.text = "${it.total_bath.toString()} Bath"
                tvSqrft.text = "${it.total_area}"
                tvAboutRD.text = it.about
                tvRItemCostDetail.text = "$${it.price}"
                setInventoryAdapterWithSpanCount(4,inventorylist)
                setRoomTypeAdapter(roomList)
            }



        }
    }

    private fun setRoomTypeAdapter(roomList: MutableList<PropertyRoom>) {
        rdBinding.rvRoomTypeRD.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = AdapterRoomTypes(roomList, requireContext())
        }
    }


}

