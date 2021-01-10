package com.santino.Article.networking

import com.santino.Article.model.ArticlePojo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList

interface APIInterface {
    @GET("/jet2/api/v1/blogs")
    fun getArticlesList(@Query("page") pageNo: Int,
                        @Query("limit") limit: Int) : Call<ArrayList<ArticlePojo>>
}