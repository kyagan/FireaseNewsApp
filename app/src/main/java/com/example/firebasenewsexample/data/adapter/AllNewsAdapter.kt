package com.example.firebasenewsexample.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasenewsexample.data.model.News
import com.example.firebasenewsexample.data.model.User
import com.example.firebasenewsexample.databinding.CustomAllNewsListBinding


class AllNewsAdapter(
    val context: Context,
    val news:List<News>,
    val onClick:(news: News) ->Unit): RecyclerView.Adapter<AllNewsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            CustomAllNewsListBinding.inflate(LayoutInflater.from(context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = news[position]
        holder.tvTitle.text = news.title
        holder.tvContent.text = news.content

        holder.itemView.setOnClickListener {
            onClick(news)
        }
    }

    class MyViewHolder(private val binding: CustomAllNewsListBinding): RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitle
        val tvContent = binding.tvContent
    }
}