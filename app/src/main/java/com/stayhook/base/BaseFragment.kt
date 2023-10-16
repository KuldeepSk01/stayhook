package com.stayhook.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.stayhook.MainActivity
import com.stayhook.R
import org.koin.core.component.KoinComponent

abstract class BaseFragment : Fragment(), KoinComponent, View.OnClickListener {

    lateinit var baseActivity: BaseActivity
    private lateinit var binding: ViewDataBinding

    abstract fun getLayoutId(): Int
    abstract fun onInitView(binding: ViewDataBinding, view: View)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity)
            baseActivity = context
    }


    open fun hideTab() {
        if (baseActivity is MainActivity) {
            MainActivity.mainActivityBinding.bottomCoordinateLayout.visibility = View.GONE
        }
    }


    open fun showTab() {
        if (baseActivity is MainActivity) {
            MainActivity.mainActivityBinding.bottomCoordinateLayout.visibility = View.VISIBLE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitView(binding, view)
    }


    fun onBackPress() {
        baseActivity.onBackPressedDispatcher.onBackPressed()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ivToolBarBack -> {
                if (parentFragmentManager.backStackEntryCount >= 1) {
                    parentFragmentManager.popBackStack()
                } else {
                    baseActivity.finish()
                }
            }
        }

    }


    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        addToBackStack: String? = null
    ) {
        val fm = parentFragmentManager
        val bt = fm.beginTransaction()
        bt.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        bt.replace(containerId, fragment)
        bt.addToBackStack(addToBackStack)
        bt.commit()

    }

    fun replaceFragment(
        containerId: Int,
        fragment: Fragment,
        b: Bundle,
        addToBackStack: String? = null
    ) {
        val bt = parentFragmentManager.beginTransaction()
        bt.setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
        fragment.arguments = b
        bt.replace(containerId, fragment)
        bt.addToBackStack(addToBackStack)
        bt.commit()

    }



    fun launchActivity(classType: Class<out BaseActivity>) {
        val intent = Intent(baseActivity, classType)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)

    }

}