package com.example.photogalery

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.photogalery.model.GalleryItem

class PhotoAdapter(private val galleryItem: List<GalleryItem>) :
    RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {

    class PhotoHolder(itemTextView: TextView) : RecyclerView.ViewHolder(itemTextView) {

        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val textView = TextView(parent.context)
        return PhotoHolder(textView)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = galleryItem[position]
        holder.bindTitle(galleryItem.title)
    }

    override fun getItemCount(): Int = galleryItem.size
}
