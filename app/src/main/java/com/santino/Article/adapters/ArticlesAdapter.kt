package com.santino.Article.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.santino.Article.R
import com.santino.Article.model.ArticlePojo
import de.hdodenhof.circleimageview.CircleImageView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class represents the Adapter class using this Recycler View's data will get reflect.
 * @author Shubham
 * @since 11-Oct-20202
 */
class ArticlesAdapter(var context: Context, var articlesList: ArrayList<ArticlePojo>) :
    RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    var likesCount: String? = null
    var commentsCount: String? = null

    /**
     * This method is an overrided function used to inflate views.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_article_item, parent, false)
        return ArticleViewHolder(view)
    }

    /**
     * This method is an overrided function used to get the list count.
     * @return actual size of a list.
     */
    override fun getItemCount(): Int {
        return articlesList.size
    }

    /**
     * This is an overrided function which handles UI.
     * @param holder, View holder instance.
     * @param position, Contains the actual List positions.
     */
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        try {
            holder.tv_time.text = dateConvert(articlesList[position].createdAt) + " hr"
            for (UserPojo in articlesList[position].user!!) {
                holder.tv_UserName.text = UserPojo.name
                holder.tv_Description.text = UserPojo.designation
                setGlideImage(holder.iv_user, UserPojo.avatar)
            }
            for (MediaPojo in articlesList[position].media!!) {
                holder.tv_articleTitle.text = MediaPojo.title
                holder.tv_articleUrl.text = MediaPojo.url
                if (MediaPojo.image != null && MediaPojo.image != "")
                    setGlideImage(holder.iv_article, articlesList[position].media!![0].image)
                else
                    holder.iv_article.visibility = View.GONE
            }
            if (articlesList[position].media!!.size == 0) {
                holder.iv_article.visibility = View.GONE
                holder.tv_articleTitle.visibility = View.GONE
                holder.tv_articleUrl.visibility = View.GONE
            }

            holder.tv_articleContent.text = articlesList[position].content
            likesCount = if (articlesList[position].likes!!.toInt() < 1000)
                articlesList[position].likes
            else {
                articlesList[position].likes!!.toInt().rem(1000).toString() + "K"
            }
            commentsCount = if (articlesList[position].comments!!.toInt() < 1000)
                articlesList[position].comments
            else {
                articlesList[position].comments!!.toInt().rem(1000).toString() + "K"
            }
            holder.tv_like.text = likesCount + " " + context.getString(R.string.likes)
            holder.tv_comment.text = commentsCount + " " + context.getString(R.string.comments)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * This method is used for converting date into hour format
     * @param time, actual date-time format.
     * @return It returns the actual hour.
     */
    fun dateConvert(time: String?) : String {
        var dateFormat : DateFormat
        dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        var date: Date = dateFormat.parse(time)
        var formatter: DateFormat = SimpleDateFormat("HH")
        var hour:String = formatter.format(date)
        return hour
    }

    /**
     * This method is used for setting the Image in Image View using Glide.
     * @param View : contains the Actual ImageView.
     * @param url: contains the actual URL.
     */
    private fun setGlideImage(view: ImageView, url: String?) {
        Glide.with(context)
            .load(url)
            .thumbnail(0.5f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .error(R.mipmap.user_icon)
            .into(view)
    }

    /**
     * This is an inner class for handling Views.
     */
    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_time: TextView
        var iv_user: CircleImageView
        var tv_UserName: TextView
        var tv_Description: TextView
        var iv_article: ImageView
        var tv_articleContent: TextView
        var tv_articleTitle: TextView
        var tv_articleUrl: TextView
        var tv_like: TextView
        var tv_comment: TextView

        init {
            tv_time = itemView.findViewById(R.id.tv_time)
            iv_user = itemView.findViewById(R.id.iv_user)
            tv_UserName = itemView.findViewById(R.id.tv_userName)
            tv_Description = itemView.findViewById(R.id.tv_description)
            iv_article = itemView.findViewById(R.id.iv_article)
            tv_articleContent = itemView.findViewById(R.id.tv_articleContent)
            tv_articleTitle = itemView.findViewById(R.id.tv_articleTitle)
            tv_articleUrl = itemView.findViewById(R.id.tv_articleUrl)
            tv_like = itemView.findViewById(R.id.tv_like)
            tv_comment = itemView.findViewById(R.id.tv_comment)
        }
    }
}