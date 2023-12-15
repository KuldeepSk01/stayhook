package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityPaymentBinding
import com.stayhook.model.response.getpopertydetail.PropertyRoom
import com.stayhook.screen.dashboard.home.privateroom.PrivateRoomActivity
import com.stayhook.util.serializable

class PaymentActivity : BaseActivity() {


    private lateinit var pBinding: ActivityPaymentBinding
    private lateinit var bottomSheetB: BottomSheetBehavior<ConstraintLayout>

    override val layoutId: Int
        get() = R.layout.activity_payment

    override fun onViewInit(binding: ViewDataBinding?) {
        pBinding = binding as ActivityPaymentBinding
        val propertyRoom = intent.getBundleExtra("bundleToken")?.serializable<PropertyRoom>("tokenRequest")!!
        Log.d("TAG", "onViewInit: Sumary $propertyRoom")
        bottomSheetB = BottomSheetBehavior.from(pBinding.bottomSheetBC.clBookingCompleteBottomSheet)
        pBinding.apply {
            toolBarPayment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPressedDispatcher.onBackPressed()
                }
                tvToolBarTitle.text = getString(R.string.title_payment)
            }
            btnPayment.setOnClickListener {
                pBinding.cvBottomBtn.visibility = View.GONE
                pBinding.bottomSheetBCCL.visibility = View.VISIBLE
                if (bottomSheetB.state != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetB.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetB.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
            //openBottomDialogSheet().show()
        }

        bottomSheetB.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                val mbBtn = bottomSheet.findViewById<AppCompatTextView>(R.id.myBookingCompleteBtn)
                mbBtn.setOnClickListener {


                    pBinding.cvBottomBtn.visibility = View.VISIBLE
                    bottomSheetB.state = BottomSheetBehavior.STATE_COLLAPSED
                    bottomSheetB.peekHeight = 0
                    bottomSheetB.state = BottomSheetBehavior.STATE_HIDDEN
                    pBinding.bottomSheetBCCL.visibility = View.GONE



                    launchActivity(PrivateRoomActivity::class.java)
                    finish()

                    // launchActivityWithB(getString(R.string.private_room))
                    // removeAllFragments()
                    //replaceFragWithB(getString(R.string.private_room))


                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

    }


    /*private fun removeAllFragments() {
        val fm = requireActivity().supportFragmentManager
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
       // onBackPress()
    }
*/

    /*  private fun replaceFragWithB(title: String) {
          val b = Bundle()
          b.putString(Constants.DefaultConstants.STRING, title)
          replaceFragment(
              R.id.flMainContainer,
              HomeRoomTypeFragment(), b,
              HomeFragment().javaClass.simpleName
          )
      }*/

}