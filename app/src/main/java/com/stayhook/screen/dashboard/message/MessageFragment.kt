package com.stayhook.screen.dashboard.message

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.stayhook.R
import com.stayhook.adapter.MessageAdapter
import com.stayhook.adapter.interfaces.OnMessageClickListener
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentMessageBinding
import com.stayhook.model.Message
import com.stayhook.screen.dashboard.MainActivity
import com.stayhook.screen.dashboard.message.chat.ChatFragment
import com.stayhook.util.IMAGE_1
import com.stayhook.util.IMAGE_2
import com.stayhook.util.IMAGE_3
import com.stayhook.util.IMAGE_4

class MessageFragment : BaseFragment(), OnMessageClickListener {
    private lateinit var mBinding: FragmentMessageBinding
    private lateinit var mainActivity: MainActivity

    override fun getLayoutId(): Int {
        return R.layout.fragment_message
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        mBinding = binding as FragmentMessageBinding
        showTab()
        mainActivity= requireActivity() as MainActivity
        mainActivity.setBottomStyle(4)
        mBinding.apply {
            toolBarMessage.apply {
                ivToolBarBack.visibility = View.INVISIBLE
                tvToolBarTitle.text = getString(R.string.title_message)
            }

            etSearchMessage.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            rvMessage.apply {
                itemAnimator = DefaultItemAnimator()
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = MessageAdapter(getMessageList(),requireContext(),this@MessageFragment)
            }
        }
    }

    private fun getMessageList(): MutableList<Message> {
        val list = mutableListOf<Message>()
        list.add(Message(1,IMAGE_4, "Kuldeep", "Ohh hello boss","",true))
        list.add(Message(2,IMAGE_1, "Boss", "hello boss"))
        list.add(Message(3,IMAGE_2, "Amit", "hii hello "))
        list.add(Message(4,IMAGE_3, "Deep", "Oye hello "))
        return list
    }

    override fun onClickMessage(model: Message) {
        val cf = ChatFragment()
        val b = Bundle()
        hideTab()
        b.putSerializable("MessageDetail",model)
        replaceFragment(R.id.flMainContainer,cf,b,MessageFragment().javaClass.simpleName)
    }

}