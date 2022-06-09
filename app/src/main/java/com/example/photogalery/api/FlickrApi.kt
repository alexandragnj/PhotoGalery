package com.example.photogalery.api

import retrofit2.Call
import retrofit2.http.GET

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=1cf0ad9cddf73538f5fc5fdf681e3d6c" +
                "&format=json" +
                "&nonjsoncallback=1" +
                "&extras=url_s"
    )
    fun fetchPhotos(): Call<FlickrResponse>
}