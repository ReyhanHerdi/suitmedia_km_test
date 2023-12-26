package com.example.suitmediakmtest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediakmtest.data.response.DataItem
import com.example.suitmediakmtest.databinding.UsersListBinding

class DataListAdapter : ListAdapter<DataItem, DataListAdapter.ListViewHolder>(DIFF_CALLBACK){
    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(dataList: DataItem)
    }

    class ListViewHolder(private val binding: UsersListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataList: DataItem) {
            val fullname = "${dataList.firstName} ${dataList.lastName}"
            binding.tvUserName.text = fullname
            binding.tvUserEmail.text = dataList.email
            Glide.with(itemView.context)
                .load(dataList.avatar)
                .into(binding.ivUserImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = UsersListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataList = getItem(position)
        holder.bind(dataList)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(dataList)
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}