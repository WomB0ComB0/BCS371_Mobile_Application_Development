package edu.farmingdale.threadsexample

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.farmingdale.threadsexample.ui.theme.ThreadsExampleTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import java.util.Locale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThreadsExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FibonacciDemoNoBgThrd(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun FibonacciDemoWithCoroutine() {
    var answer by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("40") }
    val scope = rememberCoroutineScope()

    Column {
        Row {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Number?") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Button(onClick = {
                scope.launch(Dispatchers.Default) {
                    val num = textInput.toLongOrNull() ?: 0
                    val fibNumber = fibonacci(num)
                    answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
                }
            }) {
                Text("Fibonacci")
            }
        }
        Text("Result: $answer")
    }
}
