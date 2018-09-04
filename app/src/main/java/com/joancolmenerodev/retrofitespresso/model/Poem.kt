package com.joancolmenerodev.retrofitespresso.model

data class Poem (
    var title: String? = null,
    var author: String? = null,
    var lines: List<String>? = null,
    var linecount: String? = null)
