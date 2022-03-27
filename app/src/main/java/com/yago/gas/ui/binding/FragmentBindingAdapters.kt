package com.yago.gas.ui.binding

import android.content.Context
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.yago.gas.R
import javax.inject.Inject

class FragmentBindingAdapters @Inject constructor(private val context: Context) {

    @BindingAdapter("bindUserImage")
    fun bindUserImage(appCompatImageView: AppCompatImageView, userIsMe: Boolean) {
        if (userIsMe) {
            appCompatImageView.setImageResource(R.mipmap.arrow_left)
        } else {
            appCompatImageView.setImageResource(R.mipmap.arrow_left)
        }
    }
}