package com.santino.Article.model

import java.util.ArrayList

class ArticlePojo {

    var createdAt: String? = null
    var comments: String? = null
    var id: String? = null
    var media: ArrayList<MediaPojo>? = null
    var user: ArrayList<UserPojo>? = null
    var content: String? = null
    var likes: String? = null

    override fun toString(): String {
        return "ArticlePojo(createdAt=$createdAt, comments=$comments, id=$id, media=$media, user=$user, content=$content, likes=$likes)"
    }
}