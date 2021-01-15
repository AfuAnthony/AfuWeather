package com.anthonyh.afuweather.place

import com.anthonyh.afuweather.place.entity.City
import com.anthonyh.afuweather.place.entity.County
import com.anthonyh.afuweather.place.entity.Province
import retrofit2.http.GET
import retrofit2.http.Path

interface PlaceAPI {

    @GET("api/china")
    suspend fun getProvinces(): MutableList<Province>

    @GET("api/china/{provinceId}")
    suspend fun getCities(@Path("provinceId") provinceId: Int): MutableList<City>

    @GET("api/china/{provinceId}/{cityId}")
    suspend fun getCounties(
        @Path("provinceId") provinceId: Int,
        @Path("cityId") cityId: Int
    ): MutableList<County>

}