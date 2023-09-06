package com.example.firebasenewsexample.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.firebasenewsexample.data.model.News
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.databinding.CustomUsersListBinding

class UserListAdapter(
    val context:Context,
    val users:List<User>,
    val onClick:(user: User) ->Unit):RecyclerView.Adapter<UserListAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CustomUsersListBinding.inflate(LayoutInflater.from(context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = users[position]
        holder.ivUserProfileImage.load(user.profileImageUrl)
        holder.tvUserEmail.text = user.email

        holder.itemView.setOnClickListener {
            onClick(user)
        }
    }

    class MyViewHolder(private val binding:CustomUsersListBinding): ViewHolder(binding.root) {
        val ivUserProfileImage = binding.ivUserProfileImage
        val tvUserEmail = binding.tvUserEmail
    }
}