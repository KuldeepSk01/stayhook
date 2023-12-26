package com.stayhook.screen.dashboard.message.chat

import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MessageChatAdapter
import com.stayhook.base.BaseActivity
import com.stayhook.databinding.FragmentChatBinding
import com.stayhook.model.Message
import com.stayhook.util.Constants
import com.stayhook.util.IMAGE_1
import com.stayhook.util.IMAGE_2
import com.stayhook.util.IMAGE_3
import com.stayhook.util.IMAGE_4
import com.stayhook.util.serializable

class ChatActivity : BaseActivity() {

    private lateinit var cBinding: FragmentChatBinding
    override val layoutId = R.layout.fragment_chat

    override fun onViewInit(binding: ViewDataBinding?) {
        cBinding = binding as FragmentChatBinding
        val model = intent.getBundleExtra(Constants.DefaultConstants.BUNDLE)
            ?.serializable<Message>(Constants.DefaultConstants.MODEL_DETAIL)
        cBinding.apply {
            ivToolBarChatBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
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




            rvChat.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, false)
                adapter = MessageChatAdapter(getMessageList(), this@ChatActivity)
            }


        }
    }


    private fun getMessageList(): MutableList<Message> {
        val list = mutableListOf<Message>()
        list.add(Message(1, IMAGE_4, "Kuldeep", "Ohh hello boss", "", true, 1))
        list.add(Message(2, IMAGE_1, "Boss", "hello boss", "20:33 PM", true, 2))
        list.add(Message(2, IMAGE_1, "Boss", "hello boss", "20:33 PM", false, 2))
        list.add(Message(2, IMAGE_1, "Boss", "hello boss", "20:33 PM", false, 2))
        list.add(Message(3, IMAGE_2, "Amit", "hii hello "))
        list.add(Message(4, IMAGE_3, "Deep", "Oye hello "))
        return list
    }


}