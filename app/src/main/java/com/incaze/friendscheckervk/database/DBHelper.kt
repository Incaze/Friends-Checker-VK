package com.incaze.friendscheckervk.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser

class DBHelper(context: Context) : SQLiteOpenHelper(context, context.getString(R.string.db_name), null, 1) {

    override fun onCreate(db:SQLiteDatabase) {
      db.execSQL(
          "create table UsersVK ("
                  + "key integer primary key autoincrement,"
                  + "id text,"
                  + "first_name text,"
                  + "last_name text,"
                  + "photo text,"
                  + "can_access_closed integer,"
                  + "deactivated text,"
                  + "domain text" + ");"
      )
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {

    }


}