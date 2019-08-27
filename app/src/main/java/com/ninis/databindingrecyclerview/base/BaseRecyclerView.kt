package com.ninis.databindingrecyclerview.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.functions.Consumer

class BaseRecyclerView {
    class Adapter<ITEM : Any, B : ViewDataBinding>(
        @LayoutRes private val layoutResId: Int,
        private val bindingVariableId: Int? = null,
        private val callBack: Consumer<ITEM>
    ) : RecyclerView.Adapter<ViewHolder<B>>(), View.OnClickListener {


        private val items = mutableListOf<ITEM>()

        fun replaceAll(items: List<ITEM>?) {
            items?.let {
                this.items.clear()
                this.items.addAll(it)
            }
        }

        fun addAll(items: List<ITEM>?) {
            items?.let {
                this.items.addAll(it)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
            val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
            view.setOnClickListener(this)
            return ViewHolder(bindingVariableId, view)
        }

        override fun getItemCount() = items.size

        override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
            holder.onBindViewHolder(items[position], position)
        }

        override fun onClick(v: View?) {
            v?.let {
                callBack.accept(items[it.tag as Int])
            }
        }

    }

    class ViewHolder<B : ViewDataBinding>(
        private val bindingVariableId: Int?,
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding: B = DataBindingUtil.bind(itemView)!!

        fun onBindViewHolder(item: Any?, position: Int) {
            itemView.tag = position
            try {
                bindingVariableId?.let {
                    binding.setVariable(it, item)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}