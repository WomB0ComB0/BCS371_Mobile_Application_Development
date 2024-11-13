package org.example.kobableclone.data.model

data class User(
    val id: String,
    val username: String,
    val password: String,
    val isParent: Boolean,
    val parentId: String? = null // For child accounts, links to parent
)

