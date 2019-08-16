package com.incaze.friendscheckervk.database

import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import com.incaze.friendscheckervk.feed.MainFeed
import com.incaze.friendscheckervk.model.VKUser

class DBExecutor {

    fun insertUserIntoDB(dataHelper: DBHelper, userVK: VKUser){
        val db = dataHelper.writableDatabase
        val content = ContentValues()
        content.put("id", userVK.id)
        content.put("first_name", userVK.first_name)
        content.put("last_name", userVK.last_name)
        content.put("photo", userVK.photo)
        if (userVK.can_access_closed) {
            content.put("can_access_closed", 1)
        } else {
            content.put("can_access_closed", 0)
        }
        content.put("deactivated", userVK.deactivated)
        content.put("domain", userVK.domain)
        db.insert("UsersVK", null, content)
        db.close()
    }

    fun restoreDB(dataHelper: DBHelper, adapter: MainFeed, activity: Activity){
        val db = dataHelper.writableDatabase
        adapter.addListOfUsers(loadUsers(db))
        db.close()
        adapter.setup(activity, adapter, dataHelper)
    }

    fun deleteAllUsersFromDB(dataHelper: DBHelper){
        val db = dataHelper.writableDatabase
        db.delete("UsersVK", null, null)
        db.close()
    }

    private fun loadUsers(db: SQLiteDatabase) : List<VKUser> {
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
                userList.add(
                    VKUser(
                        id = c.getString(id),
                        first_name = c.getString(first_name),
                        last_name = c.getString(last_name),
                        photo = c.getString(photo),
                        can_access_closed = c.getInt(can_access_closed) == 1,
                        deactivated = c.getString(deactivated),
                        domain = c.getString(domain)
                    )
                )
            } while (c.moveToNext())
        }
        c.close()
        return userList
    }

}