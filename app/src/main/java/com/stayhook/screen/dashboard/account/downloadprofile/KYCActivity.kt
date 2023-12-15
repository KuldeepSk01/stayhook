package com.stayhook.screen.dashboard.account.downloadprofile

import android.app.Dialog
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.ScheduledTimePickAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityKycactivityBinding
import com.stayhook.databinding.LayoutScheduledTimeListBinding
import com.stayhook.model.response.MyProfileResponse
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs
import com.stayhook.util.CustomDialogs.showDateDialog
import com.stayhook.util.OnDropDownListener
import com.stayhook.util.dropDownPopup
import com.stayhook.util.getRealPathFromURI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.core.component.inject
import java.io.File

class KYCActivity : BaseActivity() {
    private lateinit var mBinding: ActivityKycactivityBinding
    private val mViewModel: KYCViewModel by inject()
    private var frontAadhaarImage: MultipartBody.Part? = null
    private var backAadhaarImage: MultipartBody.Part? = null
    private var panCardImage: MultipartBody.Part? = null
    private var policeVerificationImage: MultipartBody.Part? = null
    private lateinit var myProfile: MyProfileResponse
    private var stateId: Int = 0
    private var cityId: Int = 0
    private var stateList: MutableList<StateCityResponse> = mutableListOf()
    private var cityList: MutableList<StateCityResponse> = mutableListOf()



    override val layoutId: Int
        get() = R.layout.activity_kycactivity

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewInit(binding: ViewDataBinding?) {
        mBinding = binding as ActivityKycactivityBinding



        mBinding.apply {
            toolbarKYC.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.kyc_details)
            }
            etGenderKYC.setOnClickListener {
                dropDownPopup(this@KYCActivity,it,R.menu.menu_gender_items,object :
                    OnDropDownListener {
                    override fun onDropDownClick(item: String) {
                        etGenderKYC.text = item
                    }
                }).show()
            }

            etDOBKYC.setOnClickListener {
                showDateDialog(this@KYCActivity, object : CustomDialogs.OnShowDateDialogListener {
                    override fun onSelectDate(date: String) {
                        etDOBKYC.text = date
                    }
                })
            }

            etStateKYC.setOnClickListener {
                mViewModel.hitStateApi(myProfile.countryId.toString())
                mViewModel.stateResponse.observe(this@KYCActivity, getStateResponseObserver)
            }

            etCityKYC.setOnClickListener {

                mViewModel.hitCityApi(if (stateId == 0) myProfile.stateId.toString() else stateId.toString())
                mViewModel.cityResponse.observe(this@KYCActivity, getCityResponseObserver)
                /*
                  dropDownPopup(this@KYCActivity,it,R.menu.menu_gender_items,
                      object : OnDropDownListener {
                          override fun onDropDownClick(item: String) {
                              etStateKYC.text = item
                          }
                      })*/
            }

            ivUploadFrontAadhaarBtnKYC.setOnClickListener {
                uploadFrontAadhaarLaunchContract.launch("image/*")
            }
            ivUploadBackAadhaarBtnKYC.setOnClickListener {
                uploadBackAadhaarLaunchContract.launch("image/*")

            }
            ivUploadPanBtnKYC.setOnClickListener {
                uploadPANLaunchContract.launch("image/*")

            }
            ivUploadPoliceVerificationBtnKYC.setOnClickListener {
                uploadPoliceVerificationContract.launch("image/*")
            }

            ivDeleteFAImageBtnKYC.setOnClickListener {
                rlFrontAadhaarImageLayout.visibility = View.GONE
            }
            ivDeleteBAImageBtnKYC.setOnClickListener {
                rlBackAadhaarImageLayout.visibility = View.GONE
            }
            ivDeletePanImageBtnKYC.setOnClickListener {
                rlPanCardImageLayout.visibility = View.GONE
            }
            ivDeletePVImageBtnKYC.setOnClickListener {
                rlPoliceVerificationImageLayout.visibility = View.GONE
            }

            btnSaveProfile.setOnClickListener {
               val kycHasMap = HashMap<String, Any>()
                kycHasMap["first_name"] = etFullNameKYC.text.toString()
                kycHasMap["dob"] = etDOBKYC.text.toString()
                kycHasMap["gender"] = etGenderKYC.text.toString()
                kycHasMap["aadhaarNo"] = etAadhaarNoKYC.text.toString()
                kycHasMap["panNo"] = etPANNoKYC.text.toString()
                kycHasMap["address"] = etAddressKYC.text.toString()
                kycHasMap["state"] = if (stateId == 0) myProfile.stateId else stateId
                kycHasMap["city"] = if (cityId == 0) myProfile.cityId else cityId
                Log.d(
                    "TAG",
                    "onViewInit: Aadhaar ${etAadhaarNoKYC.text.toString()} ${etPANNoKYC.text.toString()}"
                )
                mViewModel.hitUpdateKYCApi(
                    kycHasMap,
                    frontAadhaarImage,
                    backAadhaarImage,
                    panCardImage,
                    policeVerificationImage
                )
                mViewModel.getUpdateKYCResponse()
                    .observe(this@KYCActivity, updateKycResponseObserver)

            }

        }

    }

    private val updateKycResponseObserver: Observer<ApiResponse<SuccessErrorResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    CustomDialogs.showCustomSuccessDialog(this@KYCActivity,
                        getString(R.string.verification_complete_text),
                        getString(R.string.verification_our_representative_will_text),
                        getString(R.string.title_okay),
                        object : CustomDialogs.CustomDialogsListener {
                            override fun onComplete(d: Dialog) {
                                d.dismiss()
                                finish()
                            }
                        }).show()

                }

                ApiResponse.Status.ERROR -> {
                    CustomDialogs.showErrorMessage(
                        this@KYCActivity,
                        it.error?.message.toString()
                    )
                    hideProgress()
                }

            }
        }
    }
    private val getStateResponseObserver: Observer<ApiResponse<CollectionBaseResponse<StateCityResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    stateList.clear()
                    stateList = it.data?.data as MutableList<StateCityResponse>
                    showStateListDialog(stateList, mBinding.etStateKYC)

                }

                ApiResponse.Status.ERROR -> {
                    CustomDialogs.showErrorMessage(
                        this@KYCActivity,
                        it.error?.message.toString()
                    )
                    hideProgress()
                }

            }
        }
    }
    private val getCityResponseObserver: Observer<ApiResponse<CollectionBaseResponse<StateCityResponse>>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    cityList.clear()
                    cityList = it.data?.data as MutableList<StateCityResponse>
                    showCityListDialog(cityList, mBinding.etCityKYC)

                }

                ApiResponse.Status.ERROR -> {
                    CustomDialogs.showErrorMessage(
                        this@KYCActivity,
                        it.error?.message.toString()
                    )
                    hideProgress()
                }

            }
        }
    }

    private val uploadFrontAadhaarLaunchContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            try {
                // frontAadhaarImageUri = it!!
                mBinding.ivUploadFrontAadhaarImageKYC.setImageURI(it)
                Log.d("EditProfile", "frontAadhaarUri: Uri $it")

                val file =
                    File(getRealPathFromURI(it!!, this@KYCActivity).toString())
                val profileRequestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                frontAadhaarImage = MultipartBody.Part.createFormData(
                    "upload_aadhaar",
                    file.name,
                    profileRequestFile
                )


                mBinding.rlFrontAadhaarImageLayout.visibility = View.VISIBLE

            } catch (e: Exception) {
                Log.d("EditProfile", "Select File Error: $e")
                frontAadhaarImage = null
                mBinding.rlFrontAadhaarImageLayout.visibility = View.GONE

            }

        }

    private val uploadBackAadhaarLaunchContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            try {
                // backAadhaarImageUri = it!!
                mBinding.ivUploadedBackAadhaarImageKYC.setImageURI(it)
                Log.d("EditProfile", "frontAadhaarUri: Uri $it")
                mBinding.rlBackAadhaarImageLayout.visibility = View.VISIBLE

                val file =
                    File(getRealPathFromURI(it!!, this@KYCActivity).toString())
                val profileRequestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                backAadhaarImage = MultipartBody.Part.createFormData(
                    "upload_aadhaar2",
                    file.name,
                    profileRequestFile
                )


            } catch (e: Exception) {
                Log.d("EditProfile", "Select File Error: $e")
                mBinding.rlBackAadhaarImageLayout.visibility = View.GONE
                backAadhaarImage = null

            }

        }

    private val uploadPANLaunchContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            try {
                //panCardImageUri = it!!
                mBinding.ivUploadedPanImageKYC.setImageURI(it)
                Log.d("Update Kyc", "panUri:  $it")
                mBinding.rlPanCardImageLayout.visibility = View.VISIBLE


                val file =
                    File(getRealPathFromURI(it!!, this@KYCActivity).toString())
                val profileRequestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                panCardImage =
                    MultipartBody.Part.createFormData("upload_pan", file.name, profileRequestFile)

            } catch (e: Exception) {
                Log.d("EditProfile", "Select File Error: $e")
                mBinding.rlPanCardImageLayout.visibility = View.GONE
                panCardImage = null

            }

        }
    private val uploadPoliceVerificationContract =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            try {
                //policeVerificationImageUri = it!!
                mBinding.ivUploadedPoliceVerificationImageKYC.setImageURI(it)
                Log.d("Complete", "policeVerificationImageUri: Uri $it")
                mBinding.rlPoliceVerificationImageLayout.visibility = View.VISIBLE

                val file =
                    File(getRealPathFromURI(it!!, this@KYCActivity).toString())
                val profileRequestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                policeVerificationImage = MultipartBody.Part.createFormData(
                    "police_verification",
                    file.name,
                    profileRequestFile
                )

            } catch (e: Exception) {
                Log.d("Complete", "Select File Error: $e")
                mBinding.rlPoliceVerificationImageLayout.visibility = View.GONE
                policeVerificationImage = null
            }

        }


    override fun onResume() {
        super.onResume()
        myProfile = mPref.getUserDetail()
        Log.d("TAG", "onResume: KYC Profile $myProfile")
        mBinding.apply {
            myProfile.let {
                etFullNameKYC.setText(it.fullName)
                etDOBKYC.text = it.dob
                etGenderKYC.text = it.gender
                etPANNoKYC.setText(it.panNumber)
                etAadhaarNoKYC.setText(it.aadhaarNumber)
                etAddressKYC.setText(it.address)
                etStateKYC.text = it.stateName
                etCityKYC.text = it.cityName
            }
        }
    }


    private fun showStateListDialog(list: MutableList<StateCityResponse>, tv: AppCompatTextView) {
        // val alertDialog = AlertDialog.Builder(this@ScheduleVisitActivity)
        val alertDialog = Dialog(this@KYCActivity)
        val b = DataBindingUtil.inflate<LayoutScheduledTimeListBinding>(
            LayoutInflater.from(this@KYCActivity),
            R.layout.layout_scheduled_time_list,
            null,
            false
        )
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(this@KYCActivity)
            adapter = ScheduledTimePickAdapter(list,
                this@KYCActivity,
                object : ScheduledTimePickAdapter.OnScheduledTimePickerListener {
                    override fun onTimePicker(model: StateCityResponse) {
                        Log.d("TAG", "onTimePicker: time $model")
                        tv.text = model.name
                        stateId = model.id
                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }

    private fun showCityListDialog(list: MutableList<StateCityResponse>, tv: AppCompatTextView) {
        // val alertDialog = AlertDialog.Builder(this@ScheduleVisitActivity)
        val alertDialog = Dialog(this@KYCActivity)
        val b = DataBindingUtil.inflate<LayoutScheduledTimeListBinding>(
            LayoutInflater.from(this@KYCActivity),
            R.layout.layout_scheduled_time_list,
            null,
            false
        )
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(this@KYCActivity)
            adapter = ScheduledTimePickAdapter(list,
                this@KYCActivity,
                object : ScheduledTimePickAdapter.OnScheduledTimePickerListener {
                    override fun onTimePicker(model: StateCityResponse) {
                        Log.d("TAG", "onTimePicker: time $model")
                        tv.text = model.name
                        cityId = model.id
                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }

}