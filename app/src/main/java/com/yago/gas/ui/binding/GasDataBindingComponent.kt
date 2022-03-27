package com.yago.gas.ui.binding

import android.content.Context
import androidx.databinding.DataBindingComponent

class GasDataBindingComponent(context: Context) : DataBindingComponent {

    private val adapter = FragmentBindingAdapters(context)

    override fun getFragmentBindingAdapters() = adapter
}

