package com.longdy.kotlinrxandroidmvp.presenter

import com.longdy.kotlinrxandroidmvp.api.APIService
import com.longdy.kotlinrxandroidmvp.model.DataUser
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Longdy on 2/4/2018.
 */
class UserPresenter: UserContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private lateinit var view: UserContract.View

    override fun loadUsersApi() {
        getUsers(true)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun attachView(view: UserContract.View) {
        this.view = view
    }

    //-------------------------------

    fun getUsers(isSubcribes: Boolean){
        if (isSubcribes) {
            var observableFriends = APIService().loadUsers()
            updateView(observableFriends, true)
        }
    }

    fun updateView(observableFriends: Observable<DataUser>, isAPI : Boolean){

        var subscribe =  observableFriends.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({users: DataUser? ->
                    if (isAPI){
                        var listUsers = users!!.user;
                        listUsers?.let {
                            for (users in it) {
                                insertToArrayList(users)
                            }
                        }

                        view.onLoadUserOk(listUsers!!)
                        view.hideProgress()
                    }
                },
                        { t: Throwable? ->
                            view.hideProgress()})
        subscriptions.add(subscribe)
    }

    fun insertToArrayList(users : DataUser.User){
        var listDataUser = ArrayList<DataUser.User>()
        val dataUser = DataUser.User(users.id, users.name)
        listDataUser.add(dataUser)
    }
}