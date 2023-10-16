package com.stayhook.screen.onboarding

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.viewpager2.widget.ViewPager2
import com.stayhook.MainActivity
import com.stayhook.R
import com.stayhook.adapter.OnBoardingAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.ActivityOnBoardingBinding
import com.stayhook.model.OnBoarding
import com.stayhook.screen.login.LoginActivity

class OnBoardingActivity : BaseActivity() {
    private lateinit var onBoardBinding: ActivityOnBoardingBinding
    private var currentItemPos = 0
    override val layoutId: Int
        get() = R.layout.activity_on_boarding

    override fun onViewInit(binding: ViewDataBinding?) {
        onBoardBinding = binding as ActivityOnBoardingBinding
    }

    override fun onResume() {
        super.onResume()
        val adapter = OnBoardingAdapter(getOmnBoardList(), this@OnBoardingActivity)
        onBoardBinding.vPageA2OnBoard.adapter = adapter
        if ( onBoardBinding.vPageA2OnBoard.currentItem==2){
            onBoardBinding.btnGetStartedONBoarding.visibility = View.VISIBLE
            onBoardBinding.ivNextBtnONBoarding.visibility= View.GONE
        }else{
            onBoardBinding.btnGetStartedONBoarding.visibility = View.GONE
            onBoardBinding.ivNextBtnONBoarding.visibility= View.VISIBLE
        }
        onBoardBinding.vPageA2OnBoard.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                currentItemPos = position
                Log.d("TAG", "onPageScrolled: position $position")
                if (position==2){
                    onBoardBinding.btnGetStartedONBoarding.visibility = View.VISIBLE
                    onBoardBinding.ivNextBtnONBoarding.visibility= View.GONE
                }else{
                    onBoardBinding.btnGetStartedONBoarding.visibility = View.GONE
                    onBoardBinding.ivNextBtnONBoarding.visibility= View.VISIBLE
                }
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        onBoardBinding.indicator.setViewPager(onBoardBinding.vPageA2OnBoard)
        onBoardBinding.ivNextBtnONBoarding.setOnClickListener {
            if (onBoardBinding.vPageA2OnBoard.currentItem!=2){
                onBoardBinding.vPageA2OnBoard.currentItem++
            }
        }
        onBoardBinding.btnGetStartedONBoarding.setOnClickListener {
            launchActivity(LoginActivity::class.java)
            finish()

        }

        onBoardBinding.tvSKipBtnTXt.setOnClickListener {
            launchActivity(LoginActivity::class.java)
            finish()
        }
    }

    private fun getOmnBoardList(): MutableList<OnBoarding> {
        val list = mutableListOf<OnBoarding>()
        list.add(
            OnBoarding(
                R.drawable.intro_one,
                getString(R.string.intro)
            )
        )
        list.add(
            OnBoarding(
                R.drawable.intro_two,
                getString(R.string.intro)
            )
        )
        list.add(
            OnBoarding(
                R.drawable.intro_three,
                getString(R.string.intro)
            )
        )
        return list
    }
}