package org.example.kobableclone.data.repository

import android.content.ContentValues
import org.example.kobableclone.data.local.DatabaseHelper
import org.example.kobableclone.data.model.User
import java.util.*

class UserRepository {
    private val dbHelper = DatabaseHelper.getInstance()

    fun createUser(user: User): Boolean {
        return try {
            dbHelper.writableDatabase.use { db ->
                val values = ContentValues().apply {
                    put("id", user.id)
                    put("username", user.username)
                    put("password", user.password)
                    put("is_parent", user.isParent)
                    put("parent_id", user.parentId)
                }
                db.insert("users", null, values)
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    fun getUserById(userId: String): User? {
        dbHelper.readableDatabase.use { db ->
            val cursor = db.query(
                "users",
                null,
                "id = ?",
                arrayOf(userId),
                null,
                null,
                null
            )

            cursor.use {
                if (it.moveToFirst()) {
                    return User(
                        id = it.getString(it.getColumnIndexOrThrow("id")),
                        username = it.getString(it.getColumnIndexOrThrow("username")),
                        password = it.getString(it.getColumnIndexOrThrow("password")),
                        isParent = it.getInt(it.getColumnIndexOrThrow("is_parent")) == 1,
                        parentId = it.getString(it.getColumnIndexOrThrow("parent_id"))
                    )
                }
            }
        }
        return null
    }

    fun getUserByUsername(username: String): User? {
        dbHelper.readableDatabase.use { db ->
            val cursor = db.query(
                "users",
                null,
                "username = ?",
                arrayOf(username),
                null,
                null,
                null
            )

            cursor.use {
                if (it.moveToFirst()) {
                    return User(
                        id = it.getString(it.getColumnIndexOrThrow("id")),
                        username = it.getString(it.getColumnIndexOrThrow("username")),
                        password = it.getString(it.getColumnIndexOrThrow("password")),
                        isParent = it.getInt(it.getColumnIndexOrThrow("is_parent")) == 1,
                        parentId = it.getString(it.getColumnIndexOrThrow("parent_id"))
                    )
                }
            }
        }
        return null
    }

    fun getChildrenByParentId(parentId: String): List<User> {
        val children = mutableListOf<User>()
        
        dbHelper.readableDatabase.use { db ->
            val cursor = db.query(
                "users",
                null,
                "parent_id = ?",
                arrayOf(parentId),
                null,
                null,
                "username ASC"
            )

            cursor.use {
                while (it.moveToNext()) {
                    children.add(
                        User(
                            id = it.getString(it.getColumnIndexOrThrow("id")),
                            username = it.getString(it.getColumnIndexOrThrow("username")),
                            password = it.getString(it.getColumnIndexOrThrow("password")),
                            isParent = false,
                            parentId = parentId
                        )
                    )
                }
            }
        }
        return children
    }

    fun updateUser(user: User): Boolean {
        return try {
            dbHelper.writableDatabase.use { db ->
                val values = ContentValues().apply {
                    put("username", user.username)
                    put("password", user.password)
                    put("is_parent", user.isParent)
                    put("parent_id", user.parentId)
                }
                
                db.update(
                    "users",
                    values,
                    "id = ?",
                    arrayOf(user.id)
                )
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    fun deleteUser(userId: String): Boolean {
        return try {
            dbHelper.writableDatabase.use { db ->
                db.delete("users", "id = ?", arrayOf(userId))
                true
            }
        } catch (e: Exception) {
            false
        }
    }

    fun validateCredentials(username: String, password: String): User? {
        return getUserByUsername(username)?.let { user ->
            if (user.password == password) user else null
        }
    }
}

