package com.example.photogalery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogalery.model.GalleryItem

class PhotoAdapter(private val galleryItem: List<GalleryItem>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    class PhotoHolder(item: View) : RecyclerView.ViewHolder(item) {

        val itemImageView: ImageView=item.findViewById(R.id.image_recycler)
        fun bindImage(galleryItem: GalleryItem) {
            val url=galleryItem.url
            Glide.with(itemImageView)
                .load(url)
                .placeholder(R.drawable.bill_up_close)
                .error(R.drawable.bill_up_close)
                .fallback(R.drawable.bill_up_close)
                .into(itemImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_gallery, parent, false)
        return PhotoHolder(v)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = galleryItem[position]
        holder.bindImage(galleryItem)
    }

    override fun getItemCount(): Int = galleryItem.size
}
