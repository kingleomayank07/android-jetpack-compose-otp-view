package com.mayank.libotp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpFields(
    count: Int = 4,
    modifier: Modifier,
    charModifier: Modifier,
    otpValueHolder: SnapshotStateList<MutableState<String>>
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        repeat(count) {
            otpValueHolder.add(mutableStateOf(""))
            OtpChar(
                isFirstField = it == 0,
                otpValueHolder = otpValueHolder[it],
                isLastField = it == count - 1,
                modifier = charModifier
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OtpChar(
    isFirstField: Boolean = false,
    isLastField: Boolean = false,
    otpValueHolder: MutableState<String>,
    modifier: Modifier
) {
    val focusManager = LocalFocusManager.current
    val maxChar = 1
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = otpValueHolder.value,
            onValueChange = {
                if (it.length <= maxChar) {
                    otpValueHolder.value = it
                }
                if (it.isNotEmpty()) {
                    when {
                        isLastField -> {
                            focusManager.clearFocus()
                        }
                        else -> {
                            focusManager.moveFocus(FocusDirection.Next)
                        }
                    }
                }
                if (it.isEmpty()) {
                    when {
                        isFirstField -> {
                            focusManager.clearFocus()
                        }
                        else -> {
                            focusManager.moveFocus(FocusDirection.Previous)
                        }
                    }
                }
            },
            modifier = Modifier
                .width(50.dp)
                .onKeyEvent {
                    if (it.key == Key.Backspace) {
                        otpValueHolder.value = ""
                        true
                    } else {
                        false
                    }
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = White,
                backgroundColor = Transparent,
                unfocusedIndicatorColor = Gray,
                focusedIndicatorColor = White
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = if (isLastField) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = if (isLastField) KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ) else KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }
            )
        )
        Spacer(modifier = Modifier.width(20.dp))
    }
}
