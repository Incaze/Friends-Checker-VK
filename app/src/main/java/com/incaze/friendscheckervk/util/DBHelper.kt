package com.incaze.friendscheckervk.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser

internal class DBHelper(context: Context) : SQLiteOpenHelper(context, context.getString(R.string.db_name), null, 1) {

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

    fun loadUsers(dbHelper: DBHelper) : List<VKUser> {
        val db = dbHelper.writableDatabase
        val c = db.query(
            "UsersVK",
            null,
            null,
            null,
            null,
            null,
            null
        )
        val userList : MutableList<VKUser> = arrayListOf()
           if (c.moveToFirst()) {
               val id = c.getColumnIndex("id")
               val first_name = c.getColumnIndex("first_name")
               val last_name = c.getColumnIndex("last_name")
               val photo = c.getColumnIndex("photo")
               val can_access_closed = c.getColumnIndex("can_access_closed")
               val deactivated = c.getColumnIndex("deactivated")
               val domain = c.getColumnIndex("domain")
               do {
                   userList.add(VKUser(
                       id = c.getString(id),
                       first_name = c.getString(first_name),
                       last_name = c.getString(last_name),
                       photo = c.getString(photo),
                       can_access_closed = c.getInt(can_access_closed) == 1,
                       deactivated = c.getString(deactivated),
                       domain = c.getString(domain)
                   ))
               } while (c.moveToNext())
           }
        c.close()
        return userList
    }

}