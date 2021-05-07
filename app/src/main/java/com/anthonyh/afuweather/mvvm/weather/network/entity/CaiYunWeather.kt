package com.anthonyh.afuweather.mvvm.weather.network.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
@author Anthony.H
@date: 2021/4/30
@desription:
 */
data class CaiYunWeather(
    val status: String?,
    @SerializedName("server_time") val serverTime: String?,

    val result: Result?,
    val location: DoubleArray?,
    @Expose(serialize = false, deserialize = false) var locationName: String?
) {


}

data class Result(

    val realTime: RealTime?,
    val daily: Daily?,
    val forecast_keypoint: String?
) {


}


//////////////
data class RealTime(
    val status: String?, val temperature: String?, val humidity: Float,
    val skycon: String?, val airQuality: AirQuality?,
) {

}

data class AirQuality(val pm25: String?, val description: Description?) {

}

data class Description(val chn: String?) {

}
////////////////////

data class Minutely(val status: String?, val probability: Array<Float>?) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Minutely

        if (status != other.status) return false
        if (probability != null) {
            if (other.probability == null) return false
            if (!probability.contentEquals(other.probability)) return false
        } else if (other.probability != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status?.hashCode() ?: 0
        result = 31 * result + (probability?.contentHashCode() ?: 0)
        return result
    }
}

data class Hourly(
    val status: String?,
    val description: String?,
    val precipitation: Array<Precipitation>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hourly

        if (status != other.status) return false
        if (description != other.description) return false
        if (precipitation != null) {
            if (other.precipitation == null) return false
            if (!precipitation.contentEquals(other.precipitation)) return false
        } else if (other.precipitation != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status?.hashCode() ?: 0
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (precipitation?.contentHashCode() ?: 0)
        return result
    }

}

data class Precipitation(val datetime: String?, val value: Float?)

////////////
data class Daily(val status: String?, val skycon: Array<Skycon>?, val temperature: Temperature?) {

}

data class Skycon(val date: String?, val value: String?)



data class Temperature(val date: String?, val max: Float?, val min: Float?)

