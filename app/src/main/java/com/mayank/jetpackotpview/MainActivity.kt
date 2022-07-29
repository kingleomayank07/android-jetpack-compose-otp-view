package com.mayank.jetpackotpview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.mayank.jetpackotpview.databinding.ActivityMainBinding
import com.mayank.libotp.OtpFields

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var getOtp = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val composeView = findViewById<ComposeView>(R.id.compose_main)
        composeView.setContent {
            MaterialTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val otpCount = 6
                    val otpValueHolder = remember { mutableStateListOf<MutableState<String>>() }
                    OtpFields(
                        count = otpCount,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        charModifier = Modifier.background(Transparent),
                        otpValueHolder = otpValueHolder
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Button(colors = ButtonDefaults.buttonColors(),
                        onClick = {
                            getOtpValue(otpValueHolder)
                        }) {
                        Text(text = "GET OTP", color = Black)
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    if (getOtp.value.length == otpCount) {
                        Text(text = "OTP is : ${getOtp.value}", color = Black)
                    }
                }
            }
        }
    }

    private fun getOtpValue(otpValueHolder: SnapshotStateList<MutableState<String>>) {
        getOtp.value = ""
        otpValueHolder.forEach {
            getOtp.value += it.value
        }
        Log.d("TAG", "onCreate: ${getOtp.value}")
    }
}
