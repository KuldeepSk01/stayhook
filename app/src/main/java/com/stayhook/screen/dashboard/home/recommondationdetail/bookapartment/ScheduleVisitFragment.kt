package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentScheduleVisitBinding

class ScheduleVisitFragment : BaseFragment() {

    private lateinit var svBinding : FragmentScheduleVisitBinding
    private val durationFragment : SelectDurationFragment by lazy {
        SelectDurationFragment()
    }
    override fun getLayoutId(): Int {
      return  R.layout.fragment_schedule_visit
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        svBinding = binding as FragmentScheduleVisitBinding
        binding.apply {
            setBackGround(tvAMSelect)
            toolbarScheduleAVisit.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.schedule_visit)
            }
            tvAMSelect.setOnClickListener {
                setBackGround(tvAMSelect)
            }
            tvPMSelect.setOnClickListener {
                setBackGround(tvPMSelect)
            }

            tvTimePickerScheduleVisit.setOnClickListener {
                Toast.makeText(requireContext(), "Open to pick a Time", Toast.LENGTH_SHORT).show()
            }
            binding.scheduleBtn.setOnClickListener {
              /*  replaceFragment(
                    R.id.flMainContainer,
                    durationFragment,
                    this@ScheduleVisitFragment.javaClass.simpleName
                )*/
                Log.d(
                    "SDFragment",
                    "onInitView SDFragment: className ${this@ScheduleVisitFragment.javaClass.simpleName}"
                )
            }
        }
    }
    private fun setBackGround(tv1: AppCompatTextView) {
          svBinding.apply {
              tvAMSelect.background =
                  ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
              tvPMSelect.background =
                  ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
              tvAMSelect.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
              tvPMSelect.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
          }
          tv1.setTextColor(resources.getColor(R.color.primary_color, null))
          tv1.background =
              ResourcesCompat.getDrawable(resources, R.drawable.select_bed_filter_bg, null)
    }

}