# android-jetpack-compose-otp-view

A custom OTP view written in compose usually used in cases of authentication.

#HOW TO USE

val otpFieldCount = 6

val otpValueHolder = remember { mutableStateListOf<MutableState<String>>() }



OtpFields(
  count = otpFieldCount,
  modifier = Modifier
  .fillMaxWidth()
  .padding(start = 10.dp, end = 10.dp),
  charModifier = Modifier.background(Transparent),
  otpValueHolder = otpValueHolder
)




//just pass otpValueHolder
  
private fun getOtpValue(otpValueHolder: SnapshotStateList<MutableState<String>>) {
  
        getOtp.value = ""
        otpValueHolder.forEach {
            getOtp.value += it.value
        }
        Log.d("TAG", "onCreate: ${getOtp.value}") 
  
}
