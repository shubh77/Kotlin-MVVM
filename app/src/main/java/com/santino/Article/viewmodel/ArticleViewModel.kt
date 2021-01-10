package com.santino.Article.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.santino.Article.model.ArticlePojo
import com.santino.Article.networking.Repository

/**
 * This class represents our ViewModel.
 */
class ArticleViewModel :ViewModel() {
    var mutableLiveData: MutableLiveData<ArrayList<ArticlePojo>> ?= null
    var repository: Repository?= null

    /**
     * This method is used for making an API Call.
     * @param actual page number.
     */
    fun init(pageNum: Int) {
        repository = Repository.instance
        mutableLiveData = repository!!.getArticles(pageNum,10)
    }

    /**
     * This function is used to return the instance of live data.
     */
    fun getRepository(): LiveData<ArrayList<ArticlePojo>>? {
        return mutableLiveData
    }
}