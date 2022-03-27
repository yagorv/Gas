package com.yago.gas.ui.views.main.adapter.stores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yago.gas.R
import com.yago.gas.domain.customdata.Store

class StoresAdapter(private val context: Context?) : ListAdapter<Store, StoresViewHolder>(

    object : DiffUtil.ItemCallback<Store>() {
        override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean =
            oldItem == newItem
    }) {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoresViewHolder {
        return StoresViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.store_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StoresViewHolder, position: Int) {
        val trade = getItem(position)
        holder.apply {
            bind(createOnClickListener(trade), trade)
            itemView.tag = trade
        }
    }

    private fun createOnClickListener(store: Store): View.OnClickListener {
        return View.OnClickListener {
            context.let {
                listener?.onclick(store)
            }
        }
    }

    interface Listener {
        fun onclick(store: Store)
    }

}