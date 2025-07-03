package com.daoranews.model

data class Article(
    val id: Int,
    val title: String,
    val snippet: String,
    val author: String,
    val date: String,
    val readTimeInMinutes: Int,

    val fullContent: String
)
