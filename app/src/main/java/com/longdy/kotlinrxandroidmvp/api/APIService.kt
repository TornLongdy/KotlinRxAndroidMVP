package com.longdy.kotlinrxandroidmvp.api

import com.google.gson.GsonBuilder
import com.longdy.kotlinrxandroidmvp.model.DataUser
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Longdy on 2/4/2018.
 */
class APIService {
    val service : APIServiceInterface
    var baseUrl = "https://reqres.in/api/"

    init {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build()

        service = retrofit.create<APIServiceInterface>(APIServiceInterface::class.java)
    }

    fun loadUsers(): Observable<DataUser> {
        return service.getUser()
    }
}