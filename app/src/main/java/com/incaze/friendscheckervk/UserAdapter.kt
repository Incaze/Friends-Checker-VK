package com.incaze.friendscheckervk

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso

//@Parcelize
class UserAdapter:  RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var users: MutableList<VKUser> = arrayListOf()

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userSurname: TextView
        val userName: TextView
        val userPhoto: ImageView
        var userID: String

        init {
            userSurname = v.findViewById(R.id.user_surname)
            userName = v.findViewById(R.id.user_name)
            userPhoto = v.findViewById(R.id.user_photo)
            userID = ""
        }

        fun bind(user: VKUser) {
            userSurname.text = user.lastName
            userName.text = user.firstName
            Picasso.get().load(user.photo).error(R.drawable.photo_1).into(userPhoto)
            userID = user.id
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.user_item, viewGroup, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addUser(users: VKUser?) {
        this.users.add(users!!)
        notifyItemChanged(getItemCount() - 1)
    }

    fun deleteUser(pos: Int) {
        users.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun isEmpty() : Boolean{
        return users.size == 0
    }

    fun removeAll(){
        users.removeAll(users)
        notifyDataSetChanged()
    }

    override fun getItemCount() = users.size
}
