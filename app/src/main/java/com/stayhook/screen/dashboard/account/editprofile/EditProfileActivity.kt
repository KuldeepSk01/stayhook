package com.stayhook.screen.dashboard.account.editprofile

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.FragmentEditProfileBinding
import com.stayhook.model.response.SuccessErrorResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.OnDropDownListener
import com.stayhook.util.dropDownPopup
import com.stayhook.util.getRealPathFromURI
import com.stayhook.util.mLog
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.core.component.inject
import java.io.File
import java.security.Permissions
import java.text.SimpleDateFormat
import java.util.Date


class EditProfileActivity : BaseActivity() {

    private lateinit var pBinding: FragmentEditProfileBinding
    private val mViewModel: EditProfileViewModel by inject()
    private var imageUri: Uri? = null
    private lateinit var file: File
    override val layoutId: Int
        get() = R.layout.fragment_edit_profile

    override fun onViewInit(binding: ViewDataBinding?) {
        pBinding = binding as FragmentEditProfileBinding
        setUserDetails()

        pBinding.apply {
            toolbarEditProfile.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.edit_profile)
            }


            ivEditProfileImg.setOnClickListener {
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val gallery = Intent(MediaStore.ACTION_PICK_IMAGES)
                    Log.d("MyTag", "setInitialSetup: PERMISSION_GRANTED 13")
                    imageSelectActivityLauncher.launch(gallery)
                } else {
                    val permit = Permissions.verifyStoragePermissions(this@EditProfileActivity)
                    if (permit != PackageManager.PERMISSION_GRANTED)
                    else {
                        //   val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                        val gallery =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                        Log.d(
                            "MyTag",
                            "setInitialSetup: PERMISSION_GRANTED" + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        )
                        imageSelectActivityLauncher.launch(gallery)
                    }
                }*/

                imageContract.launch("image/*")
            }

            genderET.setOnClickListener {
                dropDownPopup(this@EditProfileActivity,it,R.menu.menu_gender_items,object :OnDropDownListener{
                    override fun onDropDownClick(item: String) {
                        genderET.setText(item)
                    }
                }).show()
            }

            btnUpdateProfile.setOnClickListener {
                val map = HashMap<String, Any>()
                map["first_name"] = etUsernameEditProfile.text.toString()
                map["email"] = etEmailEditProfle.text.toString()
                map["mobile"] = tvMobileEditProfile.text.toString()
                map["gender"] = genderET.text.toString()

                var file = File("")
                if (imageUri != null) {
                    file = File(getRealPathFromURI(imageUri!!, this@EditProfileActivity).toString())
                    val profileRequestFile: RequestBody =
                        RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
                    val profileMultiPartBody =
                        MultipartBody.Part.createFormData("profile", file.name, profileRequestFile)
                    mViewModel.hitUpdateProfileApi(map, profileMultiPartBody)

                } else {
                    val profileBody = null
                    mViewModel.hitUpdateProfileApi(map, profileBody)
                }
                mViewModel.getUpdateProfileResponse()
                    .observe(this@EditProfileActivity, updateProfileObserver)

            }
        }
    }

    private val imageContract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        try {
            imageUri = it!!
            pBinding.ivEditProfileImg.setImageURI(imageUri)
            Log.d("EditProfile", "onInitView: Uri $imageUri")

        } catch (e: Exception) {
            Log.d("EditProfile", "Select File Error: $e")
        }
    }

 
    private fun setUserDetails() {
        val userDetail = mPref.getUserDetail()
        Log.d("ProfileFragment", "User Detail : $userDetail")
        userDetail.let {
            pBinding.apply {
                etUsernameEditProfile.setText(it.fullName)
                etEmailEditProfle.setText(it.email)
                tvMobileEditProfile.setText(it.mobile)
                genderET.setText(it.gender)
                ivEditProfileImg.apply {
                    if (it.profile.isEmpty()) {
                        this.background =
                            ResourcesCompat.getDrawable(resources, R.drawable.place_holder, null)
                    } else {
                        Glide.with(this@EditProfileActivity).load(it.profile).into(this)
                    }
                }
            }
        }
    }


    private val updateProfileObserver: Observer<ApiResponse<SuccessErrorResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    Toast.makeText(this@EditProfileActivity, "Profile update successfully.", Toast.LENGTH_SHORT).show()
                    finish()
                }

                ApiResponse.Status.ERROR -> {
                    showErrorMessage(this@EditProfileActivity, it.error?.message.toString())
                    hideProgress()
                }

            }
        }
    }


    private val imageSelectActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            try {
                if (it.resultCode== RESULT_OK && it.data!=null){
                    imageUri = it?.data!!.data
                    pBinding.ivEditProfileImg.setImageURI(imageUri)
                    Log.d("EditProfile", "onInitView: Uri $imageUri")

                }
                else{
                    mLog("Nothing Selected...")
                }





            } catch (e: Exception) {
                Log.d("EditProfile", "Select File Error: $e")
            }
            Log.d("MyTAG", "selected image uri ${it.data} ")

        }


}