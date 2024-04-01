package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SelectBedAdapter
import com.stayhook.adapter.SelectRoomAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivitySelectBedBinding
import com.stayhook.interfaces.OnRoomClickListener
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse
import com.stayhook.screen.dashboard.home.recommondationdetail.beds.BedsViewModel
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.Utility
import org.koin.core.component.inject

class SelectBedActivity : BaseActivity(), OnRoomClickListener, SelectBedAdapter.SelectBedListener {

    private lateinit var sBBinding: ActivitySelectBedBinding

    private lateinit var propertyId: String

    private val roomVm: RoomViewModel by inject()
    private val mViewModel: BedsViewModel by inject()

    private lateinit var roomList: MutableList<PropertyRoom>
    private lateinit var roomBedList: MutableList<PropertyRoom>

    private var isBedSelect = false

    private var propertyRoom = PropertyRoom()


    override val layoutId: Int
        get() = R.layout.activity_select_bed

    override fun onViewInit(binding: ViewDataBinding?) {
        sBBinding = binding as ActivitySelectBedBinding
        roomList = mutableListOf()
        roomBedList = mutableListOf()
        propertyId = mPref[Constants.DefaultConstants.SELECT_PROPERTY_ID, ""]!!
        hitPropertyRoomApi(propertyId)

        sBBinding.apply {
            sBToolBar.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.select_bed)
            }

            btnSelectBed.setOnClickListener {
                if (!Utility.isNetworkAvailable(this@SelectBedActivity)){
                    showErrorMessage(this@SelectBedActivity,
                        Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                    )
                    return@setOnClickListener
                }

                if (!isBedSelect){
                    showErrorMessage(this@SelectBedActivity,"Please select bed !")
                }else if (propertyRoom.availabele == getString(R.string.no_text)) {
                    CustomDialogs.showErrorMessage(
                        this@SelectBedActivity, getString(R.string.not_available)
                    )
                }else{
                    val b = Bundle()
                    b.putSerializable("tokenRequest",propertyRoom)
                    launchActivity(SelectDateActivity::class.java,"bundleToken",b)
                }
            }

        }
    }


    private fun hitPropertyRoomApi(propertyId: String) {
        roomVm.hitPropertyDetail(propertyId)
        roomVm.getPropertyDetailResponse()
            .observe(this@SelectBedActivity, propertyRoomResponseObserver)
    }

    private fun hitPropertyRoomBedsApi(propertyId: String, roomId: String) {
        mViewModel.hitPropertyRoomBeds(propertyId, roomId)
        mViewModel.getPropertyRoomBedsResponse()
            .observe(this@SelectBedActivity, propertyRoomBedsResponseObserver)
    }

    private val propertyRoomResponseObserver: Observer<ApiResponse<CollectionBaseResponse<PropertyRoom>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    roomList.clear()
                    Log.d("MyTag", "room list inside select bed ${it.data?.data}: ")
                    roomList = it.data?.data!! as MutableList<PropertyRoom>
                    setRoomList(roomList)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@SelectBedActivity, it.error?.message.toString()
                    )
                }
            }
        }
    }

    private val propertyRoomBedsResponseObserver: Observer<ApiResponse<CollectionBaseResponse<PropertyRoom>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    roomBedList.clear()
                    Log.d("Tag", "Bed list ${it.data?.data}")
                    roomBedList = it.data?.data!! as MutableList<PropertyRoom>
                    setRoomBedList(roomBedList)
                    isBedSelect = false

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    CustomDialogs.showErrorMessage(
                        this@SelectBedActivity, it.error?.message.toString()
                    )
                }
            }
        }
    }

    private fun setRoomList(roomList: MutableList<PropertyRoom>) {
        sBBinding.rvRooms.apply {
            layoutManager = LinearLayoutManager(this@SelectBedActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = SelectBedAdapter(roomList, this@SelectBedActivity, this@SelectBedActivity)
               hitPropertyRoomBedsApi(propertyId, roomList[0].id.toString())
        }
    }




    private fun setRoomBedList(roomList: MutableList<PropertyRoom>) {
        sBBinding.apply {
            rvSelectBed.itemAnimator = DefaultItemAnimator()
            rvSelectBed.layoutManager =
                LinearLayoutManager(this@SelectBedActivity, LinearLayoutManager.VERTICAL, false)
            rvSelectBed.adapter = SelectRoomAdapter(
                roomList,
                this@SelectBedActivity,
                this@SelectBedActivity,
                getString(R.string.Bed_select)
            )

        }

    }

    /*this is for bed selecting*/
    override fun onRoomClick(room: PropertyRoom) {
        Log.d("TAG", "onRoomClick: $room")
        isBedSelect = true
        propertyRoom = room

    }

    /*this is for top room slider*/
    override fun onBedClick(model: PropertyRoom) {
        hitPropertyRoomBedsApi(propertyId, model.id.toString())
    }


}