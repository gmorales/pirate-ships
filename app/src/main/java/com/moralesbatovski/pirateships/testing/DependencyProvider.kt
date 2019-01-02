package com.moralesbatovski.pirateships.testing

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okio.Okio
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

/**
 * @author Gustavo Morales
 *
 * Class only usable for testing purposes.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
object DependencyProvider {

    fun getRetrofit(baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS).build())
                .build()
    }

    /**
     * Helper method to read a JSON file and return it as a string
     *
     * @param fileName file's name
     * @return JSON converted to String
     */
    @SuppressLint("NewApi")
    fun getResponseFromJson(fileName: String): String {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("$fileName.json")
        val source = Okio.buffer(Okio.source(inputStream))
        return source.readString(StandardCharsets.UTF_8)
    }
}