package com.shahriyor.android_graphql.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahriyor.android_graphql.UserListQuery
import com.shahriyor.android_graphql.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UserListQuery.User, UserAdapter.UserViewHolder>(Comparator) {

    var itemClick: ((UserListQuery.User) -> Unit)? = null

    object Comparator : DiffUtil.ItemCallback<UserListQuery.User>() {
        override fun areItemsTheSame(
            oldItem: UserListQuery.User,
            newItem: UserListQuery.User,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserListQuery.User,
            newItem: UserListQuery.User,
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserListQuery.User?) {
            binding.apply {
                tvName.text = user?.name.toString()
                tvTwitter.text = user?.twitter.toString()
                tvRocket.text = user?.rocket.toString()

                root.setOnClickListener {
                    itemClick?.invoke(user!!)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}