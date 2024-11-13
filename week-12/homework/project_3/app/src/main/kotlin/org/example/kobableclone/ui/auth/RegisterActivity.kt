package org.example.kobableclone.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.example.kobableclone.R
import org.example.kobableclone.data.model.User
import org.example.kobableclone.data.repository.UserRepository
import java.util.UUID

class RegisterActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var isParentSwitch: Switch
    private lateinit var registerButton: Button
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val isChild = intent.getBooleanExtra("isChild", false)
        val parentId = intent.getStringExtra("parentId")

        initializeViews()
        setupUI(isChild)
        setupListeners(isChild, parentId)
    }

    private fun initializeViews() {
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        isParentSwitch = findViewById(R.id.isParentSwitch)
        registerButton = findViewById(R.id.registerButton)
    }

    private fun setupUI(isChild: Boolean) {
        isParentSwitch.isChecked = !isChild
        isParentSwitch.isEnabled = !isChild
    }

    private fun setupListeners(isChild: Boolean, parentId: String?) {
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                id = UUID.randomUUID().toString(),
                username = username,
                password = password,
                isParent = !isChild,
                parentId = parentId
            )

            userRepository.insert(user)
            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()

            if (isChild) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("isChild", true)
                intent.putExtra("parentId", user.id)
                startActivity(intent)
                finish()
            }
        }
    }
}

