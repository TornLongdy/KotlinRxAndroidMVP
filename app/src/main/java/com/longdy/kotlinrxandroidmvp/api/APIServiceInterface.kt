package com.longdy.kotlinrxandroidmvp.api

import com.longdy.kotlinrxandroidmvp.model.DataUser
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Longdy on 2/4/2018.
 */
interface APIServiceInterface {

    @GET("users?per_page=10")
    fun getUser(): Observable<DataUser>
}