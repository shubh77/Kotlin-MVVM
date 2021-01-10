package com.santino.Article.networking

import androidx.lifecycle.MutableLiveData
import com.santino.Article.model.ArticlePojo
import com.santino.Article.networking.APIClient.createService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val apiInterface: APIInterface

    init {
        apiInterface = createService<APIInterface>(APIInterface::class.java)
    }

    companion object {
        var repository: Repository? = null
        val instance: Repository?
            get() {
                if (repository == null) {
                    repository = Repository()
                }
                return repository
            }
    }

    /**
     * This method is used for generating an API Call
     */
    fun getArticles(pageNum: Int, limit: Int): MutableLiveData<ArrayList<ArticlePojo>> {
        val articles = MutableLiveData<ArrayList<ArticlePojo>>()

        apiInterface.getArticlesList(pageNum, limit)!!.enqueue(object: Callback<ArrayList<ArticlePojo>> {
            override fun onResponse(call: Call<ArrayList<ArticlePojo>>, response: Response<ArrayList<ArticlePojo>>) {
                if (response.isSuccessful) {
                    articles.setValue(response.body())
                }
            }
            override fun onFailure(call: Call<ArrayList<ArticlePojo>>, t: Throwable) {
                articles.setValue(null)
            }
        })
        return articles
    }
}