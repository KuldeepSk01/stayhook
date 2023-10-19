package com.stayhook.screen.dashboard

import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityMainBinding
import com.stayhook.screen.dashboard.account.AccountFragment
import com.stayhook.screen.dashboard.favorite.FavoriteFragment
import com.stayhook.screen.dashboard.home.HomeFragment
import com.stayhook.screen.dashboard.message.MessageFragment
import com.stayhook.screen.dashboard.search.SearchFragment
import com.stayhook.screen.interfaces.DashBoardListener
import org.koin.core.component.KoinComponent


class MainActivity : BaseActivity(), KoinComponent, DashBoardListener {
    private lateinit var dashBoardListener: DashBoardListener

    companion object {
        const val TAG = "MainActivity"
        lateinit var mainActivityBinding: ActivityMainBinding
        const val HOME_FRAGMENT = 1
        const val SEARCH_FRAGMENT = 2
        const val FAVORITE_FRAGMENT = 3
        const val MESSAGE_FRAGMENT = 4
        const val ACCOUNT_FRAGMENT = 5

    }


    private var onBackPressAgain: Boolean = false

    private val homeFragment: HomeFragment by lazy {
        HomeFragment()
    }
    private val searchFragment: SearchFragment by lazy {
        SearchFragment()
    }

    private val messageFragment: MessageFragment by lazy {
        MessageFragment()
    }

    private val favoriteFragment: FavoriteFragment by lazy {
        FavoriteFragment()
    }

    private val accountFragment: AccountFragment by lazy {
        AccountFragment()
    }

    override val layoutId = R.layout.activity_main

    override fun onViewInit(binding: ViewDataBinding?) {
        mainActivityBinding = binding as ActivityMainBinding
        dashBoardListener = this@MainActivity
        dashBoardListener.onBottomIconClick(homeFragment)

        mainActivityBinding.customBottomBarLayout.ivHomeIcon.setBackgroundResource(R.drawable.ic_home_selected)

        mainActivityBinding.customBottomBarLayout.clHomeNav.setOnClickListener {
            dashBoardListener.onBottomIconClick(homeFragment)
        }
        mainActivityBinding.customBottomBarLayout.clSearchNav.setOnClickListener {
            dashBoardListener.onBottomIconClick(searchFragment)
        }
        mainActivityBinding.customBottomBarLayout.clFavoriteNav.setOnClickListener {
            dashBoardListener.onBottomIconClick(favoriteFragment)
        }
        mainActivityBinding.customBottomBarLayout.clMessageNav.setOnClickListener {
            dashBoardListener.onBottomIconClick(messageFragment)
        }
        mainActivityBinding.customBottomBarLayout.clProfileNav.setOnClickListener {
            dashBoardListener.onBottomIconClick(accountFragment)
        }

    }

    /*   private fun selectedIcon(ivIcon: AppCompatImageView, actv: AppCompatTextView) {
           defaultIconsAndTextColor()
           actv.setTextColor(resources.getColor(R.color.primary_color, null))

       }*/

    open fun setBottomStyle(bottomTabItem: Int) {
        mainActivityBinding.customBottomBarLayout.tvHomeNav.setTextColor(
            resources.getColor(
                R.color.sub_heading_text_color, null
            )
        )
        mainActivityBinding.customBottomBarLayout.tvSearchNav.setTextColor(
            resources.getColor(
                R.color.sub_heading_text_color, null
            )
        )
        mainActivityBinding.customBottomBarLayout.tvFavoriteNav.setTextColor(
            resources.getColor(
                R.color.sub_heading_text_color, null
            )
        )
        mainActivityBinding.customBottomBarLayout.tvMessageNav.setTextColor(
            resources.getColor(
                R.color.sub_heading_text_color, null
            )
        )
        mainActivityBinding.customBottomBarLayout.tvProfileNav.setTextColor(
            resources.getColor(
                R.color.sub_heading_text_color, null
            )
        )
        mainActivityBinding.customBottomBarLayout.ivHomeIcon.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_home, null)
        mainActivityBinding.customBottomBarLayout.ivSearchIcon.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_search, null)
        mainActivityBinding.customBottomBarLayout.ivFavoriteIcon.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)
        mainActivityBinding.customBottomBarLayout.ivMessageIcon.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_message, null)
        mainActivityBinding.customBottomBarLayout.ivProfileIcon.background =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_user, null)

        when (bottomTabItem) {
            HOME_FRAGMENT -> {
                mainActivityBinding.customBottomBarLayout.ivHomeIcon.setBackgroundResource(R.drawable.ic_home_selected)
                mainActivityBinding.customBottomBarLayout.tvHomeNav.setTextColor(
                    resources.getColor(
                        R.color.primary_color, null
                    )
                )

            }

            SEARCH_FRAGMENT -> {
                mainActivityBinding.customBottomBarLayout.ivSearchIcon.setBackgroundResource(R.drawable.ic_search_selected)
                mainActivityBinding.customBottomBarLayout.tvSearchNav.setTextColor(
                    resources.getColor(
                        R.color.primary_color, null
                    )
                )
            }

            FAVORITE_FRAGMENT -> {
                mainActivityBinding.customBottomBarLayout.ivFavoriteIcon.setBackgroundResource(R.drawable.ic_filled_fav)
                mainActivityBinding.customBottomBarLayout.tvFavoriteNav.setTextColor(
                    resources.getColor(
                        R.color.primary_color, null
                    )
                )
            }

            MESSAGE_FRAGMENT -> {
                mainActivityBinding.customBottomBarLayout.ivMessageIcon.setBackgroundResource(R.drawable.ic_message_selected)
                mainActivityBinding.customBottomBarLayout.tvMessageNav.setTextColor(
                    resources.getColor(
                        R.color.primary_color, null
                    )
                )
            }

            ACCOUNT_FRAGMENT -> {
                mainActivityBinding.customBottomBarLayout.ivProfileIcon.setBackgroundResource(R.drawable.ic_user_selected)
                mainActivityBinding.customBottomBarLayout.tvProfileNav.setTextColor(
                    resources.getColor(
                        R.color.primary_color, null
                    )
                )
            }
        }


    }

    override fun onBottomIconClick(fragment: Fragment) {
        val bt = supportFragmentManager.beginTransaction()
        bt.replace(R.id.flMainContainer, fragment)
        bt.commit()
    }

}