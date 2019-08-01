package com.incaze.friendscheckervk

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.squareup.picasso.Picasso


open class AdapterData:  RecyclerView.Adapter<AdapterData.ViewHolder>() {
    var users: MutableList<VKUser> = arrayListOf()

    open class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
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

        open fun bind(user: VKUser) {
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

    override fun getItemCount() = users.size
}
