package com.stayhook.screen.dashboard.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SeeAllItemAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSeeAllItemsBinding
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import org.koin.core.component.inject

class SeeAllItemsFragment : BaseFragment(), OnItemsClickListener {
    private lateinit var seeAllBinding: FragmentSeeAllItemsBinding
    private val mViewModel: SeeAllItemViewModel by inject()
    private var propertyList = mutableListOf<RecommendData>()
    private var propertyCurrentList = mutableListOf<RecommendData>()
    private var currentItemCount: Int = propertyList.size
    private var totalItemCount = 0
    private  var pageNumber: Int=0
    override fun getLayoutId(): Int {
        return R.layout.fragment_see_all_items
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        seeAllBinding = binding as FragmentSeeAllItemsBinding
        propertyList.clear()
        pageNumber = 1
        getAllList(pageNumber)
        currentItemCount = propertyList.size

        seeAllBinding.toolBarSeeAllItem.apply {
            ivToolBarBack.setOnClickListener {
                onBackPress()
            }
            tvToolBarTitle.text = getString(R.string.see_all_text)
        }

        seeAllBinding.tvLoadMoreBtn.setOnClickListener {
            pageNumber++
            getAllList(pageNumber)
        }


    }

    private fun getAllList(pageNumber: Int) {

        val req = GetPropertyRequest().apply {
            page = pageNumber
        }

        mViewModel.hitPropertyApi(req)
        mViewModel.getPropertyResponse().observe(requireActivity(), propertyResponseObserver)
    }

    private val propertyResponseObserver: Observer<ApiResponse<GetPropertyBaseResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    totalItemCount = it.data?.count!!
                    propertyCurrentList = it.data?.data as MutableList<RecommendData>
                    setList(propertyCurrentList)

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                }
            }
        }
    }

    private fun setList(list: MutableList<RecommendData>) {
        propertyList.addAll(list)
        currentItemCount = propertyList.size
        seeAllBinding.apply {
            rvSeeAll.apply {
                val lm = LinearLayoutManager(baseActivity.baseContext, LinearLayoutManager.VERTICAL, false)
                itemAnimator = DefaultItemAnimator()
                layoutManager = lm
                adapter = SeeAllItemAdapter(propertyList, baseActivity.baseContext, this@SeeAllItemsFragment)
            }

            Log.d("SeeAll", "onInitView: PageNo $pageNumber")
            Log.d("SeeAll", "onInitView: totalIem $totalItemCount")
            Log.d("SeeAll", "onInitView: currentItem $currentItemCount")

            if (totalItemCount == currentItemCount) {
                tvLoadMoreBtn.visibility = View.GONE
            } else {
                tvLoadMoreBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onCLickItems(model: RecommendData) {
        mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID,model.id.toString())
        launchActivity(RecommendationDetailActivity::class.java)
      /*  val b = Bundle()

        val rdFragment = RecommendationDetailActivity()
        b.putString("propertyId", model.id.toString())
        replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
        Log.d("TAG", "onCLickItems: model data is $model")
        hideTab()*/
    }


}