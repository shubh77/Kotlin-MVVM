package com.santino.Article

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.santino.Article.adapters.ArticlesAdapter
import com.santino.Article.model.ArticlePojo
import com.santino.Article.viewmodel.ArticleViewModel
import java.lang.Exception

/**
 *
 * This class is used for showing the Dashboard screen.
 * @author Shubham
 * @since 11-Oct-20202
 */
class MainActivity : AppCompatActivity() {
    var articleList:ArrayList<ArticlePojo> = ArrayList<ArticlePojo>()
    var adapter: ArticlesAdapter ?= null
    var rvArticle: RecyclerView ?= null
    var viewModel: ArticleViewModel ? = null
    var count:Int = 0
    var currentFirstVisible: Int = 0
    var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvArticle = findViewById(R.id.rvArticle)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        callAPI()
        setupRecyclerView()
    }

    /**
     * This method is used for setting up the Recycler View.
     */
    fun setupRecyclerView() {
        val linearLayoutManager:LinearLayoutManager = LinearLayoutManager(this)
        if (adapter == null) {
            adapter = ArticlesAdapter(this, articleList)
            rvArticle!!.layoutManager = LinearLayoutManager(this)
            rvArticle!!.adapter = adapter
            rvArticle!!.itemAnimator = DefaultItemAnimator()
            rvArticle!!.isNestedScrollingEnabled = true
            linearLayoutManager.orientation = RecyclerView.VERTICAL
            rvArticle!!.layoutManager = linearLayoutManager
            rvArticle!!.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        } else {
            adapter!!.notifyDataSetChanged()
        }
        val firstVisibleInListview = IntArray(1)
        firstVisibleInListview[0] = linearLayoutManager.findFirstVisibleItemPosition()
        rvArticle!!.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                currentFirstVisible = linearLayoutManager.findFirstVisibleItemPosition()
                if (currentFirstVisible > firstVisibleInListview[0])
                    Log.i("RecyclerView scrolled: ", "scroll up!")
                else {
                    if (currentFirstVisible == currentPosition + 7) {
                        Log.i("MainActivity", " recyclerView: "+currentFirstVisible +" ,firstVisible: " +firstVisibleInListview[0]+ ", pos: "+currentPosition)
                        currentPosition = currentFirstVisible
                        callAPI()
                    }
                }
                firstVisibleInListview[0] = currentFirstVisible
            }
        })
    }

    /**
     * This method is used for making an API Call to fetch the Articles List.
     */
    fun callAPI() {
        if (isNetworkAvailable()) {
            viewModel!!.init(++count)
            viewModel!!.getRepository()!!
                .observe(this, Observer { response: java.util.ArrayList<ArticlePojo> ->
                    Log.i("RecyclerView scrolled: ", "scroll up! API Call :"+articleList.size+ " , "+response.size + ", count: "+ count)
                    articleList.addAll(response)
                    adapter!!.notifyDataSetChanged()
                })
        } else {
            Toast.makeText(this, "Network not available", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is used to check that Internet is currently available or not.
     * @return Boolean value ->  True- Internet connected and False- Internet not connected.
     */
    fun isNetworkAvailable() : Boolean {
        try {
            val connectivityManager: ConnectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
            val networkInfo: NetworkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}