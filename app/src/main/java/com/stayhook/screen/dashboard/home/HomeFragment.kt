package com.stayhook.screen.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.stayhook.R
import com.stayhook.adapter.NearbyLocationItemAdapter
import com.stayhook.adapter.RecentlyAddedItemAdapter
import com.stayhook.adapter.RecommendationItemAdapter
import com.stayhook.adapter.interfaces.RecommendationAdapterListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentHomeBinding
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.model.response.home.HomeResponse
import com.stayhook.model.response.home.RecommendData
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.RecommendationDetailActivity
import com.stayhook.util.Constants
import com.stayhook.util.Constants.NetworkConstant.Companion.NO_INTERNET_AVAILABLE
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.Utility.isNetworkAvailable
import com.stayhook.util.mToast
import org.koin.core.component.inject


class HomeFragment : BaseFragment(), RecommendationAdapterListener {

    private val homeViewModel: HomeViewModel by inject()
    private var recommendationList = mutableListOf<RecommendData>()
    private var recentlyAddedDataList = mutableListOf<RecommendData>()
    private var nearbyDataList = mutableListOf<RecommendData>()
    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var mGoogleMap: GoogleMap

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        homeBinding = binding as FragmentHomeBinding
        showTab()
        homeViewModel.fragmentHome = this@HomeFragment
        homeBinding.homeViewModel = homeViewModel

        val searchMap =
            childFragmentManager.findFragmentById(R.id.homeMap) as SupportMapFragment
        //searchMap.getMapAsync(this@HomeFragment)

        homeBinding.tvHomeLocation.apply {
            if (!isNetworkAvailable(requireContext())) {
                mToast(requireContext(), NO_INTERNET_AVAILABLE)
            } else {
                text = homeViewModel.getCurrentAddress()
                //mLog("Address $lat")
            }
        }

        //  if (mGoogleMap!=null)
        /*mGoogleMap.setOnMyLocationChangeListener(OnMyLocationChangeListener { arg0 ->


            mGoogleMap.addMarker(
                MarkerOptions().position(
                    LatLng(arg0.latitude, arg0.longitude)
                ).title("It's Me!")
            )
        })*/


        hitHomeApi()

    }

    private fun setAdapters() {
        homeBinding.apply {
            rvRecommendationItems.apply {
                layoutManager =
                    LinearLayoutManager(
                        baseActivity.baseContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = RecommendationItemAdapter(
                    recommendationList,
                    baseActivity.baseContext,
                    this@HomeFragment
                )
            }
            rvRecentlyAddedHome.apply {
                layoutManager =
                    LinearLayoutManager(
                        baseActivity.baseContext,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = RecentlyAddedItemAdapter(
                    recentlyAddedDataList,
                    baseActivity.baseContext,
                    this@HomeFragment
                )
            }
            homeBinding.rvNearByLocationItems.apply {
                layoutManager =
                    LinearLayoutManager(
                        baseActivity.baseContext,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter =
                    NearbyLocationItemAdapter(
                        nearbyDataList,
                        baseActivity.baseContext,
                        this@HomeFragment
                    )
            }
        }

    }

    private fun hitHomeApi() {
        if (!isNetworkAvailable(requireContext())) {
            showErrorMessage(requireActivity(), NO_INTERNET_AVAILABLE)
            return
        }
        homeViewModel.hitHomePageApi()
        homeViewModel.getHomeResponse().observe(requireActivity(), homeResponseObserver)

    }

    private val homeResponseObserver: Observer<ApiResponse<HomeResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    recommendationList = it.data?.recommend_data as MutableList<RecommendData>
                    recentlyAddedDataList = it.data.recently_data as MutableList<RecommendData>
                    nearbyDataList = it.data.nearby_data as MutableList<RecommendData>
                    setAdapters()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(requireActivity(), it.error?.message.toString())
                }
            }
        }
    }


    override fun onClickItems(model: RecommendData) {
        mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID, model.id.toString())
        launchActivity(RecommendationDetailActivity::class.java)
    }

    override fun addFavoriteItem(model: RecommendData) {
        homeViewModel.hitAddFavoritePropertyApi(model.id.toString())
        homeViewModel.getAddFavoritePropertyResponse()
            .observe(requireActivity(), addFavoriteResponseObserver)
    }

    private val addFavoriteResponseObserver: Observer<ApiResponse<SuccessErrorResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hitHomeApi()
                    mToast(requireContext(), it.data?.message.toString())
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(requireContext(), it.error?.message.toString())
                }
            }
        }
    }

    /*
        override fun onMapReady(p0: GoogleMap) {
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
            mGoogleMap.isMyLocationEnabled = true

            mGoogleMap.setOnMyLocationClickListener {
                val lat = it.latitude
                val lng = it.longitude
                Log.d("TAG", "$lat $lng")
                val latlng = LatLng(lat!!, lng!!)
                homeBinding.tvHomeLocation.apply {
                    mLog("Connection ${isConnectionAvailable()}")
                    if (isConnectionAvailable()){
                        mToast(requireContext(),NO_INTERNET_AVAILABLE)
                    }else{
                        getCurrentAddress(lat,lng)
                        //mLog("Address $lat")
                    }
                }


                mGoogleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        latlng,
                        12.0f
                    )
                )
            }




        }
    */

    /*
        fun getCurrentAddress( lat:Double,lng:Double) {

            var address: String? = null
            if (lat != 0.0 && lng != 0.0) {
                mLog("user current address $lat $lng")
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(
                    lat!!,
                    lng!!,
                    1
                ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                val addressLine =
                    addresses!![0].getAddressLine(1) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                mLog("user current complete address line $addressLine")
                val city = addresses[0].locality
                val state = addresses[0].adminArea
                val country = addresses[0].countryName
                val postalCode = addresses[0].postalCode
                val knownName = addresses[0].featureName
                address =  String.format("%s,%s",city,country)
                homeBinding.tvLocationHome.text = address
            }
        }
    */


}