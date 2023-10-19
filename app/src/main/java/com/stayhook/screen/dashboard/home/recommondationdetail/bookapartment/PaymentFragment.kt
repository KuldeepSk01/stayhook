package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentPaymentBinding
import com.stayhook.screen.dashboard.home.HomeFragment
import com.stayhook.screen.dashboard.home.HomeRoomTypeActivity
import com.stayhook.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaymentFragment : BaseFragment() {

    private lateinit var pBinding: FragmentPaymentBinding
    private lateinit var bottomSheetB:BottomSheetBehavior<ConstraintLayout>
    override fun getLayoutId(): Int {
        return R.layout.fragment_payment
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        pBinding = binding as FragmentPaymentBinding
        bottomSheetB = BottomSheetBehavior.from(pBinding.bottomSheetBC.clBookingCompleteBottomSheet)
        pBinding.apply {
            toolBarPayment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.title_payment)
            }
            btnPayment.setOnClickListener {
                pBinding.cvBottomBtn.visibility = View.GONE
                pBinding.bottomSheetBCCL.visibility = View.VISIBLE
                if(bottomSheetB.state != BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetB.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetB.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
            //openBottomDialogSheet().show()
            }

        bottomSheetB.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
               val mbBtn =  bottomSheet.findViewById<AppCompatTextView>(R.id.myBookingCompleteBtn)
                mbBtn.setOnClickListener {

                    pBinding.cvBottomBtn.visibility = View.VISIBLE
                    bottomSheetB.state = BottomSheetBehavior.STATE_COLLAPSED
                    bottomSheetB.peekHeight=0
                    bottomSheetB.state = BottomSheetBehavior.STATE_HIDDEN
                    pBinding.bottomSheetBCCL.visibility = View.GONE
                    launchActivityWithB(getString(R.string.private_room))
                    removeAllFragments()

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })
        }

    private fun removeAllFragments() {
        val fm = requireActivity().supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
    }


    private fun launchActivityWithB(title: String) {
        val b = Bundle()
        b.putString(Constants.DefaultConstants.STRING, title)
        launchActivity(HomeRoomTypeActivity::class.java, Constants.DefaultConstants.BUNDLE,b)
    }


}