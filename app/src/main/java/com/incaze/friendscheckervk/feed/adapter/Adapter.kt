package com.incaze.friendscheckervk.feed.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.incaze.friendscheckervk.R
import com.incaze.friendscheckervk.model.VKUser
import com.incaze.friendscheckervk.database.DBHelper
import com.incaze.friendscheckervk.util.Toast

@Suppress("UNCHECKED_CAST")
abstract class Adapter<VH : RecyclerView.ViewHolder>:  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var users: MutableList<VKUser> = arrayListOf()
    var onItemClick: ((VKUser) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindHolder(holder as VH, users[position], position)
    }

    override fun getItemCount() = users.size

    abstract fun onCreateHolder(parent: ViewGroup): VH
    abstract fun onBindHolder(viewHolder: VH, data: VKUser, position: Int)

    abstract class ViewHolder(v: View, onItemClick: ((VKUser) -> Unit)?, users: MutableList<VKUser>) : RecyclerView.ViewHolder(v){

        val userSurname: TextView
        val userName: TextView
        val userPhoto: ImageView
        val errorUser: ImageButton

        init {
            userSurname = v.findViewById(R.id.user_surname)
            userName = v.findViewById(R.id.user_name)
            userPhoto = v.findViewById(R.id.user_photo)
            errorUser = v.findViewById(R.id.error_user)

            userSurname.setOnClickListener {
                onItemClick?.invoke(users[adapterPosition])
            }
            userName.setOnClickListener {
                onItemClick?.invoke(users[adapterPosition])
            }
            userPhoto.setOnClickListener{
                onItemClick?.invoke(users[adapterPosition])
            }
            errorUser.setOnClickListener{
                val toast = Toast()
                toast.showToast(it.context, it.resources.getString(R.string.error_user))
            }
        }

        abstract fun bind(user: VKUser)

    }

    abstract fun setup(activity: Activity, adapter: Adapter<VH>, dbHelper: DBHelper? = null)

    fun returnListOfUsers() : MutableList<VKUser>{
        return users
    }

    fun addListOfUsers(users: List<VKUser>){
        this.users.removeAll(this.users)
        this.users.addAll<VKUser>(users)
    }

}