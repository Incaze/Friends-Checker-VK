package com.incaze.friendscheckervk

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.squareup.picasso.Picasso


class UserAdapter:  RecyclerView.Adapter<UserAdapter.ViewHolder>() {
      private var users: MutableList<VKUser> = arrayListOf()

        class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userSurname: TextView
        val userName: TextView
        val userPhoto: ImageView
        val errorUser: ImageButton

        init {
            userSurname = v.findViewById(R.id.user_surname)
            userName = v.findViewById(R.id.user_name)
            userPhoto = v.findViewById(R.id.user_photo)
            errorUser = v.findViewById(R.id.error_user)
        }

        fun bind(user: VKUser) {
            userSurname.text = user.lastName
            userName.text = user.firstName
            Picasso.get().load(user.photo).error(R.drawable.ic_error_user).into(userPhoto)
            if (((!user.can_access_closed) or (user.deactivated != ""))) {
                errorUser.visibility = View.VISIBLE
            }
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

    fun addListOfUsers(users: List<VKUser>){
        this.users.removeAll(this.users)
        this.users.addAll<VKUser>(users)
    }

    fun retainListOfUsers(users: List<VKUser>){
        val tmp: MutableList<VKUser> = arrayListOf()
        tmp.addAll<VKUser>(this.users)
        this.users.removeAll(this.users)
        for (i in 0 until tmp.size) {
            for (j in 0 until users.size) {
                if (tmp[i].id == users[j].id) this.users.add(tmp[i])
            }
        }
    }

    fun notifyListOfUsers(){
        notifyDataSetChanged()
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

    fun returnListOfUsers() : MutableList<VKUser>{
        return users
    }

    override fun getItemCount() = users.size
}
