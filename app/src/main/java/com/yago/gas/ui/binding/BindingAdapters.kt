package com.yago.gas.ui.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.yago.gas.R
import com.yago.gas.domain.customdata.StoreCategory
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.imageResource

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("categoryColor")
    fun bindCategoryColor(view: View, category: StoreCategory?) {
        when (category) {
            StoreCategory.BEAUTY -> view.backgroundColor =
                ContextCompat.getColor(view.context, R.color.category1)
            StoreCategory.FOOD -> view.backgroundColor = ContextCompat.getColor(
                view.context, R.color.category2
            )
            StoreCategory.SHOPPING -> view.backgroundColor = ContextCompat.getColor(
                view.context, R.color.category3
            )
            StoreCategory.UNKNOWN -> view.backgroundColor = ContextCompat.getColor(
                view.context, R.color.category4
            )
            null -> view.backgroundColor = ContextCompat.getColor(
                view.context,
                R.color.category4
            )
        }
    }

    @JvmStatic
    @BindingAdapter("categoryImage")
    fun bindCategoryImage(view: AppCompatImageView, category: StoreCategory?) {
        when (category) {
            StoreCategory.BEAUTY -> view.imageResource = R.mipmap.truck_white
            StoreCategory.FOOD -> view.imageResource = R.mipmap.catering_white
            StoreCategory.SHOPPING -> view.imageResource = R.mipmap.cart_white
            StoreCategory.UNKNOWN -> view.imageResource = R.mipmap.cart_white
            null -> view.imageResource = R.mipmap.cart_white
        }
    }

    @JvmStatic
    @BindingAdapter("categoryOpaqueImage")
    fun bindCategoryOpaqueImage(view: AppCompatImageView, category: StoreCategory?) {
        when (category) {
            StoreCategory.BEAUTY -> view.imageResource = R.mipmap.truck_colour
            StoreCategory.FOOD -> view.imageResource = R.mipmap.catering_colour
            StoreCategory.SHOPPING -> view.imageResource = R.mipmap.cart_colour
            StoreCategory.UNKNOWN -> view.imageResource = R.mipmap.cart_colour
            null -> view.imageResource = R.mipmap.cart_colour
        }
    }

    @JvmStatic
    @BindingAdapter("categoryName")
    fun bindCategoryName(textView: TextView, category: StoreCategory?) {
        when (category) {
            StoreCategory.BEAUTY -> textView.text =
                textView.context.getString(R.string.category_beauty)
            StoreCategory.FOOD -> textView.text = textView.context.getString(R.string.category_food)
            StoreCategory.SHOPPING -> textView.text =
                textView.context.getString(R.string.category_shopping)
            StoreCategory.UNKNOWN -> textView.text =
                textView.context.getString(R.string.category_unknown)
            null -> textView.text = textView.context.getString(R.string.category_unknown)
        }
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun bindImage(imageView: AppCompatImageView, url: String?) {
        if (url != null) {
            Glide.with(imageView.context).load(url).apply(
                RequestOptions.bitmapTransform(
                    RoundedCorners(16)
                )
            ).listener(getRequestListener()).into(imageView)
                .waitForLayout()
        } else {
            Glide.with(imageView.context).load(R.mipmap.placeholder_empty_image)
                .listener(getRequestListener()).into(imageView).waitForLayout()
        }
    }

    @JvmStatic
    @BindingAdapter("bindDistance")
    fun bindDistance(textView: TextView, distance: Int?) {
        distance?.let {
            if (distance < 1000) {
                textView.text =
                    textView.context.getString(R.string.place_meters, distance.toString())
            } else {
                textView.text =
                    textView.context.getString(R.string.place_kms, (distance / 1000).toString())
            }
        }
    }

    private fun getRequestListener(): RequestListener<Drawable> {
        return object : RequestListener<Drawable> {
            override fun onLoadFailed(
                @Nullable e: GlideException?,
                model: Any,
                target: Target<Drawable>,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

            override fun onResourceReady(
                resource: Drawable,
                model: Any,
                target: Target<Drawable>,
                dataSource: DataSource,
                isFirstResource: Boolean
            ) = false

        }
    }

}
