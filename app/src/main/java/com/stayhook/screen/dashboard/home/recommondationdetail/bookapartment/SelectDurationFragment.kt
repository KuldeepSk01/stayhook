package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SelectDurationAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSelectDurationBinding
import com.stayhook.model.Duration

class SelectDurationFragment : BaseFragment() {
    private lateinit var sdBinding: FragmentSelectDurationBinding
    private val selectRoomActivity : RoomActivity by lazy {
        RoomActivity()
    }
    override fun getLayoutId(): Int {
        return R.layout.fragment_select_duration
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        sdBinding = binding as FragmentSelectDurationBinding
        sdBinding.apply {
            sDToolBar.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.select_duration)
            }
            rvDuration.itemAnimator = DefaultItemAnimator()
            rvDuration.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvDuration.adapter = SelectDurationAdapter(getList(), baseActivity.baseContext)


        }
        sdBinding.btnSelectDurationDetail.setOnClickListener {
            launchActivity(RoomActivity::class.java)
               // replaceFragment(R.id.flMainContainer,selectRoomFragment,this@SelectDurationFragment.javaClass.simpleName)
                Log.d("RDFragment", "onInitView RDFragment: className ${this@SelectDurationFragment.javaClass.simpleName}")
        }

    }

    private fun getList(): MutableList<Duration> {
        val list = mutableListOf<Duration>()
        list.add(Duration(3, 104, 0, 219,36,18,7))
        list.add(Duration(6, 104, 0, 219,36,18,7))
        list.add(Duration(9, 104, 0, 219,36,18,7))
        list.add(Duration(12, 104, 0, 219,36,18,7))
        return list
    }

}