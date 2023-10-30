package com.stayhook.screen.dashboard.search

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.stayhook.R
import com.stayhook.adapter.SearchAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSearchBinding
import com.stayhook.model.Recommendation
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.HomeFragment
import com.stayhook.screen.dashboard.home.HomeViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import org.koin.core.component.inject


class SearchFragment : BaseFragment(), OnMapReadyCallback, OnItemsClickListener {
    private lateinit var sBinding: FragmentSearchBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var mGoogleMap: GoogleMap
    private val homeViewModel : HomeViewModel by inject()

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sBinding = binding as FragmentSearchBinding
        showTab()
        mainActivity = requireActivity() as MainActivity
        mainActivity.setBottomStyle(2)
        sBinding.apply {
            ivSearchFilterBtn.setOnClickListener {
                replaceFragment(
                    R.id.flMainContainer,
                    SearchFilterFragment(),
                    SearchFragment::javaClass.name
                )
                hideTab()
            }

            val searchMap =
                childFragmentManager.findFragmentById(R.id.searchMap) as SupportMapFragment
            searchMap.getMapAsync(this@SearchFragment)
            // searchMap.getMapAsync(this@SearchFragment)

            rvSearchFragment.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter =
                    SearchAdapter(homeViewModel.getRecommendationList(), requireContext(), this@SearchFragment)
            }

        }

    }

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0
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