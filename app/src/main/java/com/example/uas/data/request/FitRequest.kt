package com.example.uas.data.request

import com.google.gson.annotations.SerializedName

data class FitRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("durORrep")
    val durORrep: String
)
