package com.shahriyor.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.shahriyor.android_graphql.DeleteUserMutation
import com.shahriyor.android_graphql.R
import com.shahriyor.android_graphql.UpdateUserMutation
import com.shahriyor.android_graphql.UserByIdQuery
import com.shahriyor.android_graphql.databinding.ActivityUpdateBinding
import com.shahriyor.android_graphql.network.GraphQL
import com.shahriyor.android_graphql.util.logger
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    var id = ""
    private val binding by lazy { ActivityUpdateBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        getUserById(id)

        binding.btnUpdate.setOnClickListener {
            binding.apply {
                val name = etName.text.toString().trim()
                val rocket = etRocket.text.toString().trim()
                val twitter = etTwitter.text.toString().trim()
                updateUser(id, name, rocket, twitter)
                finish()
            }
        }

        binding.btnDelete.setOnClickListener {
            deleteUser(id)
            finish()
        }


    }

    private fun deleteUser(id: String?) {
        lifecycleScope.launch {
            val response = try {
                GraphQL.get().mutation(DeleteUserMutation(id!!)).execute()
            } catch (e: Exception) {
                logger(e.message!!)
            }
        }
    }

    private fun updateUser(id: String?, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().mutation(UpdateUserMutation(id!!, rocket, name, twitter)).execute()
            } catch (e: Exception) {
                logger(e.message!!)
                return@launchWhenResumed
            }
        }
    }

    private fun getUserById(id: String?) {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UserByIdQuery(id!!)).execute()
            } catch (e: Exception) {
                logger(e.message!!)
                return@launchWhenResumed
            }
            val user = response.data?.users_by_pk
            user?.let {
                this@UpdateActivity.id = it.id.toString()
            }
            binding.etName.setText(user?.name)
            binding.etRocket.setText(user?.rocket)
            binding.etTwitter.setText(user?.twitter)
        }

    }
}