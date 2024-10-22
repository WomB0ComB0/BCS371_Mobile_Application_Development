package edu.farmingdale.pizzapartybottomnavbar

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ToDo 2: the slider should be able to change the text value of the screen

// ToDo 3: Make the UI look better by adding a gradient background (vertical) and padding

@Composable
fun Screen3() {
    var sliderValue by remember { mutableStateOf(0.5f) }
    var chkd by remember { mutableStateOf(true) }

    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F7FA), Color(0xFF80DEEA))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it },
                modifier = Modifier.fillMaxWidth(),
                enabled = chkd
            )

            Text(
                fontSize = 24.sp,
                text = "Slider Value: ${(sliderValue * 100).toInt()}%",
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Button(
                onClick = {
                    val newInt = Intent(Intent.ACTION_VIEW)
                    newInt.data = Uri.parse("tel:6314202000")
                    context.startActivity(newInt)
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(fontSize = 20.sp, text = "Call me")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Checkbox(
                    checked = chkd,
                    onCheckedChange = { chkd = it }
                )
                Text("Enable Slider", fontSize = 18.sp)
            }
        }
    }
}






