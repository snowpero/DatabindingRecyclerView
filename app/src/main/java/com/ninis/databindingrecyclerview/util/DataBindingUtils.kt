package com.ninis.databindingrecyclerview.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ninis.databindingrecyclerview.base.BaseRecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.lang.Exception

class DataBindingUtils {

    companion object {
        @BindingAdapter("loadImgUrl")
        @JvmStatic
        fun loadImgUrl(view: ImageView, url: String?) {
            Picasso.get()
                .load(url)
                .into(view)
        }

        @BindingAdapter("loadImgUrlThumbnail")
        @JvmStatic
        fun loadImgUrlThumbnail(view: ImageView, url: String?, thumbNailUrl: String?) {
            Picasso.get()
                .load(thumbNailUrl)
                .into(view, object : Callback {
                    override fun onSuccess() {
                        Picasso.get()
                            .load(url)
                            .into(view)
                    }

                    override fun onError(e: Exception?) {

                    }
                })
        }

        @BindingAdapter("loadImgUrlRounded")
        @JvmStatic
        fun loadImgUrlRounded(view: ImageView, url: String?) {
            Picasso.get()
                .load(url)
                .transform(RoundedCornersTransformation(4, 2))
                .into(view)
        }

        @BindingAdapter("loadImgUrlCircle")
        @JvmStatic
        fun loadImgUrlCircle(view: ImageView, url: String?) {
            Picasso.get()
                .load(url)
                .transform(CropCircleTransformation())
                .into(view)
        }

        @Suppress("UNCHECKED_CAST")
        @BindingAdapter("setListItems")
        @JvmStatic
        fun setListItems(view: RecyclerView, items: List<Any>?) {
            (view.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
                replaceAll(items)
                notifyDataSetChanged()
            }
        }

        @Suppress("UNCHECKED_CAST")
        @BindingAdapter("addListItems")
        @JvmStatic
        fun addListItems(view: RecyclerView, items: List<Any>?) {
            (view.adapter as? BaseRecyclerView.Adapter<Any, *>)?.run {
                val start = itemCount
                addAll(items)
                notifyItemRangeInserted(start, itemCount)
            }
        }
    }
}