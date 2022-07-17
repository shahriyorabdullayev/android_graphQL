package com.shahriyor.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.shahriyor.android_graphql.InsertUserMutation
import com.shahriyor.android_graphql.R
import com.shahriyor.android_graphql.databinding.ActivityCreateBinding
import com.shahriyor.android_graphql.network.GraphQL
import com.shahriyor.android_graphql.util.logger
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnCreate.setOnClickListener {
            binding.apply {
                val name = etName.text.toString().trim()
                val rocket = etRocket.text.toString().trim()
                val twitter = etTwitter.text.toString().trim()
                createUser(name, rocket, twitter)
                finish()

            }
        }

    }

    private fun createUser(name: String, rocket: String, twitter: String){
        lifecycleScope.launch launchWhenResumed@{
            try {
                val response =
                    GraphQL.get().mutation(InsertUserMutation(name, rocket, twitter)).execute()
                logger("${response.data?.insert_users?.affected_rows}")
            } catch (E: ApolloException) {
                return@launchWhenResumed
            }
        }
    }
}