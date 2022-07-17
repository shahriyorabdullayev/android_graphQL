package com.shahriyor.android_graphql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.shahriyor.android_graphql.activity.CreateActivity
import com.shahriyor.android_graphql.activity.UpdateActivity
import com.shahriyor.android_graphql.adapter.UserAdapter
import com.shahriyor.android_graphql.databinding.ActivityMainBinding
import com.shahriyor.android_graphql.network.GraphQL
import com.shahriyor.android_graphql.util.launchActivity
import com.shahriyor.android_graphql.util.logger
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val userAdapter by lazy { UserAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        getUserList()
        clickListener()
    }

    override fun onRestart() {
        super.onRestart()
        getUserList()
    }

    private fun setupRecyclerView() {
        binding.rvMain.adapter = userAdapter
    }

    private fun clickListener() {
        binding.fb.setOnClickListener {
            launchActivity(CreateActivity::class.java)
        }

        userAdapter.itemClick = {
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("id", it.id.toString())
            startActivity(intent)
        }
    }

    private fun getUserList(){
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UserListQuery(20)).execute()
            } catch (e: ApolloException) {
                logger(e.message!!)
                return@launchWhenResumed
            }
            val users = response.data?.users
            userAdapter.submitList(users)
        }
    }
}