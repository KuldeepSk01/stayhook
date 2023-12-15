package com.stayhook.screen.dashboard.search

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import com.google.android.material.slider.LabelFormatter
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.RangeSlider.OnChangeListener
import com.stayhook.R
import com.stayhook.base.BaseFragment
import com.stayhook.databinding.FragmentSearchFilterBinding
import com.stayhook.model.SearchFilter
import java.text.NumberFormat
import java.util.Currency


class SearchFilterFragment : BaseFragment() {
    private lateinit var bindingSearchFilter: FragmentSearchFilterBinding
    override fun getLayoutId(): Int {
        return R.layout.fragment_search_filter
    }

    override fun onInitView(binding: ViewDataBinding, view: View) {
        bindingSearchFilter = binding as FragmentSearchFilterBinding
        bindingSearchFilter.apply {
            /*toolbarFilterSearch.apply {
                ivToolBarBack.setOnClickListener {
                    onBackPress()
                }
                tvToolBarTitle.text = getString(R.string.filter_text)
            }*/



     /*       setBackGround(tvSFilterMale, getString(R.string.gender))
            setBackGround(tvSFilterOneBed, getString(R.string.bedrooms_text))
            setBackGround(tvSFilterYes, getString(R.string.food_available_text))

            ArrayAdapter.createFromResource(
                baseActivity.baseContext, R.array.minimum_amount, android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears.
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner.
                acsMinPrice.adapter = adapter
            }


            ArrayAdapter.createFromResource(
                baseActivity.baseContext,
                R.array.maximus_amount,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears.
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner.
                acsMaxPrice.adapter = adapter
            }
            val onItemSelectLis = object : OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    Log.d("SearchFilter", "onItemSelected: item ${p0?.getItemAtPosition(p2)}")
                    *//*  Toast.makeText(
                          baseActivity.baseContext,
                          "item ${p0?.getItemAtPosition(p2)}",
                          Toast.LENGTH_SHORT
                      ).show()*//*
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
            acsMaxPrice.onItemSelectedListener = onItemSelectLis
*/

            rangeSliderPrice.apply {
                valueTo = 100000.0F
                valueFrom = 0.0F

                setLabelFormatter(object : LabelFormatter{
                    override fun getFormattedValue(value: Float): String {
                        val format = NumberFormat.getCurrencyInstance()
                        format.maximumFractionDigits = 0
                        format.currency = Currency.getInstance("INR")
                        format.format(value.toDouble())
                        return format.format(value.toDouble())
                    }

                })


                    this.addOnChangeListener(object : OnChangeListener{
                        override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
                            tvMinPrice.text = slider.values[0].toString()
                            tvMaxPrice.text = slider.values[1].toString()
                            Log.d("TAG","current value $value  from ${slider.valueFrom} to ${slider.valueTo} and values ${slider.values}")
                        }

                    })

            }
            btnFilterApply.setOnClickListener {
                Toast.makeText(requireContext(),"values ${rangeSliderPrice.values}",Toast.LENGTH_SHORT).show()
                Log.d("TAG","from ${rangeSliderPrice.valueFrom} to ${rangeSliderPrice.valueTo} and values ${rangeSliderPrice.values}")

            }
            llcFilterHouse.setOnClickListener {
                setBackGround(it as LinearLayoutCompat)
            }
            llcFilterPrivateRoom.setOnClickListener {
                setBackGround(it as LinearLayoutCompat)
            }
            llcFilterSharedRoom.setOnClickListener {
                setBackGround(it as LinearLayoutCompat)
            }


         /*   tvSFilterMale.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.gender))
            }
            tvSFilterFemale.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.gender))
            }
            tvSFilterBoth.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.gender))
            }
            tvSFilterYes.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.food_available_text))
            }
            tvSFilterNo.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.food_available_text))
            }
            tvSFilterOneBed.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.bedrooms_text))
            }
            tvSFilterTwoBed.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.bedrooms_text))
            }
            tvSFilterThreeBed.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.bedrooms_text))
            }
            tvSFilterFourBed.setOnClickListener {
                setBackGround(it as AppCompatTextView, getString(R.string.bedrooms_text))
            }
*/

        }
    }

   /* private fun setBackGround(
        tv: AppCompatTextView, filterType: String
    ) {
        bindingSearchFilter.apply {
            when (filterType) {
                getString(R.string.food_available_text) -> {
                    tvSFilterYes.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tvSFilterNo.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tv.setTextColor(resources.getColor(R.color.white, null))
                    tv.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.select_search_filter_bg,
                            null
                        )

                }

                getString(R.string.gender) -> {
                    tvSFilterMale.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tvSFilterFemale.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tvSFilterBoth.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.heading_text_color, null))
                    }
                    tv.setTextColor(resources.getColor(R.color.white, null))
                    tv.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.select_search_filter_bg,
                            null
                        )

                }

                getString(R.string.bedrooms_text) -> {
                    tvSFilterOneBed.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tvSFilterTwoBed.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tvSFilterThreeBed.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }
                    tvSFilterFourBed.apply {
                        background = null
                        setTextColor(resources.getColor(R.color.sub_heading_text_color, null))
                    }

                    tv.setTextColor(resources.getColor(R.color.white, null))
                    tv.background =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.select_search_filter_bg,
                            null
                        )

                }
            }
        }
    }
*/
    private fun setBackGround(llc: LinearLayoutCompat) {
        bindingSearchFilter.apply {
            llcFilterHouse.background = null
            llcFilterPrivateRoom.background = null
            llcFilterSharedRoom.background = null
            llc.background =
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.selected_box_drawable,
                    null
                )

        }
    }
}