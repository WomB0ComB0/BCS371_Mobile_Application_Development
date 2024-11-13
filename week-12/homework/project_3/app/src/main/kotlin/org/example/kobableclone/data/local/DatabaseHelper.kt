package org.example.kobableclone.data.local

private const val CREATE_USERS_TABLE = """
    CREATE TABLE users (
        id TEXT PRIMARY KEY,
        username TEXT NOT NULL UNIQUE,
        password TEXT NOT NULL,
        is_parent INTEGER NOT NULL,
        parent_id TEXT,
        FOREIGN KEY (parent_id) REFERENCES users(id)
    )
"""

class DatabaseHelper {

}
