package com.stayhook.screen.dashboard.search

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.stayhook.R
import com.stayhook.adapter.SearchAdapter
import com.stayhook.adapter.interfaces.OnItemsClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSearchBinding
import com.stayhook.databinding.FragmentSearchFilterBinding
import com.stayhook.model.request.GetPropertyRequest
import com.stayhook.model.response.getproperty.GetPropertyBaseResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import org.koin.core.component.inject
import java.text.NumberFormat
import java.util.Currency
import kotlin.math.min


class SearchFragment : BaseFragment(), OnMapReadyCallback, OnItemsClickListener,
    TextView.OnEditorActionListener {
    private lateinit var sBinding: FragmentSearchBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var mGoogleMap: GoogleMap
    private val mViewMode: SearchFilterViewModel by inject()
    private val propertySet = mutableSetOf<RecommendData>()
    private var propertyList = mutableListOf<RecommendData>()
    private var currentItemCount :Int = 0
    private var totalItemCount : Int = 0
    private var pageNumber:Int = 1

    private var minPrice:Int?=null
    private var maxPrice:Int?=null
    private var searchLocation:String?=null
    private var propertyType:String?=null

    override fun getLayoutId(): Int {
        return R.layout.fragment_search
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sBinding = binding as FragmentSearchBinding
        showTab()
        mainActivity = requireActivity() as MainActivity
        mainActivity.setBottomStyle(2)
        propertySet.clear()
        propertyList.clear()

        pageNumber = 1
        totalItemCount = 0
        currentItemCount = 0
        getAllList(pageNumber,null,null,null,null)

        sBinding.apply {
            ivSearchFilterBtn.setOnClickListener {
                openBottomSheet()
              /*  replaceFragment(
                    R.id.flMainContainer,
                    SearchFilterFragment(),
                    SearchFragment::javaClass.name
                )
                hideTab()*/
            }

            tvLoadMoreBtn.setOnClickListener {
                pageNumber++
                getAllList(pageNumber,searchLocation,minPrice,maxPrice,propertyType)
            }

            val searchMap =
                childFragmentManager.findFragmentById(R.id.searchMap) as SupportMapFragment
            searchMap.getMapAsync(this@SearchFragment)
            etSearchS.setOnEditorActionListener(this@SearchFragment)

        }

    }

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0

        mGoogleMap = p0
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mGoogleMap.isMyLocationEnabled=true
        val lat = mPref[Constants.PreferenceConstant.LATITUDE, 0F]?.toDouble()
        val lng = mPref[Constants.PreferenceConstant.LONGITUDE, 0F]?.toDouble()
        Log.d("TAG","$lat $lng")
        val latlng = LatLng(lat!!,lng!!)
        mGoogleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latlng,
                12.0f
            )
        )
    }

    private fun getAllList(
        pageNumber: Int,
        searchLocation: String? = null,
        minPrice: Int?=null,
        maxPrice: Int?=null,
        property: String?=null
    ) {
        val req = GetPropertyRequest().apply {
            page = pageNumber
            location = searchLocation
            priceMin = minPrice
            priceMax = maxPrice
            propertyType = property
        }
        mViewMode.hitPropertyApi(req)
        mViewMode.getPropertyResponse().observe(requireActivity(), propertyResponseObserver)

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
                    val list  = it.data.data as MutableList<RecommendData>

                    if (list.isEmpty()){
                        sBinding.rlNoDataFound.visibility = View.VISIBLE
                        sBinding.rvSearchFragment.visibility = View.GONE
                    }else{
                        sBinding.rlNoDataFound.visibility = View.GONE
                        sBinding.rvSearchFragment.visibility = View.VISIBLE
                        setList(list)
                    }
                }
                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(requireActivity(),it.error?.message.toString())
                }
            }
        }
    }

    private fun setList(list: MutableList<RecommendData>) {
        propertySet.addAll(list)
        propertyList.addAll(propertySet)
        currentItemCount = propertyList.size

        sBinding.apply {
            rvSearchFragment.apply {
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
                    this@SearchFragment
                )
            }

            Log.d("TAG", "Search: PageNo $pageNumber")
            Log.d("TAG", "Search: totalIem $totalItemCount")
            Log.d("TAG", "Search: currentItem $currentItemCount")

            if (totalItemCount == currentItemCount) {
                tvLoadMoreBtn.visibility = View.GONE
            } else {
                tvLoadMoreBtn.visibility = View.VISIBLE
            }
        }
    }

    override fun onCLickItems(model: RecommendData) {
        mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID, model.id.toString())
        launchActivity(RecommendationDetailActivity::class.java)
    }

    override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            val search = sBinding.etSearchS.text.toString()
            if (search.isEmpty()){
                searchLocation = null
                CustomDialogs.showErrorMessage(requireActivity(),getString(R.string.error_empty_search_location))
            }else{
                Log.d("TAG","search $search")
                searchLocation = search
                pageNumber = 1
                currentItemCount = 0
                totalItemCount = 0
                propertySet.clear()
                getAllList(pageNumber, search, minPrice, maxPrice, propertyType)
            }
            return true
        }
        return false
    }


    private fun openBottomSheet(){
        val dialog = BottomSheetDialog(requireContext())
        val filterLayoutBiding = DataBindingUtil.inflate<FragmentSearchFilterBinding>(LayoutInflater.from(requireContext()),R.layout.fragment_search_filter,null,false)
        filterLayoutBiding.apply {
            rangeSliderPrice.apply {
                valueTo = 100000.0F
                valueFrom = 0.0F


                setLabelFormatter(object : LabelFormatter {
                    override fun getFormattedValue(value: Float): String {
                        val format = NumberFormat.getCurrencyInstance()
                        format.maximumFractionDigits = 0
                        format.currency = Currency.getInstance("INR")
                        format.format(value.toDouble())
                        return format.format(value.toDouble())
                    }

                })


                this.addOnChangeListener(object : RangeSlider.OnChangeListener {
                    override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                        minPrice = slider.values[0].toInt()
                        maxPrice = slider.values[1].toInt()
                        tvMinPrice.text = String.format("%s %d",getString(R.string.indian_currency_symbol),slider.values[0].toInt())
                        tvMaxPrice.text = String.format("%s %d",getString(R.string.indian_currency_symbol),slider.values[1].toInt())
                       // Log.d("TAG","current value $value  from ${slider.valueFrom} to ${slider.valueTo} and values ${slider.values}")
                    }

                })

            }
            tvCloseBtnSf.setOnClickListener {
                dialog.dismiss()
                pageNumber = 0
                currentItemCount = 0
                totalItemCount = 0
                propertySet.clear()
                getAllList(pageNumber, null, null, null, null)
            }
            btnFilterApply.setOnClickListener {
                pageNumber = 0
                currentItemCount = 0
                totalItemCount = 0
                propertySet.clear()
                searchLocation = etSearchFilter.text.toString()
                getAllList(pageNumber, searchLocation, minPrice, maxPrice, propertyType)
               // Toast.makeText(requireContext(),"values ${rangeSliderPrice.values}", Toast.LENGTH_SHORT).show()
                Log.d("TAG","min price ${rangeSliderPrice.valueFrom} max price ${rangeSliderPrice.valueTo} and values ${rangeSliderPrice.values}" +
                        "propertyType $propertyType location word $searchLocation")
                dialog.dismiss()
            }
            llcFilterHouse.setOnClickListener {
                propertyType = getString(R.string.house)
                setBackGround(it as LinearLayoutCompat,filterLayoutBiding)
            }
            llcFilterPrivateRoom.setOnClickListener {
                propertyType = getString(R.string.private_room)
                setBackGround(it as LinearLayoutCompat,filterLayoutBiding)
            }
            llcFilterSharedRoom.setOnClickListener {
                propertyType = getString(R.string.shared_room)
                setBackGround(it as LinearLayoutCompat,filterLayoutBiding)
            }

        }



        dialog.setContentView(filterLayoutBiding.root)
        dialog.create()
        dialog.show()
    }

    private fun setBackGround(llc: LinearLayoutCompat,bindingSearchFilter:FragmentSearchFilterBinding) {
        bindingSearchFilter.apply {
            llcFilterHouse.background = null
            llcFilterPrivateRoom.background = null
            llcFilterSharedRoom.background = null
            llc.background =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.selected_box_drawable,
                    null
                )

        }
    }


}