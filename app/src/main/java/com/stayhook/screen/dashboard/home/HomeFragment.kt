package com.stayhook.screen.dashboard.home

import android.os.Bundle
import android.util.Log
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
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import com.stayhook.util.CustomDialogs.showErrorMessage
import org.koin.core.component.inject

class HomeFragment : BaseFragment(), OnItemsClickListener {

    private val homeViewModel: HomeViewModel by inject()
    private var recommendationList = mutableListOf<RecommendData>()
    private var recentlyAddedDataList = mutableListOf<RecommendData>()
    private var nearbyDataList = mutableListOf<RecommendData>()

    // FusedLocationProviderClient - Main class for receiving location updates.
    // private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e.,
// how often you should receive updates, the priority, etc.
    //private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
// has a new Location
    // private lateinit var locationCallback: LocationCallback

    // This will store current location info
    // private var currentLocation: Location? = null
    private lateinit var mainActivity: MainActivity
    private val rDetailFragment: RecommendationDetailFragment by lazy {
        RecommendationDetailFragment()
    }

    private lateinit var homeBinding: FragmentHomeBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        homeBinding = binding as FragmentHomeBinding
        mainActivity = requireActivity() as MainActivity
        mainActivity.setBottomStyle(1)
        showTab()
        homeViewModel.fragmentHome = this@HomeFragment
        homeBinding.homeViewModel = homeViewModel
        hitHomeApi()
        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private fun setAdapters() {
        homeBinding.apply {
            rvRecommendationItems.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = RecommendationItemAdapter(
                    recommendationList,
                    requireContext(),
                    this@HomeFragment
                )
            }
            rvRecentlyAddedHome.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = RecentlyAddedItemAdapter(
                    recentlyAddedDataList,
                    requireContext(),
                    this@HomeFragment
                )
            }
            homeBinding.rvNearByLocationItems.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter =
                    NearbyLocationItemAdapter(nearbyDataList, requireContext(), this@HomeFragment)
            }

        }

    }

    private fun hitHomeApi() {
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
        val b = Bundle()
        val rdFragment = RecommendationDetailFragment()
        b.putString("propertyId", model.id.toString())
        replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
        Log.d("TAG", "onCLickItems: model data is $model")
        hideTab()
    }

}