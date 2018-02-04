package com.longdy.kotlinrxandroidmvp.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.longdy.kotlinrxandroidmvp.R
import com.longdy.kotlinrxandroidmvp.model.DataUser
import com.longdy.kotlinrxandroidmvp.presenter.UserContract
import com.longdy.kotlinrxandroidmvp.presenter.UserPresenter
import com.longdy.kotlinrxandroidmvp.ui.adapter.ListUserAdapter
import com.longdy.kotlinrxandroidmvp.util.UsersSearchEngine
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), UserContract.View {

    var listUserAdapter = ListUserAdapter()
    val presenter: UserContract.Presenter = UserPresenter()
    protected lateinit var usersSearchEngine: UsersSearchEngine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.subscribe()
        presenter.attachView(this)
        initView()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }

    fun initView(){
        showProgress()
        presenter.loadUsersApi()


    }

    override fun onLoadUserOk(users: List<DataUser.User>) {
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = listUserAdapter

        listUserAdapter.users = users

        usersSearchEngine = UsersSearchEngine(users)

    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showSearchResult(result: List<DataUser.User>) {

        if (result.isEmpty()) {
            Toast.makeText(this, R.string.nothing_found, Toast.LENGTH_SHORT).show()
        }
        listUserAdapter.users = result
    }

    private fun createButtonClickObservable(): Observable<String> {

        return Observable.create { emmiter ->
            searchButton!!.setOnClickListener{
                emmiter.onNext(queryEditText.text.toString())
            }

            emmiter.setCancellable{
                searchButton!!.setOnClickListener(null)
            }

        }
    }
    override fun onStart() {
        super.onStart()
        val searchTextObservable = createButtonClickObservable()

        searchTextObservable
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext { showProgress() }
                .observeOn(Schedulers.io())
                .map{usersSearchEngine.search(it)} //= {query -> cheeseSearchEngine.search(query)}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    hideProgress()
                    showSearchResult(it) }

    }

}
