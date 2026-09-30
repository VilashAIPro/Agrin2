package com.example.agrinet.data.remote.api

import com.example.agrinet.data.remote.models.DataGovResponse
import com.example.agrinet.data.remote.models.OpenWeatherResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface DataGovInApiService {
    @GET("resource/9ef84268-d588-465a-a308-a864a43d0070")
    suspend fun getMandiPrices(
        @Query("api-key") apiKey: String,
        @Query("format") format: String = "json",
        @Query("limit") limit: Int = 20,
        @Query("filters[state]") state: String? = null
    ): DataGovResponse
}

interface OpenWeatherApiService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): OpenWeatherResponse
}

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    val dataGovService: DataGovInApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.data.gov.in/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(DataGovInApiService::class.java)
    }

    val openWeatherService: OpenWeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OpenWeatherApiService::class.java)
    }
}
