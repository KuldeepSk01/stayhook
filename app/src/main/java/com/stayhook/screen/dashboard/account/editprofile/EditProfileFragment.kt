package com.stayhook.screen.dashboard.account.editprofile

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentEditProfileBinding
import com.stayhook.model.response.UpdateProfileResponse
import com.stayhook.network.ApiResponse
import com.stayhook.util.CustomDialogs.showErrorMessage
import com.stayhook.util.CustomDialogs.showSuccessMessage
import com.stayhook.util.getRealPathFromURI
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.core.component.inject
import java.io.File


class EditProfileFragment : BaseFragment() {

    private lateinit var pBinding: FragmentEditProfileBinding
    private val mViewModel: EditProfileViewModel by inject()
    private var imageUri: Uri? = null
    private lateinit var file: File
    override fun getLayoutId(): Int {
        return R.layout.fragment_edit_profile
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

    override fun onInitView(binding: ViewDataBinding, view: View) {
        pBinding = binding as FragmentEditProfileBinding
        setUserDetails()

        pBinding.apply {
            toolbarEditProfile.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.edit_profile)
            }
            ivEditProfileImg.setOnClickListener {
                imageContract.launch("image/*")
            }


            btnUpdateProfile.setOnClickListener {
                val map = HashMap<String, Any>()
                map["first_name"] = etUsernameEditProfile.text.toString()
                map["email"] = etEmailEditProfle.text.toString()
                map["mobile"] = tvMobileEditProfile.text.toString()
                map["gender"] = genderET.text.toString()
                map["profile"] = ""

                var file = File("")
                if (imageUri != null) {
                    file = File(getRealPathFromURI(imageUri!!, requireActivity()).toString())
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
                    .observe(requireActivity(), updateProfileObserver)

            }
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
                        Glide.with(requireActivity()).load(it.profile).into(this)
                    }
                }
            }
        }
    }


    private val updateProfileObserver: Observer<ApiResponse<UpdateProfileResponse>> by lazy {
        Observer {
            when (it.status) {
                ApiResponse.Status.LOADING -> {
                    showProgress()
                }

                ApiResponse.Status.SUCCESS -> {
                    hideProgress()
                    showSuccessMessage(requireActivity(), it.data?.message!!)
                }

                ApiResponse.Status.ERROR -> {
                    showErrorMessage(requireActivity(), it.error?.message.toString())
                    hideProgress()
                }

            }
        }
    }


}