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
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import com.stayhook.screen.notification.NotificationFragment
import org.koin.core.component.inject

class HomeFragment : BaseFragment(), OnItemsClickListener {

    private val homeViewModel: HomeViewModel by inject()
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
                adapter = RecentlyAddedItemAdapter(it, requireContext())

            }
        }

        homeViewModel.getNearByLocationList().let {
            homeBinding.rvNearByLocationItems.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = NearbyLocationItemAdapter(it, requireContext())
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
            replaceFrag(SeeAllItemsFragment())
            hideTab()
        }
        homeBinding.llcPrivateRoomHome.setOnClickListener {
            replaceFrag(SeeAllItemsFragment())
            hideTab()
        }
        homeBinding.llcSharedRoomHome.setOnClickListener {
            replaceFrag(SeeAllItemsFragment())
            hideTab()
        }
        homeBinding.ivNotificationIconHome.setOnClickListener {
            replaceFrag(NotificationFragment())
            hideTab()
        }


    }

    private fun getRecommendationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                "https://images.oyoroomscdn.com/uploads/hotel_image/168652/c4f2cc00b8fb02ba.jpg",
                "Abok hook",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://i0.wp.com/stanzaliving.wpcomstaging.com/wp-content/uploads/2022/04/d601b-hostels-vs-pgs-min.jpg?fit=4000%2C3000&ssl=1",
                "Kuldeep's hotel",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://content3.jdmagicbox.com/comp/chennai/g8/044pxx44.xx44.180125140725.t6g8/catalogue/victorias-in-ladies-and-pg-hostel-sirucheri-chennai-hostel-for-girl-students-c72xw2x314.jpg?clr=",
                "Raju's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://www.thehivehostels.com/uploads/images/1658301040_7796f3aa4d7819a2f5d5.jpeg",
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        return list
    }

    private fun getNearByLocationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                "https://images.oyoroomscdn.com/uploads/hotel_image/168652/c4f2cc00b8fb02ba.jpg",
                "Abok hook",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://i0.wp.com/stanzaliving.wpcomstaging.com/wp-content/uploads/2022/04/d601b-hostels-vs-pgs-min.jpg?fit=4000%2C3000&ssl=1",
                "Kuldeep's hotel",
                "single Room",
                "Greater Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://content3.jdmagicbox.com/comp/chennai/g8/044pxx44.xx44.180125140725.t6g8/catalogue/victorias-in-ladies-and-pg-hostel-sirucheri-chennai-hostel-for-girl-students-c72xw2x314.jpg?clr=",
                "Raju's",
                "single Room",
                "Noida,Sector 4",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://www.thehivehostels.com/uploads/images/1658301040_7796f3aa4d7819a2f5d5.jpeg",
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        return list
    }

    override fun onCLickItems(model: Recommendation) {
        val b = Bundle()
        val rdFragment = RecommendationDetailFragment()
        b.putSerializable("recommendationDetail", model)
        replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
        Log.d("TAG", "onCLickItems: model data is $model")
        hideTab()
    }

    private fun replaceFrag(f:Fragment){
        replaceFragment(
            R.id.flMainContainer,
            f,
            HomeFragment().javaClass.simpleName
        )
    }


}