package com.stayhook.screen.dashboard.home.recommondationdetail

import android.Manifest
import android.app.ActionBar.LayoutParams
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.stayhook.R
import com.stayhook.adapter.AmenitiesAdapter
import com.stayhook.adapter.RecommendationDetailAdapter
import com.stayhook.adapter.interfaces.AdapterRoomTypes
import com.stayhook.base.BaseActivity
import com.stayhook.base.BaseResponse
import com.stayhook.databinding.FragmentRecommendationDetailBinding
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getpopertydetail.PropertyImage
import com.stayhook.model.response.getpopertydetail.PropertyInventory
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment.RoomActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.schedulevisit.ScheduleVisitActivity
import com.stayhook.screen.dashboard.home.recommondationdetail.writeareview.WriteAReviewActivity
import com.stayhook.screen.dashboard.message.MessageFragment
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.mLog
import org.koin.core.component.inject
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class RecommendationDetailActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var rdBinding: FragmentRecommendationDetailBinding
    private val mViewModel: RecommendationViewModel by inject()
    private lateinit var propertyDetail: GetPropertyDetail
    private var inventorylist = mutableListOf<PropertyInventory>()
    private var roomList = mutableListOf<PropertyRoom>()
    private lateinit var propertyId: String
    private lateinit var mGoogleMap: GoogleMap
    private var isChatOpen : Boolean = false



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewInit(binding: ViewDataBinding?) {
        rdBinding = binding as FragmentRecommendationDetailBinding

    }

    override fun onResume() {
        super.onResume()
        propertyId = mPref[Constants.DefaultConstants.SELECT_PROPERTY_ID, ""]!!

        /* locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
         if (setLocationPermission(verifyMe2Activity)) {
             gpsInit()
         }else{
             mToast("Please enable location permission.")
         }*/

        mViewModel.hitPropertyDetail(propertyId)
        mViewModel.getPropertyDetailResponse()
            .observe(this@RecommendationDetailActivity, propertyDetailReponseObserver)

        rdBinding.apply {
            toolbarRD.apply {
                ivToolBarBack.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.visibility = View.GONE
                ivToolBarRightIcon.visibility = View.VISIBLE
            }



            /*  val searchMap =  supportFragmentManager.findFragmentById(R.id.searchMapRD) as SupportMapFragment
              searchMap.getMapAsync(this@RecommendationDetailActivity)
  */

            ivSHManagerChatBtn.setOnClickListener {
                isChatOpen = true
                clRdLayout.visibility = View.GONE
                clRdChatLayout.visibility = View.VISIBLE
                replaceFragment(R.id.flRdChat,MessageFragment())
            }
            tvRDScheduleList.setOnClickListener {
                val b  = Bundle()
                b.putSerializable("propertyDetail",propertyDetail)
                launchActivity(ScheduleVisitActivity::class.java,"bundleDetail",b)

            }
            tvViewMoreBtn.setOnClickListener {
                rvFeaturesAmenity.apply {
                    layoutParams.width = LayoutParams.MATCH_PARENT
                    layoutParams.height = LayoutParams.MATCH_PARENT
                }
                setInventoryAdapterWithSpanCount(5, inventorylist)
                tvViewMoreBtn.visibility = View.GONE
            }
            tvReviewsWriteAReview.setOnClickListener {
                val b = Bundle()
                launchActivity(WriteAReviewActivity::class.java)
            }


            btnBookDetail.setOnClickListener {
                val b  = Bundle()
                b.putSerializable("propertyDetail",propertyDetail)
                launchActivity(RoomActivity::class.java,"bundleDetail",b)
            }

        }
    }

    private fun setInventoryAdapterWithSpanCount(
        i: Int,
        inventoryList: MutableList<PropertyInventory>
    ) {
        if (inventoryList.isEmpty()) {
            rdBinding.tvViewMoreBtn.visibility = View.GONE
            rdBinding.rlBuildingAmenities.visibility = View.GONE
        } else {
            if (inventoryList.size > 4) {
                rdBinding.rlBuildingAmenities.visibility = View.VISIBLE
                rdBinding.tvViewMoreBtn.visibility = View.VISIBLE
            } else {
                rdBinding.tvViewMoreBtn.visibility = View.GONE
            }
        }
        rdBinding.rvFeaturesAmenity.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(this@RecommendationDetailActivity, i)
            adapter = AmenitiesAdapter(inventoryList, this@RecommendationDetailActivity)
        }
    }

    private val propertyDetailReponseObserver: Observer<ApiResponse<BaseResponse<GetPropertyDetail>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    propertyDetail = it.data?.data!!
                    setDetail(propertyDetail)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(
                        this@RecommendationDetailActivity,
                        it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setDetail(getPropertyDetail: GetPropertyDetail) {
        rdBinding.apply {
            getPropertyDetail.let {
                mPref.put(Constants.DefaultConstants.SELECT_PROPERTY_ID,it.id.toString())
                mLog("Property map ${it.property_map}")

                if (it.property_images.isEmpty()){
                    mLog("Property images is empty")
                    viewPagerRD.adapter= RecommendationDetailAdapter(getDefaultPropertyImages(),this@RecommendationDetailActivity)
                }else{
                    viewPagerRD.adapter = RecommendationDetailAdapter(
                        it.property_images as MutableList<PropertyImage>,
                        this@RecommendationDetailActivity
                    )
                }
                wvMap.settings.javaScriptEnabled = true
                wvMap.webViewClient = WebViewClient()
                try {
                    val  encodedUrl = URLEncoder.encode(
                    "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3504.0478030975714!2d77.37883717549848!3d28.568327225699832!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x390cef5e4bee0711%3A0xae2422de42cff45!2sSILICON%20CITY%2C%20Sector%2076%2C%20Noida%2C%20Uttar%20Pradesh!5e0!3m2!1sen!2sin!4v1702626638694!5m2!1sen!2sin",
                        "UTF-8"
                    )
                    val i = 0
                    val iframe =
                        "<iframe src=$encodedUrl width=100% height=100% frameborder=0 style=border:0</iframe>"
                    wvMap.loadData(iframe, "text/html", "utf-8")


                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }


                indicatorRD.setViewPager(viewPagerRD)
                inventorylist = it.property_inventory as MutableList<PropertyInventory>
                roomList = it.property_room as MutableList<PropertyRoom>
                tvDetailApartmentType.text = it.property_name
                tvDetailPageRatings.text = String.format("%s %s",it.rating.toString(),"(${it.total_review.toString()} Reviews)")
                tvDetailRoomType.text = it.property_type
                mLog("Reccommendation Detail Loction ${String.format(
                    "%s%s%s%s%s%s",
                    it.street,
                    it.city,
                    it.state,
                    it.area,
                    it.pincode,
                    it.country
                )} ")
                tvLocationDescription.text = String.format(
                    "%s%s%s%s%s%s",
                    it.street,
                    it.city,
                    it.state,
                    it.area,
                    it.pincode,
                    it.country
                )
                tvBeds.text = String.format("%d %s",it.total_bed,"Bed")
                tvBath.text = String.format("%d %s",it.total_bath,"Bath")
                tvSqrft.text = String.format("%s",it.total_area)
                tvAboutRD.text = it.about
               // tvRItemCostDetail.text = "$${it.price}"
                setInventoryAdapterWithSpanCount(4, inventorylist)
                setRoomTypeAdapter(roomList)
            }


        }
    }

    private fun getDefaultPropertyImages(): MutableList<PropertyImage> {
        val list = mutableListOf<PropertyImage>()
        list.add(PropertyImage(1,""))
        list.add(PropertyImage(2,""))
        list.add(PropertyImage(3,""))
        return list
    }

    private fun setRoomTypeAdapter(roomList: MutableList<PropertyRoom>) {
        rdBinding.rvRoomTypeRD.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = GridLayoutManager(this@RecommendationDetailActivity, 2)
            adapter = AdapterRoomTypes(roomList, this@RecommendationDetailActivity)
        }
    }


    override val layoutId: Int
        get() = R.layout.fragment_recommendation_detail

    override fun onMapReady(p0: GoogleMap) {
        mGoogleMap = p0
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mGoogleMap.isMyLocationEnabled=true
        val lat = mPref[Constants.PreferenceConstant.LATITUDE, 0F]?.toDouble()
        val lng = mPref[Constants.PreferenceConstant.LONGITUDE, 0F]?.toDouble()
        mLog("Recommendation Detail lat $lat lng $lng")
        val latlng = LatLng(lat!!,lng!!)
        mGoogleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                latlng,
                12.0f
            )
        )

    }



/*
    fun getIframeData(encodedUr:String):String{
        try {
//            "https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3503.0836982119276!2d77.33584365068658!3d28.597265792340774!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x390ce503813db205%3A0xccf2296ef740c90!2sKarol&#39;s%20Salon!5e0!3m2!1sen!2sin!4v1598944640412!5m2!1sen!2sin"
           val  encodedUrl = URLEncoder.encode(
                "$encodedUr",
                "UTF-8"
            )
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        val iframe =
            "<iframe src=$encodedUrl width=100% height=100% frameborder=0 style=border:0</iframe>"
        return iframe

    }
*/


    override fun onBackPressed() {
        super.onBackPressed()
        if (isChatOpen){
            rdBinding.clRdLayout.visibility = View.VISIBLE
            rdBinding.clRdChatLayout.visibility = View.GONE
        }else{
            onBackPressedDispatcher.onBackPressed()
        }

    }

}

