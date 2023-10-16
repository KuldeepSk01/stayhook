package com.stayhook.screen.dashboard.home

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.SeeAllItemAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSeeAllItemsBinding
import com.stayhook.model.Recommendation

class SeeAllItemsFragment : BaseFragment() {
    private lateinit var seeAllBinding: FragmentSeeAllItemsBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_see_all_items
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        seeAllBinding = binding as FragmentSeeAllItemsBinding
        seeAllBinding.apply {
            toolBarSeeAllItem.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.see_all_text)
            }

            rvSeeAll.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = SeeAllItemAdapter(getRecommendationList(), requireContext())

            }

        }


    }

    private fun getRecommendationList(): MutableList<Recommendation> {
        val list = mutableListOf<Recommendation>()
        list.add(
            Recommendation(
                "https://images.oyoroomscdn.com/uploads/hotel_image/168652/c4f2cc00b8fb02ba.jpg",
                "Abok hook",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "Men"
            )
        )
        list.add(
            Recommendation(
                "https://i0.wp.com/stanzaliving.wpcomstaging.com/wp-content/uploads/2022/04/d601b-hostels-vs-pgs-min.jpg?fit=4000%2C3000&ssl=1",
                "Kuldeep's hotel",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "Women"
            )
        )
        list.add(
            Recommendation(
                "https://content3.jdmagicbox.com/comp/chennai/g8/044pxx44.xx44.180125140725.t6g8/catalogue/victorias-in-ladies-and-pg-hostel-sirucheri-chennai-hostel-for-girl-students-c72xw2x314.jpg?clr=",
                "Raju's",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "Women"
            )
        )
        list.add(
            Recommendation(
                "https://www.thehivehostels.com/uploads/images/1658301040_7796f3aa4d7819a2f5d5.jpeg",
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5",
                "Men"
            )
        )
        return list
    }


}