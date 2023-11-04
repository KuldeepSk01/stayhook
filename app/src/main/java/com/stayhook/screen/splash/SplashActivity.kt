package com.stayhook.screen.splash

import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivitySplashBinding
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.onboarding.OnBoardingActivity
import com.stayhook.util.Constants.PreferenceConstant.IS_LOGIN

class SplashActivity : BaseActivity() {
    private lateinit var splashBinding: ActivitySplashBinding
    private lateinit var mRunnable: Runnable
    private lateinit var mHandler: Handler

    override val layoutId = R.layout.activity_splash

    override fun onViewInit(binding: ViewDataBinding?) {
        splashBinding = binding as ActivitySplashBinding

        mRunnable = Runnable {
            if (mPref[IS_LOGIN, false]) {
                launchActivity(MainActivity::class.java)
                finish()
            } else {
                launchActivity(OnBoardingActivity::class.java)
                finish()
            }

        }
        mHandler = Handler(Looper.getMainLooper())
    }

    override fun onResume() {
        super.onResume()

        mHandler.postDelayed(mRunnable, 3000)
    }


    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(mRunnable)
    }

    override fun onStop() {
        super.onStop()
        mHandler.removeCallbacks(mRunnable)
    }

}