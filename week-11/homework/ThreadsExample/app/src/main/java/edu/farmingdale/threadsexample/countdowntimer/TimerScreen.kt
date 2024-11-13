package edu.farmingdale.threadsexample.countdowntimer

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.NumberPicker
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.DecimalFormat
import java.util.Locale
import android.provider.Settings
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import edu.farmingdale.threadsexample.R

@Composable
fun TimerScreen(
    modifier: Modifier = Modifier,
    timerViewModel: TimerViewModel = viewModel()
) {
    val remainingTime = timerViewModel.remainingMillis
    val isRunning = timerViewModel.isRunning
    val progress = if (timerViewModel.totalMillis > 0) {
        remainingTime.toFloat() / timerViewModel.totalMillis.toFloat()
    } else 0f
    val isLastTenSeconds = remainingTime in 1..10000
    
    // Play sound effect when timer reaches 0
    val context = LocalContext.current
    LaunchedEffect(remainingTime) {
        if (remainingTime == 0L && isRunning) {
            MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)?.let { mediaPlayer ->
                mediaPlayer.start()
                mediaPlayer.setOnCompletionListener { 
                    it.release() 
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(300.dp)
        ) {
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxSize(),
                strokeWidth = 8.dp
            )
            Text(
                text = timerText(remainingTime),
                fontSize = if (isLastTenSeconds) 60.sp else 48.sp,
                fontWeight = if (isLastTenSeconds) FontWeight.Bold else FontWeight.Normal,
                color = if (isLastTenSeconds) Color.Red else Color.Black
            )
        }

        TimePicker(
            hour = timerViewModel.selectedHour,
            min = timerViewModel.selectedMinute,
            sec = timerViewModel.selectedSecond,
            onTimePick = timerViewModel::selectTime
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Button(
                onClick = timerViewModel::startTimer,
                enabled = !isRunning
            ) {
                Text("Start")
            }
            
            Button(
                onClick = timerViewModel::cancelTimer,
                enabled = isRunning
            ) {
                Text("Reset")
            }
        }
    }
}



fun timerText(timeInMillis: Long): String {
    val duration: Duration = timeInMillis.milliseconds
    return String.format(
        Locale.getDefault(),"%02d:%02d:%02d",
        duration.inWholeHours, duration.inWholeMinutes % 60, duration.inWholeSeconds % 60)
}

@Composable
fun TimePicker(
    hour: Int = 0,
    min: Int = 0,
    sec: Int = 0,
    onTimePick: (Int, Int, Int) -> Unit = { _: Int, _: Int, _: Int -> }
) {
    // Values must be remembered for calls to onPick()
    var hourVal by remember { mutableIntStateOf(hour) }
    var minVal by remember { mutableIntStateOf(min) }
    var secVal by remember { mutableIntStateOf(sec) }

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Hours")
            NumberPickerWrapper(
                initVal = hourVal,
                maxVal = 99,
                onNumPick = {
                    hourVal = it
                    onTimePick(hourVal, minVal, secVal)
                }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Text("Minutes")
            NumberPickerWrapper(
                initVal = minVal,
                onNumPick = {
                    minVal = it
                    onTimePick(hourVal, minVal, secVal)
                }
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Seconds")
            NumberPickerWrapper(
                initVal = secVal,
                onNumPick = {
                    secVal = it
                    onTimePick(hourVal, minVal, secVal)
                }
            )
        }
    }
}

@Composable
fun NumberPickerWrapper(
    initVal: Int = 0,
    minVal: Int = 0,
    maxVal: Int = 59,
    onNumPick: (Int) -> Unit = {}
) {
    val numFormat = NumberPicker.Formatter { i: Int ->
        DecimalFormat("00").format(i)
    }

    AndroidView(
        factory = { context ->
            NumberPicker(context).apply {
                setOnValueChangedListener { numberPicker, oldVal, newVal -> onNumPick(newVal) }
                minValue = minVal
                maxValue = maxVal
                value = initVal
                setFormatter(numFormat)
            }
        }
    )
}