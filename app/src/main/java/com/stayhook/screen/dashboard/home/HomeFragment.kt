package com.stayhook.screen.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.NearbyLocationItemAdapter
import com.stayhook.adapter.RecentlyAddedItemAdapter
import com.stayhook.adapter.RecommendationItemAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentHomeBinding
import com.stayhook.model.Recommendation
import com.stayhook.permissions.MyPermissions
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import com.stayhook.screen.notification.NotificationFragment
import com.stayhook.util.Constants
import org.koin.core.component.inject

class HomeFragment : BaseFragment(), OnItemsClickListener {

    private val homeViewModel: HomeViewModel by inject()

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
        homeBinding.homeViewModel=homeViewModel
        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)



        homeViewModel.getRecommendationList().let {
            homeBinding.rvRecommendationItems.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = RecommendationItemAdapter(
                    it,
                    requireContext(),
                    this@HomeFragment
                )

            }
            homeBinding.rvRecentlyAddedHome.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = RecentlyAddedItemAdapter(it, requireContext(), this@HomeFragment)

            }
            homeBinding.rvNearByLocationItems.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = NearbyLocationItemAdapter(it, requireContext(), this@HomeFragment)
            }
        }
    }

    override fun onCLickItems(model: Recommendation) {
        val b = Bundle()
        val rdFragment = RecommendationDetailFragment()
        b.putSerializable("recommendationDetail", model)
        replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
        Log.d("TAG", "onCLickItems: model data is $model")
        hideTab()
    }

}