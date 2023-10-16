package com.stayhook.screen.dashboard.favorite

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.FavoriteAdapter
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentFavoriteBinding
import com.stayhook.model.Recommendation

class FavoriteFragment : BaseFragment() {
    private lateinit var favoriteBinding: FragmentFavoriteBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_favorite
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        favoriteBinding = binding as FragmentFavoriteBinding
        favoriteBinding.apply {
            toolBarFavorites.apply {
                ivToolBarBack.visibility = View.VISIBLE
                tvToolBarTitle.text = getString(R.string.title_favorite)
            }

            rvFavoriteNav.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = FavoriteAdapter(getRecommendationList(), requireContext())
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
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://i0.wp.com/stanzaliving.wpcomstaging.com/wp-content/uploads/2022/04/d601b-hostels-vs-pgs-min.jpg?fit=4000%2C3000&ssl=1",
                "Kuldeep's hotel",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://content3.jdmagicbox.com/comp/chennai/g8/044pxx44.xx44.180125140725.t6g8/catalogue/victorias-in-ladies-and-pg-hostel-sirucheri-chennai-hostel-for-girl-students-c72xw2x314.jpg?clr=",
                "Raju's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        list.add(
            Recommendation(
                "https://www.thehivehostels.com/uploads/images/1658301040_7796f3aa4d7819a2f5d5.jpeg",
                "Deep's",
                "single Room",
                "Noida",
                "2222",
                "4.5"
            )
        )
        return list
    }


}