package com.longdy.kotlinrxandroidmvp.basemvp

/**
 * Created by Longdy on 2/4/2018.
 */
class BaseContract {

    interface Presenter<in T>{
        fun subscribe()
        fun unSubscribe()
        fun attachView(view : T)
    }

    interface View { }

}