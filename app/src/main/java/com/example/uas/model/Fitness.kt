package com.example.uas.model

import com.google.gson.annotations.SerializedName

data class Fitness(
    @SerializedName("_id") val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category")
    val category: String?,
    @SerializedName("durORrep")
    val durORrep: String,
)
