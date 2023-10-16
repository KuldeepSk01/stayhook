package com.stayhook.screen.dashboard.message.chat

import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentChatBinding
import com.stayhook.model.Message
import com.stayhook.util.serializable

class ChatFragment : BaseFragment() {
    private lateinit var cBinding: FragmentChatBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_chat
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        cBinding = binding as FragmentChatBinding
        val model = arguments?.serializable<Message>("MessageDetail")
        cBinding.apply {
            ivToolBarChatBack.setOnClickListener {
                onBackPress()
            }
            tvToolBarChatTitle.text = model?.name
            if (model?.isOnline!!) {
                tvToolBarChatOnlineStatus.let {
                    it.text = getString(R.string.status_Online)
                    it.setTextColor(ResourcesCompat.getColor(resources, R.color.green_color, null))
                }
            } else {
                tvToolBarChatOnlineStatus.let {
                    it.text = getString(R.string.status_Offline)
                    it.setTextColor(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.sub_heading_text_color,
                            null
                        )
                    )
                }
            }

        }

    }

}