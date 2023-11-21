package com.stayhook.screen.dashboard.home

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityHomeRoomTypeBinding
import com.stayhook.model.response.home.RecommendData
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import org.koin.core.component.inject

class HomeRoomTypeActivity : BaseActivity(), OnItemsClickListener {


    private lateinit var bindingHRT: ActivityHomeRoomTypeBinding
    private val homeViewModel: HomeViewModel by inject()
    override val layoutId: Int
        get() = R.layout.activity_home_room_type

    override fun onViewInit(binding: ViewDataBinding?) {
        bindingHRT = binding as ActivityHomeRoomTypeBinding
        //  val title = arguments?.getString(Constants.DefaultConstants.STRING) as String
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
                /*  adapter = SearchAdapter(
                      filterList as MutableList,
                      baseActivity.baseContext,
                      this@HomeRoomTypeFragment
                  )*/
            }
        }
    }


    override fun onCLickItems(model: RecommendData) {
        mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID, model.id.toString())
        launchActivity(RecommendationDetailActivity::class.java)
        //launchActivity()
        /*  val b = Bundle()
          val rdFragment = RecommendationDetailActivity()
          b.putSerializable("recommendationDetail", model)
          replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
          Log.d("TAG", "onCLickItems: model data is $model")
          //  hideTab()*/

    }


}