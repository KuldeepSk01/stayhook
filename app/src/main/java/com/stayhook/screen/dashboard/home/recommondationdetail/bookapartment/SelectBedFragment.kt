package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SelectBedAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSelectBedBinding
import com.stayhook.model.Bed

class SelectBedFragment : BaseFragment() {
    private lateinit var sBBinding: FragmentSelectBedBinding
    private val selectDateFragment: SelectDateFragment by lazy {
        SelectDateFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_select_bed
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sBBinding = binding as FragmentSelectBedBinding
        setBackGround(sBBinding.llRoom1SB, sBBinding.tvRoom1, sBBinding.tvRoomBed1)

        sBBinding.apply {
            sBToolBar.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.select_bed)
            }

            llRoom1SB.setOnClickListener {
                rvSelectBed.adapter = SelectBedAdapter(getList(), requireContext())
                setBackGround(sBBinding.llRoom1SB, sBBinding.tvRoom1, sBBinding.tvRoomBed1)
            }
            llRoom2SB.setOnClickListener {
                rvSelectBed.adapter = SelectBedAdapter(getList2(), requireContext())
                setBackGround(sBBinding.llRoom2SB, sBBinding.tvRoom2, sBBinding.tvRoomBed2)
            }
            btnSelectBed.setOnClickListener {
                replaceFragment(
                    R.id.flMainContainer,
                    selectDateFragment,
                    this@SelectBedFragment.javaClass.simpleName
                )
                Log.d(
                    "RDFragment",
                    "onInitView RDFragment: className ${this@SelectBedFragment.javaClass.simpleName}"
                )
            }


            rvSelectBed.itemAnimator = DefaultItemAnimator()
            rvSelectBed.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvSelectBed.adapter = SelectBedAdapter(getList(), requireContext())


        }
    }

    private fun setBackGround(
        vLayout: LinearLayoutCompat,
        tv1: AppCompatTextView,
        tv2: AppCompatTextView
    ) {
        sBBinding.apply {
            llRoom1SB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            llRoom2SB.background =
                ResourcesCompat.getDrawable(resources, R.drawable.otp_box_background, null)
            tvRoom1.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvRoom2.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvRoomBed1.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
            tvRoomBed2.setTextColor(resources.getColor(R.color.sub_heading_text_color, null))

        }
        tv1.setTextColor(resources.getColor(R.color.primary_color, null))
        tv2.setTextColor(resources.getColor(R.color.primary_color, null))
        vLayout.background =
            ResourcesCompat.getDrawable(resources, R.drawable.select_bed_filter_bg, null)
    }


    private fun getList(): MutableList<Bed> {
        val list = mutableListOf<Bed>()
        list.add(Bed(1, "1 x Attached to bathroom", 20000))
        list.add(Bed(2, "1 x Attached to bathroom", 29928))
        return list
    }


    private fun getList2(): MutableList<Bed> {
        val list = mutableListOf<Bed>()
        list.add(Bed(1, "1 x Attached to bathroom", 20000))
        list.add(Bed(2, "1 x Attached to bathroom", 29928))
        list.add(Bed(3, "1 x Attached to bathroom", 10000))
        return list
    }


}