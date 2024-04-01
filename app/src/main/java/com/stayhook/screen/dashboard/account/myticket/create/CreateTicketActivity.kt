package com.stayhook.screen.dashboard.account.myticket.create

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.stayhook.R
import com.stayhook.adapter.ScheduledTimePickAdapter
import com.stayhook.adapter.TicketImagesAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.base.CollectionBaseResponse
import com.stayhook.databinding.ActivityCreateTicketBinding
import com.stayhook.databinding.LayoutScheduledTimeListBinding
import com.stayhook.model.TicketImagesModel
import com.stayhook.model.request.TicketRequest
import com.stayhook.model.response.StateCityResponse
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs
import com.stayhook.util.getRealPathFromURI
import com.stayhook.util.mLog
import com.stayhook.util.mToast
import org.koin.core.component.inject

class CreateTicketActivity : BaseActivity(), TicketImagesAdapter.OnImageClickListener {
    private lateinit var cBinding: ActivityCreateTicketBinding
    private val userDetail = mPref.getUserDetail()
    private var imageLists = ArrayList<TicketImagesModel>()
    private var imagesPathsList = java.util.ArrayList<String?>()
    private var propertyID:String?=null
    private var issueTypeID:String?=null
    private var issueSubTypeID:String?=null


    private val mViewModel: CreateTicketViewModel by inject()


    override val layoutId: Int
        get() = R.layout.activity_create_ticket

    override fun onViewInit(binding: ViewDataBinding?) {
        cBinding = binding as ActivityCreateTicketBinding

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestStoragePermissionAbove32()
        } else {
            requestStoragePermission()
        }
        cBinding.apply {
            toolbarHelpCenter.apply {
                tvToolBarTitle.text = getString(R.string.create_ticket)
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
            }

            etTitleCreateTicket.text = userDetail.fullName

            etComplainType.setOnClickListener {
                mViewModel.hitIssueTypeApi()
                mViewModel.getIssueTypeResponse()
                    .observe(this@CreateTicketActivity, issueTypeResponseObserver)
            }

            etIssue.setOnClickListener {
                if (issueTypeID==null){
                    mToast(this@CreateTicketActivity,"Please select Complain type")
                    return@setOnClickListener
                }
                mViewModel.hitIssueSubTypeApi(issueTypeID.toString().toInt())
                mViewModel.getIssueSubTypeResponse()
                    .observe(this@CreateTicketActivity, issueSubTypeResponseObserver)

            }

            etPropertyTypeCreateTicket.setOnClickListener {
                mViewModel.hitTicketPropertyApi()
                mViewModel.getTicketPropertyResponse()
                    .observe(this@CreateTicketActivity, getMyPropertyResponseObserver)
            }


            ivUploadBtnCreateTicket.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                    Log.d("MyTag", "setInitialSetup: PERMISSION_GRANTED 13")
                    startForResultImage.launch(gallery)
                } else {
                    val gallery = Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI
                    )
                    Log.d("MyTag", "setInitialSetup: PERMISSION_GRANTED below 13")
                    startForResultImage.launch(gallery)
                }


            }

            btnNewTicket.setOnClickListener {
                if (propertyID==null){
                    mToast(this@CreateTicketActivity,"Please select property")
                    return@setOnClickListener
                }
                if (issueTypeID==null){
                    mToast(this@CreateTicketActivity,"Please select Complain type")
                    return@setOnClickListener
                }

                if (issueSubTypeID==null){
                    mToast(this@CreateTicketActivity,"Please select issue type")
                    return@setOnClickListener
                }


                val req = TicketRequest().apply {
                    property_id = propertyID.toString().toInt()
                    issue_type = issueTypeID.toString().toInt()
                    issue = issueSubTypeID.toString().toInt()
                    issue_details = etIssueDetail.text.toString()
                    upload_document = imagesPathsList
                }

                mViewModel.hitCreateTicketApi(req)
                mViewModel.getCreateTicketResponse()
                    .observe(this@CreateTicketActivity, createTicketResponseObserver)
            }
        }
    }

    private val createTicketResponseObserver =
        Observer<ApiResponse<SuccessErrorResponse>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()

                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()

                    CustomDialogs.showCustomSuccessDialog(this@CreateTicketActivity,
                        it.data?.message.toString(),
                        getString(R.string.our_representative_will_contact_you_shortly_text),
                        getString(R.string.title_okay),
                        object : CustomDialogs.CustomDialogsListener {
                            override fun onComplete(d: Dialog) {
                                d.dismiss()
                                onBackPressedDispatcher.onBackPressed()
                            }
                        }).show()
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@CreateTicketActivity, it.error?.message.toString())
                }
            }
        }


    private val getMyPropertyResponseObserver =
        Observer<ApiResponse<CollectionBaseResponse<StateCityResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()

                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val list = it.data?.data as MutableList<StateCityResponse>
                    showPropertyAlertDialog(list)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@CreateTicketActivity, it.error?.message.toString())
                }
            }
        }
    private val issueTypeResponseObserver =
        Observer<ApiResponse<CollectionBaseResponse<StateCityResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()

                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val list = it.data?.data as MutableList<StateCityResponse>
                    showIssueTypeAlertDialog(list)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@CreateTicketActivity, it.error?.message.toString())
                }
            }
        }

    private val issueSubTypeResponseObserver =
        Observer<ApiResponse<CollectionBaseResponse<StateCityResponse>>> {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()

                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    val list = it.data?.data as MutableList<StateCityResponse>
                    showIssueSubTypeAlertDialog(list)
                }

                ApiResponse.Status.ERROR -> {
                    hideProgress()
                    mToast(this@CreateTicketActivity, it.error?.message.toString())
                }
            }
        }


    private fun showIssueTypeAlertDialog(list: MutableList<StateCityResponse>) {
        // val alertDialog = AlertDialog.Builder(this@ScheduleVisitActivity)
        val alertDialog = Dialog(this@CreateTicketActivity)
        val b = DataBindingUtil.inflate<LayoutScheduledTimeListBinding>(
            LayoutInflater.from(this@CreateTicketActivity),
            R.layout.layout_scheduled_time_list,
            null,
            false
        )
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(this@CreateTicketActivity)
            adapter = ScheduledTimePickAdapter(list,
                this@CreateTicketActivity,
                object : ScheduledTimePickAdapter.OnScheduledTimePickerListener {
                    override fun onTimePicker(model: StateCityResponse) {
                        Log.d("TAG", "onTimePicker: time $model")
                        cBinding.etComplainType.text = model.name
                        issueTypeID = model.id.toString()
                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }



    private fun showIssueSubTypeAlertDialog(list: MutableList<StateCityResponse>) {
        val alertDialog = Dialog(this@CreateTicketActivity)
        val b = DataBindingUtil.inflate<LayoutScheduledTimeListBinding>(
            LayoutInflater.from(this@CreateTicketActivity),
            R.layout.layout_scheduled_time_list,
            null,
            false
        )
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(this@CreateTicketActivity)
            adapter = ScheduledTimePickAdapter(list,
                this@CreateTicketActivity,
                object : ScheduledTimePickAdapter.OnScheduledTimePickerListener {
                    override fun onTimePicker(model: StateCityResponse) {
                        Log.d("TAG", "onTimePicker: time $model")
                        cBinding.etIssue.text = model.name
                        issueSubTypeID = model.id.toString()
                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }




    private var startForResultImage =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            try {
                if (result.resultCode == Activity.RESULT_OK) {
                    // There are no request codes
                    val data = result.data?.data!!
                    Log.d("MyTag", "data $data")
                    val path = getRealPathFromURI(data, this@CreateTicketActivity)
                    imagesPathsList.add(path.toString())
                    imageLists.add(TicketImagesModel("", path!!, data))
                    mLog("Images ${imageLists.toString()}")
                    showTicketIssueImages(imageLists)

                }
            } catch (e: Exception) {
                Log.d("MyTag", "Nothing Selected Image")
            }
        }

    private fun showTicketIssueImages(imageLists: MutableList<TicketImagesModel>) {
        cBinding.rvTicketImages.apply {
            layoutManager = LinearLayoutManager(
                this@CreateTicketActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            adapter = TicketImagesAdapter(imageLists, this@CreateTicketActivity, true,this@CreateTicketActivity)
        }
    }


    private val imageSelectActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val data = it?.data!!.data
                val path = getRealPathFromURI(data!!, this@CreateTicketActivity)
                mLog("Selected path $path")
            } else {
                mLog("Nothing to Select")
            }
        }

    private fun requestStoragePermission() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    //Toast.makeText(this, "Permissi", Toast.LENGTH_SHORT).show()
                }
                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied) {
                    // show alert dialog navigating to Settings
                    showSettingsDialog()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>, token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        }).withErrorListener {
            Toast.makeText(this, "Error occurred! ", Toast.LENGTH_SHORT).show()
        }.onSameThread().check()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestStoragePermissionAbove32() {
        Dexter.withContext(this).withPermissions(
            Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                // check if all permissions are granted
                if (report.areAllPermissionsGranted()) {
                    //Toast.makeText(this, "Permissi", Toast.LENGTH_SHORT).show()
                }
                // check for permanent denial of any permission
                if (report.isAnyPermissionPermanentlyDenied) {
                    // show alert dialog navigating to Settings
                    //showSettingsDialog()
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: List<PermissionRequest>, token: PermissionToken
            ) {
                token.continuePermissionRequest()
            }
        }).withErrorListener {
            Toast.makeText(this, "Error occurred! ", Toast.LENGTH_SHORT).show()
        }.onSameThread().check()
    }

    private fun showSettingsDialog() {
        val builder = AlertDialog.Builder(this, android.R.style.Theme_Material)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton(
            "GOTO SETTINGS"
        ) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }


    private fun showPropertyAlertDialog(list: MutableList<StateCityResponse>) {
        // val alertDialog = AlertDialog.Builder(this@ScheduleVisitActivity)
        val alertDialog = Dialog(this@CreateTicketActivity)
        val b = DataBindingUtil.inflate<LayoutScheduledTimeListBinding>(
            LayoutInflater.from(this@CreateTicketActivity),
            R.layout.layout_scheduled_time_list,
            null,
            false
        )
        b.rvSelectTime.apply {
            layoutManager = LinearLayoutManager(this@CreateTicketActivity)
            adapter = ScheduledTimePickAdapter(list,
                this@CreateTicketActivity,
                object : ScheduledTimePickAdapter.OnScheduledTimePickerListener {
                    override fun onTimePicker(model: StateCityResponse) {
                        Log.d("TAG", "onTimePicker: time $model")
                        cBinding.etPropertyTypeCreateTicket.text = model.name
                        propertyID = model.id.toString()
                        alertDialog.dismiss()
                    }
                })
        }
        alertDialog.setCancelable(false)
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.setContentView(b.root)
        alertDialog.create()
        alertDialog.show()
    }

    override fun onClickImage(model: TicketImagesModel) {

    }

}