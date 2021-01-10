package com.santino.Article.model

class UserPojo {
    var createdAt: String? = null
    var city: String? = null
    var name: String? = null
    var about: String? = null
    var id: String? = null
    var avatar: String? = null
    var designation: String? = null
    var blogId: String? = null
    var lastname: String? = null

    override fun toString(): String {
        return "UserPojo(createdAt=$createdAt, city=$city, name=$name, about=$about, id=$id, avatar=$avatar, designation=$designation, blogId=$blogId, lastname=$lastname)"
    }
}