package com.longdy.kotlinrxandroidmvp.presenter

import com.longdy.kotlinrxandroidmvp.basemvp.BaseContract
import com.longdy.kotlinrxandroidmvp.model.DataUser

/**
 * Created by Longdy on 2/4/2018.
 */
class UserContract {
    interface Presenter: BaseContract.Presenter<View>{
        fun loadUsersApi()
    }

    interface View: BaseContract.View{
        fun onLoadUserOk(users: List<DataUser.User>)
        fun showProgress()
        fun hideProgress()
        fun showSearchResult(result: List<DataUser.User>)
    }
}