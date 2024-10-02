package com.example.homework_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {

    private lateinit var course1EditText: EditText
    private lateinit var course2EditText: EditText
    private lateinit var course3EditText: EditText
    private lateinit var course4EditText: EditText
    private lateinit var course5EditText: EditText
    private lateinit var computeButton: Button
    private lateinit var resultTextView: TextView

    private val courseEditTexts by lazy {
        listOf(course1EditText, course2EditText, course3EditText, course4EditText, course5EditText)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupListeners()
    }

    private fun initializeViews() {
        course1EditText = findViewById(R.id.course1EditText)
        course2EditText = findViewById(R.id.course2EditText)
        course3EditText = findViewById(R.id.course3EditText)
        course4EditText = findViewById(R.id.course4EditText)
        course5EditText = findViewById(R.id.course5EditText)
        computeButton = findViewById(R.id.computeButton)
        resultTextView = findViewById(R.id.resultTextView)
    }

    private fun setupListeners() {
        computeButton.setOnClickListener {
            if (validateInputs()) {
                computeGPA()
                computeButton.text = getString(R.string.clear_form)
            }
        }

        courseEditTexts.forEach { editText ->
            editText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    editText.setBackgroundColor(Color.TRANSPARENT)
                    if (computeButton.text == getString(R.string.clear_form)) {
                        computeButton.text = getString(R.string.compute_gpa)
                    }
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        courseEditTexts.forEach { editText ->
            if (editText.text.isNullOrEmpty()) {
                editText.setBackgroundColor(Color.RED)
                isValid = false
            } else {
                val grade = editText.text.toString().toFloatOrNull()
                if (grade == null || grade < 0 || grade > 100) {
                    editText.setBackgroundColor(Color.RED)
                    isValid = false
                }
            }
        }
        return isValid
    }

    private fun computeGPA() {
        val grades = courseEditTexts.map { it.text.toString().toFloat() }
        val gpa = grades.average().toFloat()

        resultTextView.text = getString(R.string.gpa_result, gpa)

        when {
            gpa < 60 -> resultTextView.setBackgroundColor(Color.RED)
            gpa in 60f..79f -> resultTextView.setBackgroundColor(Color.YELLOW)
            else -> resultTextView.setBackgroundColor(Color.GREEN)
        }
    }
}