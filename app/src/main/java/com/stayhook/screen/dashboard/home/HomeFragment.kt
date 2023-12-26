package com.stayhook.screen.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.NearbyLocationItemAdapter
import com.stayhook.adapter.RecentlyAddedItemAdapter
import com.stayhook.adapter.RecommendationItemAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentHomeBinding
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.Constants.NetworkConstant.Companion.NO_INTERNET_AVAILABLE
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.Utility.isConnectionAvailable
import com.stayhook.util.mLog
import org.koin.core.component.inject

class HomeFragment : BaseFragment(), OnItemsClickListener {

    private val homeViewModel: HomeViewModel by inject()
    private var recommendationList = mutableListOf<RecommendData>()
    private var recentlyAddedDataList = mutableListOf<RecommendData>()
    private var nearbyDataList = mutableListOf<RecommendData>()
    private lateinit var homeBinding: FragmentHomeBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        homeBinding = binding as FragmentHomeBinding
        showTab()
        homeViewModel.fragmentHome = this@HomeFragment
        homeBinding.homeViewModel = homeViewModel

        homeBinding.tvHomeLocation.apply {
            val add = homeViewModel.getCurrentAddress()
            text = add
            mLog(add!!)
        }


        hitHomeApi()

    }

    private fun setAdapters() {
        homeBinding.apply {
            rvRecommendationItems.apply {
                layoutManager =
                    LinearLayoutManager(
                        baseActivity.baseContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = RecommendationItemAdapter(
                    recommendationList,
                    baseActivity.baseContext,
                    this@HomeFragment
                )
            }
            rvRecentlyAddedHome.apply {
                layoutManager =
                    LinearLayoutManager(
                        baseActivity.baseContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = RecentlyAddedItemAdapter(
                    recentlyAddedDataList,
                    baseActivity.baseContext,
                    this@HomeFragment
                )
            }
            homeBinding.rvNearByLocationItems.apply {
                layoutManager =
                    LinearLayoutManager(
                        baseActivity.baseContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter =
                    NearbyLocationItemAdapter(
                        nearbyDataList,
                        baseActivity.baseContext,
                        this@HomeFragment
                    )
            }
        }

    }

    private fun hitHomeApi() {
        if (!isConnectionAvailable()) {
            showErrorMessage(requireActivity(), NO_INTERNET_AVAILABLE)
            return
        }
        homeViewModel.hitHomePageApi()
        homeViewModel.getHomeResponse().observe(requireActivity(), homeResponseObserver)

    }

    private val homeResponseObserver: Observer<ApiResponse<HomeResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    recommendationList = it.data?.recommend_data as MutableList<RecommendData>
                    recentlyAddedDataList = it.data.recently_data as MutableList<RecommendData>
                    nearbyDataList = it.data.nearby_data as MutableList<RecommendData>
                    setAdapters()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(requireActivity(), it.error?.message.toString())
                }
            }
        }
    }

    override fun onCLickItems(model: RecommendData) {
        mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID, model.id.toString())
        launchActivity(RecommendationDetailActivity::class.java)
    }

}