package com.chris.blue.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chris.blue.R
import com.chris.blue.bean.PhotoItem
import kotlinx.android.synthetic.main.gallery_cell.view.*

class GalleryAdapter : ListAdapter<PhotoItem, GalleryAdapter.MyViewHolder>(DiffCallback) {


    /**
     * RecyclerView 优化
     * 1。在onBindViewHolder里设置监听器会导致重复创建对象
     * 在onCreateViewHolder里设置！
     * 2。DiffUtil
     *
     *
     * 为什么ItemDecoration可以绘制分格线
     *
     */


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_cell,parent,false)
        val holder = MyViewHolder(view)
        holder.itemView.setOnClickListener{
            Bundle().apply {
                putParcelable("Photo",getItem(holder.adapterPosition))
                holder.itemView.findNavController().navigate(R.id.action_galleryFragment_to_photoFragment,this);
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.shimmer.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }
        Glide.with(holder.itemView)
                .load(getItem(position).fullViewUrl)
                .placeholder(R.drawable.ic_baseline_gray_24)
                .addListener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false.apply { holder.itemView.shimmer?.stopShimmerAnimation() }
                    }

                })
                .into(holder.itemView.imageView)

    }

    object DiffCallback : DiffUtil.ItemCallback<PhotoItem>() {
        // ===内存地址的比较 ==值的比较
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }

        override fun getChangePayload(oldItem: PhotoItem, newItem: PhotoItem): Any? {
            return super.getChangePayload(oldItem, newItem)
        }

    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}


