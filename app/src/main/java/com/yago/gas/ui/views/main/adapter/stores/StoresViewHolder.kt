package com.yago.gas.ui.views.main.adapter.stores

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yago.gas.databinding.StoreListItemBinding
import com.yago.gas.domain.customdata.Store

class StoresViewHolder(val binding: StoreListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(onClick: View.OnClickListener?, item: Store) {
        binding.apply {
            clickListener = onClick
            store = item
        }
    }

}