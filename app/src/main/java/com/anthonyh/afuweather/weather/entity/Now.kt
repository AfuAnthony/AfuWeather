package com.anthonyh.afuweather.weather.entity

import com.google.gson.annotations.SerializedName

data class Now constructor(
    @SerializedName("tmp")
    var temperature: String = ""
) {
    @SerializedName("cond")
    lateinit var more: More

    fun degree() = "$temperature℃"

    inner class More {
        @SerializedName("txt")
        var info = ""
    }
}