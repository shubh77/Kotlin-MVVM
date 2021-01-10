package com.santino.Article.model

class MediaPojo {
    var createdAt: String? = null
    var image: String? = null
    var id: String? = null
    var title: String? = null
    var blogId: String? = null
    var url: String? = null

    override fun toString(): String {
        return "MediaPojo(createdAt=$createdAt, image=$image, id=$id, title=$title, blogId=$blogId, url=$url)"
    }
}
