package com.stayhook.screen.dashboard.home

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SearchAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.FragmentHomeRoomTypeBinding
import com.stayhook.util.Constants
import org.koin.core.component.inject

class HomeRoomTypeActivity : BaseActivity() {

    private lateinit var bindingHRT: FragmentHomeRoomTypeBinding
    private val homeViewModel: HomeViewModel by inject()
    override fun onViewInit(binding: ViewDataBinding?) {
        bindingHRT = binding as FragmentHomeRoomTypeBinding
        val title = intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
            ?.getString(Constants.DefaultConstants.STRING)
        val filterList = homeViewModel.getRecommendationList()
            .filter { recommendation -> recommendation.name == title }
        bindingHRT.apply {
            toolBarHRT.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = title
            }

            rvHRT.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(
                        this@HomeRoomTypeActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = SearchAdapter(filterList as MutableList, this@HomeRoomTypeActivity)
            }
        }

    }

    override val layoutId: Int
        get() = R.layout.fragment_home_room_type


}