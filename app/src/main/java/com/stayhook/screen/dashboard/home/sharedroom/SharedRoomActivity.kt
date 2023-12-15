package com.stayhook.screen.dashboard.home.sharedroom

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SearchAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.ActivityHomeRoomTypeBinding
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject

class SharedRoomActivity : BaseFragment(), OnItemsClickListener {


    private lateinit var bindingHRT: ActivityHomeRoomTypeBinding
    private val seeAllItemVM: SharedRoomViewModel by inject()
    private val propertySet = mutableSetOf<RecommendData>()
    private val propertyList = mutableListOf<RecommendData>()
    private var currentItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var pageNumber: Int = 1
    private lateinit var propertyTypes: String

    override fun getLayoutId() = R.layout.activity_home_room_type

    override fun onInitView(binding: ViewDataBinding, view: View) {
        bindingHRT = binding as ActivityHomeRoomTypeBinding
        propertyTypes = getString(R.string.shared_room)

        propertySet.clear()
        propertyList.clear()
        pageNumber = 1
        totalItemCount = 0
        currentItemCount = 0
        getAllList(pageNumber, propertyTypes)


        bindingHRT.apply {
            toolBarHRT.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = propertyTypes
            }

            tvLoadMoreBtn.setOnClickListener {
                pageNumber++
                getAllList(pageNumber, propertyTypes)
            }
            /* rvHRT.apply {
                 itemAnimator = DefaultItemAnimator()
                 layoutManager =
                     LinearLayoutManager(
                         this@HomeRoomTypeActivity,
                         LinearLayoutManager.VERTICAL,
                         false
                     )
                   adapter = SearchAdapter(
                       filterList as MutableList,
                       baseActivity.baseContext,
                       this@HomeRoomTypeFragment
                   )
             }*/
        }
    }


    override fun onCLickItems(model: RecommendData) {
        mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID, model.id.toString())
        launchActivity(RecommendationDetailActivity::class.java)

    }

    private fun getAllList(pageNumber: Int, propertyT: String) {
        val req = GetPropertyRequest().apply {
            page = pageNumber
            propertyType = propertyT
        }
        seeAllItemVM.hitPropertyApi(req)
        seeAllItemVM.getPropertyResponse()
            .observe(this@SharedRoomActivity, propertyResponseObserver)

    }

    private val propertyResponseObserver: Observer<ApiResponse<GetPropertyBaseResponse>?> by lazy {
        Observer {
            when (it?.status!!) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    propertyList.clear()
                    totalItemCount = it.data?.count!!
                    bindingHRT.toolBarHRT.tvToolBarTitle.text =
                        String.format("%s %s", propertyTypes, "($totalItemCount)")
                    setList(it.data.data as MutableList<RecommendData>)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        requireActivity(),
                        it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setList(list: MutableList<RecommendData>) {
        propertySet.addAll(list)
        currentItemCount = propertySet.size
        propertyList.addAll(propertySet)


        bindingHRT.apply {
            rvHRT.apply {
                val lm = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
                itemAnimator = DefaultItemAnimator()
                layoutManager = lm
                adapter = SearchAdapter(
                    propertyList,
                    requireContext(),
                    this@SharedRoomActivity
                )
            }

            Log.d("TAG", "Shared ROOM: PageNo $pageNumber")
            Log.d("TAG", "Shared ROOM: totalIem $totalItemCount")
            Log.d("TAG", "Shared ROOM: currentItem $currentItemCount")

            if (totalItemCount == currentItemCount) {
                tvLoadMoreBtn.visibility = View.GONE
            } else {
                tvLoadMoreBtn.visibility = View.VISIBLE
            }
        }
    }

}