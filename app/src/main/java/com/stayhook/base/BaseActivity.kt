package com.stayhook.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stayhook.R
import com.stayhook.base.interfaces.BaseInterface
import org.koin.core.component.KoinComponent

abstract class BaseActivity : AppCompatActivity(), BaseInterface, KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = layoutId
        if (layoutId != 0) {
            val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutId)
            onViewInit(binding)
        }
    }


    fun launchActivity(classType: Class<out BaseActivity>) {
        val intent = Intent(this@BaseActivity, classType)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        b: Bundle,
        addToBackStack: String? = null
    ) {
        val bt = supportFragmentManager.beginTransaction()
        bt.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        fragment.arguments = b
        bt.replace(containerId, fragment)
        bt.addToBackStack(addToBackStack)
        bt.commit()

    }

}