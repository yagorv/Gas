package com.yago.gas.ui.views.main.adapter.categories

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yago.gas.databinding.CategoryListItemBinding
import com.yago.gas.domain.customdata.StoreCategory

class CategoriesViewHolder(val binding: CategoryListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(onClick: View.OnClickListener?, item: StoreCategory) {
        binding.apply {
            clickListener = onClick
            category = item
        }
    }

}