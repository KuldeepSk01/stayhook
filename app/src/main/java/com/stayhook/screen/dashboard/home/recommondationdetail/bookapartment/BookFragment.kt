package com.stayhook.screen.dashboard.home.recommondationdetail.bookapartment

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SelectRoomAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentBookBinding
import com.stayhook.interfaces.OnRoomClickListener
import com.stayhook.model.Room

class BookFragment : BaseFragment(), OnRoomClickListener {

    private lateinit var bookFBinding: FragmentBookBinding
    private var roomType = "Master"

    private val selectBedFragment: SelectBedFragment by lazy {
        SelectBedFragment()
    }
    private val selectDateFragment: SelectDateFragment by lazy {
        SelectDateFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_book
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        bookFBinding = binding as FragmentBookBinding


        bookFBinding.apply {
            toolbarBookApartment.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.select_room)
            }

            btnSelectRoom.setOnClickListener {
                when (roomType) {
                    getString(R.string.master_text) -> {
                        replaceFragments(selectDateFragment)
                    }

                    getString(R.string.guest_text) -> {
                       replaceFragments(selectBedFragment)
                    }

                    getString(R.string.kids_text) -> {
                        replaceFragments(selectBedFragment)
                    }

                }


                Log.d(
                    "RDFragment",
                    "onInitView RDFragment: className ${this@BookFragment.javaClass.simpleName}"
                )
            }
            rvSelectRoom.itemAnimator = DefaultItemAnimator()
            rvSelectRoom.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvSelectRoom.adapter = SelectRoomAdapter(getList(), requireContext(), this@BookFragment)

        }

    }

    private fun getList(): MutableList<Room> {
        val list = mutableListOf<Room>()
        list.add(Room(1, 104, "1 x Attached to bathroom", 25000, getString(R.string.master_text),getString(R.string.private_room)))
        list.add(Room(2, 240, "1 x Attached to bathroom", 25000, getString(R.string.guest_text),getString(R.string.shared_room)))
        list.add(Room(3, 90, "1 x Attached to bathroom", 25000, getString(R.string.kids_text),getString(R.string.shared_room)))
        return list
    }

    override fun onRoomClick(room: Room) {
        roomType = room.roomType!!
    }

   private fun replaceFragments(fragment:Fragment){
       replaceFragment(
           R.id.flMainContainer,
           fragment,
           this@BookFragment.javaClass.simpleName
       )
   }

}
