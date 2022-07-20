package com.shahriyor.android_graphql

import com.shahriyor.android_graphql.network.GraphQL
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkUsersListSize() = runTest {
        val response = GraphQL.get().query(UserListQuery(12)).execute()
        assertEquals(10, response.data!!.users.size)
    }

    @Test
    fun checkInsertUser() = runTest {
        val response = GraphQL.get().mutation(InsertUserMutation("Salom", "rocket", "twitter")).execute()
        assertEquals(1, response.data?.insert_users?.affected_rows)
    }

    @Test
    fun checkDeleteUser() = runTest {
        val response = GraphQL.get().mutation(DeleteUserMutation("f622720e-63b7-4ff2-9d34-20aa294fc0a2")).execute()
        assertEquals(1, response.data?.delete_users?.affected_rows)

    }
}