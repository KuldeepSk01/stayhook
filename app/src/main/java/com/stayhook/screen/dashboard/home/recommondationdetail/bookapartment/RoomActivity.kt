package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SelectRoomAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityRoomBinding
import com.stayhook.interfaces.OnRoomClickListener
import com.stayhook.model.response.getpopertydetail.GetPropertyDetail
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.network.ApiResponse
import com.stayhook.util.Constants
import com.stayhook.util.CustomDialogs
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.Utility
import com.stayhook.util.serializable
import org.koin.core.component.inject

class RoomActivity : BaseActivity(), OnRoomClickListener,LifecycleOwner {

    private lateinit var bookFBinding: ActivityRoomBinding
    private lateinit var roomList: MutableList<PropertyRoom>
    private lateinit var propertyId: String
    private lateinit var propertyDetail: GetPropertyDetail
    private var propertyRoom: PropertyRoom? = null


    private val mViewModel: RoomViewModel by inject()
    private var roomPrivacy: String? = null

    override val layoutId: Int
        get() = R.layout.activity_room

    override fun onViewInit(binding: ViewDataBinding?) {
        bookFBinding = binding as ActivityRoomBinding
        Log.d("TAG","onViewInit")
        propertyId = mPref[Constants.DefaultConstants.SELECT_PROPERTY_ID, ""]!!
        propertyDetail = intent.getBundleExtra("bundleDetail")?.serializable("propertyDetail")!!
        // propertyId = arguments?.getString(Constants.DefaultConstants.SELECT_PROPERTY_ID)!!
        roomList = mutableListOf()

        hitPropertyRoomApi(propertyDetail.id.toString())


        bookFBinding.apply {
            toolbarBookApartment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.select_room)
            }

            btnSelectRoom.setOnClickListener {
                if (!Utility.isConnectionAvailable()) {
                    CustomDialogs.showErrorMessage(
                        this@RoomActivity, Constants.NetworkConstant.NO_INTERNET_AVAILABLE
                    )
                    return@setOnClickListener
                }

                if (propertyRoom == null) {
                    showErrorMessage(this@RoomActivity, "Please select room !")
                } else if (propertyRoom?.availabele == getString(R.string.no_text)) {
                    CustomDialogs.showErrorMessage(
                        this@RoomActivity, getString(R.string.not_available)
                    )
                } else {
                    when (propertyRoom!!.roomPrivacy!!) {
                        getString(R.string.private_text) -> {
                            val b = Bundle()
                            b.putSerializable("tokenRequest", propertyRoom)
                            launchActivity(SelectDateActivity::class.java, "bundleToken", b)
                        }

                        getString(R.string.shared_text) -> {
                            launchActivity(SelectBedActivity::class.java)
                        }
                    }

                    Log.d(
                        "RDFragment",
                        "onInitView RDFragment: className ${this@RoomActivity.javaClass.simpleName}"
                    )
                }

            }


        }
    }



    private fun hitPropertyRoomApi(propertyId: String) {
        mViewModel.hitPropertyDetail(propertyId)
        mViewModel.getPropertyDetailResponse().observe(this,propertyRoomResponseObserver)
           // .observe(this@RoomActivity, propertyRoomResponseObserver)
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
                    //Log.d("Tag", "room list ${it.data?.data}")
                    Log.d("TAG","OnData")
                   // Toast.makeText(this@RoomActivity,"data",Toast.LENGTH_SHORT).show()

                    roomList = it.data?.data!! as MutableList<PropertyRoom>
                    setAdapter(roomList)

                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    showErrorMessage(this@RoomActivity, it.error?.message.toString())
                }
            }
        }
    }


    private fun setAdapter(list: MutableList<PropertyRoom>) {
        bookFBinding.rvSelectRoom.apply {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = SelectRoomAdapter(
                list, this@RoomActivity, this@RoomActivity, getString(R.string.room_select)
            )
        }
    }

    override fun onRoomClick(room: PropertyRoom) {
        // roomPrivacy = room.roomPrivacy
        propertyRoom = room

    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG","OnDestroy")
        mViewModel.getPropertyDetailResponse().removeObserver(propertyRoomResponseObserver)


    }

}
