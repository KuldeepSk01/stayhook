package com.stayhook.screen.dashboard.home.privateroom

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SearchAdapter
import com.stayhook.adapter.SeeAllItemAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityHomeRoomTypeBinding
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.SeeAllItemViewModel
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject
import kotlin.properties.Delegates

class PrivateRoomActivity : BaseActivity(), OnItemsClickListener {


    private lateinit var bindingHRT: ActivityHomeRoomTypeBinding
    private val seeAllItemVM: PrivateRoomViewModel by inject()
    private val propertySet = mutableSetOf<RecommendData>()
    private val propertyList = mutableListOf<RecommendData>()
    private var currentItemCount :Int = 0
    private var totalItemCount : Int = 0
    private var pageNumber:Int = 1
    private lateinit var propertyTypes: String

    override val layoutId: Int
        get() = R.layout.activity_home_room_type

    override fun onViewInit(binding: ViewDataBinding?) {
        bindingHRT = binding as ActivityHomeRoomTypeBinding
        propertyTypes = intent.getStringExtra(Constants.DefaultConstants.STRING)!!


        propertySet.clear()
        propertyList.clear()
        pageNumber = 1
        totalItemCount = 0
        currentItemCount = 0
        getAllList(pageNumber,propertyTypes)


        bindingHRT.apply {
            toolBarHRT.ivToolBarBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            tvLoadMoreBtn.setOnClickListener {
                pageNumber++
                getAllList(pageNumber,propertyTypes)
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
        seeAllItemVM.getPropertyResponse().observe(this@PrivateRoomActivity, propertyResponseObserver)

    }

    private val propertyResponseObserver: Observer<ApiResponse<GetPropertyBaseResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }
                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    propertyList.clear()
                    totalItemCount = it.data?.count!!
                    bindingHRT.toolBarHRT.tvToolBarTitle.text = String.format("%s %s",propertyTypes,"($totalItemCount)")
                    setList(it.data.data as MutableList<RecommendData>)

                }
                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(this@PrivateRoomActivity,it.error?.message.toString())
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
                    this@PrivateRoomActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                itemAnimator = DefaultItemAnimator()
                layoutManager = lm
                adapter = SearchAdapter(
                    propertyList,
                    this@PrivateRoomActivity,
                    this@PrivateRoomActivity
                )
            }

            Log.d("TAG", "Private Room: PageNo $pageNumber")
            Log.d("TAG", "Private Room: totalIem $totalItemCount")
            Log.d("TAG", "Private Room: currentItem $currentItemCount")

            if (totalItemCount == currentItemCount) {
                tvLoadMoreBtn.visibility = View.GONE
            } else {
                tvLoadMoreBtn.visibility = View.VISIBLE
            }
        }
    }

}