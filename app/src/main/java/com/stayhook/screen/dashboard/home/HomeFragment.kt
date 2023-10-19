package com.stayhook.screen.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import com.stayhook.screen.notification.NotificationFragment
import com.stayhook.util.Constants
import org.koin.core.component.inject

class HomeFragment : BaseFragment(), OnItemsClickListener {

    private val homeViewModel: HomeViewModel by inject()
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
        showTab()
        mainActivity= requireActivity() as MainActivity
        mainActivity.setBottomStyle(1)
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

        homeBinding.tvSeeAllTXT.setOnClickListener {
            replaceFrag(SeeAllItemsFragment())
            hideTab()
        }
        homeBinding.tvSeeAllNearbyLocation.setOnClickListener {
            replaceFrag(SeeAllItemsFragment())
            hideTab()

        }
        homeBinding.tvRecentAddSeeAll.setOnClickListener {
            replaceFrag(SeeAllItemsFragment())
            hideTab()
        }
        homeBinding.llcHouseHome.setOnClickListener {
            launchActivityWithB(getString(R.string.house))
            hideTab()
        }
        homeBinding.llcPrivateRoomHome.setOnClickListener {
            launchActivityWithB(getString(R.string.private_room))
            hideTab()
        }
        homeBinding.llcSharedRoomHome.setOnClickListener {
            launchActivityWithB(getString(R.string.shared_room))
            hideTab()
        }
        homeBinding.ivNotificationIconHome.setOnClickListener {
            replaceFrag(NotificationFragment())
            hideTab()
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

    private fun replaceFrag(f: Fragment) {
        replaceFragment(
            R.id.flMainContainer,
            f,
            HomeFragment().javaClass.simpleName
        )
    }


    private fun launchActivityWithB(title: String) {
        val b = Bundle()
        b.putString(Constants.DefaultConstants.STRING, title)
        launchActivity(HomeRoomTypeActivity::class.java,Constants.DefaultConstants.BUNDLE,b)
    }


}