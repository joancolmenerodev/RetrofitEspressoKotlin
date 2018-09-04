package com.joancolmenerodev.retrofitespresso.networking

import retrofit2.Call
import retrofit2.http.*
import com.joancolmenerodev.retrofitespresso.model.AuthorResponse
import com.joancolmenerodev.retrofitespresso.model.Poem
import com.joancolmenerodev.retrofitespresso.model.Title


interface PoetryService{
    @GET("author")
    fun getAuthors(): Call<AuthorResponse>

    @GET("author/{name}/title")
    fun getTitle(@Path("name") name: String): Call<List<Title>>

    @GET("author,title/{author};{title}")
    fun getPoem(@Path("author") author: String, @Path("title") title: String): Call<List<Poem>>
}