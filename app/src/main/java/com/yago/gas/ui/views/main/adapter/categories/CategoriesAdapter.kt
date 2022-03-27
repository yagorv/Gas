package com.yago.gas.ui.views.main.adapter.categories

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yago.gas.R
import com.yago.gas.domain.customdata.StoreCategory

class CategoriesAdapter(private val context: Context?) :
    ListAdapter<StoreCategory, CategoriesViewHolder>(

        object : DiffUtil.ItemCallback<StoreCategory>() {
            override fun areItemsTheSame(oldItem: StoreCategory, newItem: StoreCategory): Boolean =
                oldItem === newItem

            override fun areContentsTheSame(
                oldItem: StoreCategory,
                newItem: StoreCategory
            ): Boolean =
                oldItem == newItem
        }) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.category_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val category = getItem(position)
        holder.apply {
            bind(createOnClickListener(category), category)
            itemView.tag = category
        }
    }

    private fun createOnClickListener(category: StoreCategory): View.OnClickListener {
        return View.OnClickListener {
            context.let {
                listener?.onclick(category)
            }
        }
    }

    interface Listener {
        fun onclick(category: StoreCategory)
    }

}