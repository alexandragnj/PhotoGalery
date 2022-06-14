package com.example.photogalery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.photogalery.model.GalleryItem

class PhotoGalleryViewModel : ViewModel() {

    val galleryItemLiveData: LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()
}
