package com.rahmi.palindromechecker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahmi.palindromechecker.R
import com.rahmi.palindromechecker.data.User
import com.rahmi.palindromechecker.databinding.ItemUserBinding

class UserAdapter(
    private val clickListener: OnItemClickListener
) : PagingDataAdapter<User, UserAdapter.ChatViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onUserItemClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ChatViewHolder(
        private val binding: ItemUserBinding,
        private val clickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: User) {
            Glide.with(binding.circleImageView.context)
                .load(item.avatar)
                .placeholder(R.drawable.ic_photo)
                .into(binding.circleImageView)

            binding.tvName.text = "${item.firstName} ${item.lastName}"
            binding.tvEmail.text = item.email
            itemView.setOnClickListener {
                clickListener.onUserItemClick(item)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}