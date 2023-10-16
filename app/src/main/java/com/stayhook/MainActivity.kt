package com.stayhook

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityMainBinding
import com.stayhook.screen.dashboard.account.AccountFragment
import com.stayhook.screen.dashboard.favorite.FavoriteFragment
import com.stayhook.screen.dashboard.home.HomeFragment
import com.stayhook.screen.dashboard.message.MessageFragment
import com.stayhook.screen.dashboard.search.SearchFragment
import org.koin.core.component.KoinComponent


class MainActivity : BaseActivity(), KoinComponent {
    companion object {
        const val TAG = "MainActivity"
        lateinit var mainActivityBinding: ActivityMainBinding

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
        replaceFragment(homeFragment, HomeFragment::class.java.simpleName, true)
        mainActivityBinding.customBottomBarLayout.tvHomeNav.setTextColor(ContextCompat.getColor(this, R.color.primary_color))
        mainActivityBinding.customBottomBarLayout.ivHomeIcon.setBackgroundResource(R.drawable.ic_home_selected)

        mainActivityBinding.customBottomBarLayout.clHomeNav.setOnClickListener {
            selectedIcon(
                mainActivityBinding.customBottomBarLayout.ivHomeIcon,
                mainActivityBinding.customBottomBarLayout.tvHomeNav
            )
        }
        mainActivityBinding.customBottomBarLayout.clSearchNav.setOnClickListener {
            selectedIcon(
                mainActivityBinding.customBottomBarLayout.ivSearchIcon,
                mainActivityBinding.customBottomBarLayout.tvSearchNav
            )
        }
        mainActivityBinding.customBottomBarLayout.clFavoriteNav.setOnClickListener {
            selectedIcon(
                mainActivityBinding.customBottomBarLayout.ivFavoriteIcon,
                mainActivityBinding.customBottomBarLayout.tvFavoriteNav
            )
        }
        mainActivityBinding.customBottomBarLayout.clMessageNav.setOnClickListener {
            selectedIcon(
                mainActivityBinding.customBottomBarLayout.ivMessageIcon,
                mainActivityBinding.customBottomBarLayout.tvMessageNav
            )
        }
        mainActivityBinding.customBottomBarLayout.clProfileNav.setOnClickListener {
            selectedIcon(
                mainActivityBinding.customBottomBarLayout.ivProfileIcon,
                mainActivityBinding.customBottomBarLayout.tvProfileNav
            )
        }

        /*   mainActivityBinding.bottomNavigationView.isActivated = true
           val nbt = object : NavigationBarView.OnItemSelectedListener {
               override fun onNavigationItemSelected(item: MenuItem): Boolean {
                   when (item.itemId) {
                       R.id.homeNav -> {
                           replaceFragment(homeFragment)
                           Log.d(TAG, "onNavigationItemReselected: Home")
                           return true
                       }

                       R.id.messageNav -> {
                           replaceFragment(messageFragment)
                           Log.d(TAG, "onNavigationItemReselected: Message")
                           return true
                       }

                       R.id.savedFavNav -> {
                           replaceFragment(favoriteFragment)
                           Log.d(TAG, "onNavigationItemReselected: SaveFav")
                           return true
                       }

                       R.id.searchLocationRoomNav -> {
                           replaceFragment(searchFragment)
                           Log.d(TAG, "onNavigationItemReselected: Search")
                           return true
                       }

                       R.id.profileNav -> {
                           replaceFragment(accountFragment)
                           Log.d(TAG, "onNavigationItemReselected: profile")
                           return true
                       }
                   }
                   return false

               }

           }*/
        //mainActivityBinding.bottomNavigationView.setOnItemSelectedListener(nbt)

    }

    private fun selectedIcon(ivIcon: AppCompatImageView, actv: AppCompatTextView) {
        defaultIconsAndTextColor()
        actv.setTextColor(resources.getColor(R.color.primary_color, null))
        when (actv.text.toString()) {
            getString(R.string.title_home) -> {
                replaceFragment(homeFragment, homeFragment.javaClass.simpleName, true)
                ivIcon.setBackgroundResource(R.drawable.ic_home_selected)
            }

            getString(R.string.search) -> {
                replaceFragment(searchFragment, searchFragment.javaClass.simpleName, false)
                ivIcon.setBackgroundResource(R.drawable.ic_search_selected)
            }

            getString(R.string.title_favorite) -> {
                replaceFragment(favoriteFragment, favoriteFragment.javaClass.simpleName, false)
                ivIcon.setBackgroundResource(R.drawable.ic_filled_fav)
            }

            getString(R.string.title_message) -> {
                replaceFragment(messageFragment, messageFragment.javaClass.simpleName, false)
                ivIcon.setBackgroundResource(R.drawable.ic_message_selected)

            }

            getString(R.string.profile) -> {
                replaceFragment(accountFragment, accountFragment.javaClass.simpleName, false)
                ivIcon.setBackgroundResource(R.drawable.ic_user_selected)
            }
        }

    }

    private fun defaultIconsAndTextColor() {
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
    }

    private fun replaceFragment(f: Fragment, addToBackStack: String, flag: Boolean) {
        val bt = supportFragmentManager.beginTransaction()
        bt.replace(R.id.flMainContainer, f)
        bt.commit()
//        if (flag){
//            bt.add(R.id.flMainContainer,f)
//            supportFragmentManager.popBackStack("Home",FragmentManager.POP_BACK_STACK_INCLUSIVE)
//            bt.addToBackStack("Home")
//        }
//        else{
//            bt.replace(R.id.flMainContainer, f)
//        }
//       // bt.addToBackStack(addToBackStack)
//        bt.commit()
        //replaceFragment(R.id.flMainContainer,f)

    }

}