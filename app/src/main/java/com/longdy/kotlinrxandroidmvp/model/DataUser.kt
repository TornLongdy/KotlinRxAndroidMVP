package com.longdy.kotlinrxandroidmvp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Longdy on 2/4/2018.
 */
class DataUser {

    @SerializedName("data")
    @Expose
    var user: List<User>? = null

    class User{
        @SerializedName("id")
        @Expose
        var id: String? = null
        @SerializedName("first_name")
        @Expose
        var name: String? = null

        constructor(id: String?, name: String?) {
            this.id = id
            this.name = name
        }
        fun toLowerCase(): String? {return name}
    }

}