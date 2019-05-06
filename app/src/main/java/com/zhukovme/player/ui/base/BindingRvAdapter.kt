package com.zhukovme.player.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.zhukovme.player.BR

/**
 * Created by Michael Zhukov on 06.05.2019.
 * email: zhukovme@gmail.com
 */
abstract class BindingRvAdapter : RecyclerView.Adapter<BindingRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil
                .inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder,
                                  position: Int) {
        val item = getItemForPosition(position)
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getItemForPosition(position: Int): Any?

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    protected open fun onInitViewHolder(viewHolder: ViewHolder) {
    }

    inner class ViewHolder(private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            onInitViewHolder(this)
        }

        fun bind(item: Any?) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()
        }
    }
}

abstract class BindingSingleRvAdapter(private val layoutId: Int) : BindingRvAdapter() {
    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}
