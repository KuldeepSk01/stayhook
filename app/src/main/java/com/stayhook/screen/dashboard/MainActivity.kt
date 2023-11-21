package com.stayhook.screen.dashboard

import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityMainBinding
import com.stayhook.permissions.MyPermissions
import com.stayhook.screen.dashboard.account.AccountFragment
import com.stayhook.screen.dashboard.favorite.FavoriteFragment
import com.stayhook.screen.dashboard.home.HomeFragment
import com.stayhook.screen.dashboard.message.MessageFragment
import com.stayhook.screen.dashboard.search.SearchFragment
import com.stayhook.screen.interfaces.DashBoardListener
import org.koin.core.component.KoinComponent


class MainActivity : BaseActivity(), KoinComponent, DashBoardListener, LocationListener {
    private lateinit var dashBoardListener: DashBoardListener
    private var isBackPressed = false
    private lateinit var locationManager: LocationManager

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
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        dashBoardListener = this@MainActivity
        dashBoardListener.onBottomIconClick(homeFragment)

        val userDetail = mPref.getUserDetail()
        Log.d(TAG, "onViewInit: user detail $userDetail")


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
        mainActivityBinding.customBottomBarLayout.apply {

            tvHomeNav.setTextColor(
                resources.getColor(
                    R.color.sub_heading_text_color, null
                )
            )
            tvSearchNav.setTextColor(
                resources.getColor(
                    R.color.sub_heading_text_color, null
                )
            )
            tvFavoriteNav.setTextColor(
                resources.getColor(
                    R.color.sub_heading_text_color, null
                )
            )
            tvMessageNav.setTextColor(
                resources.getColor(
                    R.color.sub_heading_text_color, null
                )
            )
            tvProfileNav.setTextColor(
                resources.getColor(
                    R.color.sub_heading_text_color, null
                )
            )

            ivHomeIcon.background = ResourcesCompat.getDrawable(resources, R.drawable.ic_home, null)
            ivSearchIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_search, null)
            ivFavoriteIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null)
            ivMessageIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_message, null)
            ivProfileIcon.background =
                ResourcesCompat.getDrawable(resources, R.drawable.ic_user, null)

            when (bottomTabItem) {
                HOME_FRAGMENT -> {
                    ivHomeIcon.setBackgroundResource(R.drawable.ic_home_selected)
                    tvHomeNav.setTextColor(
                        resources.getColor(
                            R.color.primary_color, null
                        )
                    )

                }

                SEARCH_FRAGMENT -> {
                    ivSearchIcon.setBackgroundResource(R.drawable.ic_search_selected)
                    tvSearchNav.setTextColor(
                        resources.getColor(
                            R.color.primary_color, null
                        )
                    )
                }

                FAVORITE_FRAGMENT -> {
                    ivFavoriteIcon.setBackgroundResource(R.drawable.ic_filled_fav)
                    tvFavoriteNav.setTextColor(
                        resources.getColor(
                            R.color.primary_color, null
                        )
                    )
                }

                MESSAGE_FRAGMENT -> {
                    ivMessageIcon.setBackgroundResource(R.drawable.ic_message_selected)
                    tvMessageNav.setTextColor(
                        resources.getColor(
                            R.color.primary_color, null
                        )
                    )
                }

                ACCOUNT_FRAGMENT -> {
                    ivProfileIcon.setBackgroundResource(R.drawable.ic_user_selected)
                    tvProfileNav.setTextColor(
                        resources.getColor(
                            R.color.primary_color, null
                        )
                    )
                }
            }

        }


    }

    override fun onBottomIconClick(fragment: Fragment) {
        val bt = supportFragmentManager.beginTransaction()
        bt.replace(R.id.flMainContainer, fragment)
        bt.commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            if (isBackPressed) {
                onBackPressedDispatcher.onBackPressed()
            } else {
                isBackPressed = true
                Toast.makeText(this@MainActivity, "pressed back again to exit", Toast.LENGTH_SHORT)
                    .show()
                Handler(Looper.getMainLooper()).postDelayed({
                    isBackPressed = false
                }, 3000)
            }
        }
    }

    override fun onLocationChanged(p0: Location) {

    }


    private fun requestLocationService() {
        if (locationManager == null) {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                if (MyPermissions.isLocationEnable) {
                   // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1000, this)
                } else {
                    MyPermissions.getLocationPermission(this@MainActivity)
                }
            }else{

            }

        }
    }
}