package com.example.agrinet.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MandiPriceRecord(
    @Json(name = "state") val state: String,
    @Json(name = "district") val district: String,
    @Json(name = "market") val market: String,
    @Json(name = "commodity") val commodity: String,
    @Json(name = "variety") val variety: String,
    @Json(name = "modal_price") val modalPrice: String,
    @Json(name = "min_price") val minPrice: String,
    @Json(name = "max_price") val maxPrice: String
)

@JsonClass(generateAdapter = true)
data class DataGovResponse(
    @Json(name = "status") val status: String? = null,
    @Json(name = "records") val records: List<MandiPriceRecord> = emptyList()
)

@JsonClass(generateAdapter = true)
data class WeatherMain(
    @Json(name = "temp") val temp: Double,
    @Json(name = "humidity") val humidity: Int,
    @Json(name = "feels_like") val feelsLike: Double
)

@JsonClass(generateAdapter = true)
data class WeatherDescription(
    @Json(name = "main") val main: String,
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
)

@JsonClass(generateAdapter = true)
data class WindInfo(
    @Json(name = "speed") val speed: Double
)

@JsonClass(generateAdapter = true)
data class OpenWeatherResponse(
    @Json(name = "name") val cityName: String,
    @Json(name = "main") val main: WeatherMain,
    @Json(name = "weather") val weather: List<WeatherDescription>,
    @Json(name = "wind") val wind: WindInfo? = null
)
