package com.stayhook.screen.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SearchAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentHomeRoomTypeBinding
import com.stayhook.model.Recommendation
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import com.stayhook.util.Constants
import org.koin.core.component.inject

class HomeRoomTypeFragment : BaseFragment(), OnItemsClickListener {


    private lateinit var bindingHRT: FragmentHomeRoomTypeBinding
    private val homeViewModel: HomeViewModel by inject()
    override fun onInitView(binding: ViewDataBinding, view:View) {
        bindingHRT = binding as FragmentHomeRoomTypeBinding
        hideTab()
        val title = arguments?.getString(Constants.DefaultConstants.STRING) as String
        val filterList = homeViewModel.getRecommendationList()
            .filter { recommendation -> recommendation.name == title }
        bindingHRT.apply {
            toolBarHRT.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = title
            }

            rvHRT.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = SearchAdapter(filterList as MutableList, requireContext(),this@HomeRoomTypeFragment)
            }
        }

    }


    override fun getLayoutId() = R.layout.fragment_home_room_type
    override fun onCLickItems(model: Recommendation) {
        val b = Bundle()
        val rdFragment = RecommendationDetailFragment()
        b.putSerializable("recommendationDetail", model)
        replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
        Log.d("TAG", "onCLickItems: model data is $model")
      //  hideTab()

    }

}