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
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailFragment
import org.koin.core.component.inject

class SeeAllItemsFragment : BaseFragment(), OnItemsClickListener {
    private lateinit var seeAllBinding: FragmentSeeAllItemsBinding
    private val mViewModel: SeeAllItemViewModel by inject()
    private var propertyList = mutableListOf<RecommendData>()
    private var propertyCurrentList = mutableListOf<RecommendData>()
    private var currentItemCount: Int = propertyList.size
    private var totalItemCount = 0
    private var pageNumber: Int = 1
    override fun getLayoutId(): Int {
        return R.layout.fragment_see_all_items
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        seeAllBinding = binding as FragmentSeeAllItemsBinding
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
        mViewModel.hitPropertyApi(page = pageNumber.toString())
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
                val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                itemAnimator = DefaultItemAnimator()
                layoutManager = lm
                adapter =
                    SeeAllItemAdapter(propertyList, requireContext(), this@SeeAllItemsFragment)
                /*
                                rvSeeAll.addOnScrollListener(object : PaginationScrollListener(lm){
                                    override fun loadMoreItems() {
                                        isLoading = true
                                        currentPage += 1

                                        Log.d("SeeAll", "loadMoreItems: ")
                                       // doApiCall()
                                    }

                                    override fun isLastPage(): Boolean {
                                        Log.d("SeeAll", "isLastPage: ")

                                        return isLastPage

                                    }

                                    override fun isLoading(): Boolean {
                                        Log.d("SeeAll", "isLoading: ")

                                        return isLoading

                                    }
                                })
                */
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
        val b = Bundle()
        val rdFragment = RecommendationDetailFragment()
        b.putString("propertyId", model.id.toString())
        replaceFragment(R.id.flMainContainer, rdFragment, b, HomeFragment().javaClass.simpleName)
        Log.d("TAG", "onCLickItems: model data is $model")
        hideTab()
    }


}