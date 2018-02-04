package com.longdy.kotlinrxandroidmvp.util

import com.longdy.kotlinrxandroidmvp.model.DataUser

class UsersSearchEngine(private val users: List<DataUser.User>) {

  fun search(query: String): List<DataUser.User> {
//    Thread.sleep(2000)
    return users.filter { it.toLowerCase()!!.contains(query.toLowerCase()) }
  }

}