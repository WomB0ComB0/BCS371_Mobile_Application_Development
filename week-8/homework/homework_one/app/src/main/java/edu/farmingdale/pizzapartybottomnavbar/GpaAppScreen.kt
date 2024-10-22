package edu.farmingdale.pizzapartybottomnavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

// ToDo 4: Match the UI as in drawable gpa_design.png. Use the following hints:
// - The background color should be Color.Cyan
// - Fix padding, alignment, and keypad type

// ToDo 5:  Add the GpaAppScreen composable button that clears the input fields when clicked


@Composable
fun GpaAppScreen() {
    var grade1 by remember { mutableStateOf("") }
    var grade2 by remember { mutableStateOf("") }
    var grade3 by remember { mutableStateOf("") }
    var gpa by remember { mutableStateOf("") }
    var backColor by remember { mutableStateOf(Color.White) }
    var btnLabel by remember { mutableStateOf("Calculate GPA") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GPA Calculator",
                fontSize = 28.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            GradeInput(
                value = grade1,
                onValueChange = { grade1 = it },
                label = "Course 1 Grade"
            )
            GradeInput(
                value = grade2,
                onValueChange = { grade2 = it },
                label = "Course 2 Grade"
            )
            GradeInput(
                value = grade3,
                onValueChange = { grade3 = it },
                label = "Course 3 Grade"
            )

            Button(
                onClick = {
                    if (btnLabel == "Calculate GPA") {
                        val gpaVal = calGPA(grade1, grade2, grade3)
                        if (gpaVal != null) {
                            gpa = String.format("%.2f", gpaVal)
                            backColor = when {
                                gpaVal < 60 -> Color.Red
                                gpaVal in 60.0..79.0 -> Color.Yellow
                                else -> Color.Green
                            }
                            btnLabel = "Clear"
                        } else {
                            gpa = "Invalid input"
                        }
                    } else {
                        grade1 = ""
                        grade2 = ""
                        grade3 = ""
                        gpa = ""
                        backColor = Color.White
                        btnLabel = "Calculate GPA"
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(btnLabel, fontSize = 18.sp)
            }

            if (gpa.isNotEmpty()) {
                Text(
                    text = "GPA: $gpa",
                    fontSize = 24.sp,
                    color = backColor,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun GradeInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}


fun calGPA(grade1: String, grade2: String, grade3: String): Double {
    val grades = listOf(grade1.toDouble(), grade2.toDouble(), grade3.toDouble())
    return grades.average()
}
