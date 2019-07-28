package com.incaze.friendscheckervk

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class UserAdapter(private val dataSet: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userSurname: TextView
        val userName: TextView
        val userPhoto: ImageView

        init {
            userSurname = v.findViewById(R.id.user_surname)
            userName = v.findViewById(R.id.user_name)
            userPhoto = v.findViewById(R.id.user_photo)
        }

         fun bind(user: User) {
            userSurname.text = user.surname
            userName.text = user.name
            userPhoto.setImageResource(user.photo)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_user_item, viewGroup, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.bind(dataSet[pos])
        viewHolder.itemView.tag = dataSet
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


}