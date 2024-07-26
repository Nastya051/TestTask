package com.example.testtask.presentation.ui.custom_views

import android.content.Context
import android.net.wifi.WifiManager
import android.net.wifi.WifiManager.WIFI_STATE_ENABLED
import android.os.BatteryManager
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.testtask.BuildConfig
import com.example.testtask.R


@Composable
fun HeaderRow(
    modifier: Modifier = Modifier.padding(top = 30.dp),
    isBackButton: Boolean = false,
    onClickBackButton: () -> Unit = {}
) {
    var showBatteryLevel by remember { mutableStateOf(false) }
    var showWiFiStatus by remember { mutableStateOf(false) }
    var showVersion by remember { mutableStateOf(false) }
    Box(modifier = modifier.fillMaxWidth()) {
        if (isBackButton)
            IconButton(onClick = { onClickBackButton() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(60.dp),
                            color = Color.Black
                        )
                        .align(Alignment.CenterStart)
                )
            }
        Row(modifier = Modifier
            .align(Alignment.Center)
            .padding(bottom = 16.dp)) {
            IconButton(onClick = {
                showBatteryLevel = !showBatteryLevel
                showWiFiStatus = false
                showVersion = false
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_battery_24),
                    contentDescription = "Battery",
                    tint = Color.Green
                )
            }
            IconButton(onClick = {
                showWiFiStatus = !showWiFiStatus
                showVersion = false
                showBatteryLevel = false
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_wifi_24),
                    contentDescription = "WiFi",
                    tint = Color.Cyan
                )
            }
            IconButton(onClick = {
                showVersion = !showVersion
                showWiFiStatus = false
                showBatteryLevel = false
            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_info_24),
                    contentDescription = "Info",
                    tint = Color.Gray
                )
            }
        }

        AnimatedVisibility(
            visible = showBatteryLevel,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            val batteryLvl = getBatteryLevel()
            Text(text = stringResource(id = R.string.battery, batteryLvl))
        }

        AnimatedVisibility(
            visible = showWiFiStatus,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            val wifi = getWiFiStatus()
            Text(text = stringResource(id = R.string.wifi, wifi))
        }

        AnimatedVisibility(
            visible = showVersion,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            val version = getAppVersion()
            Text(text = stringResource(id = R.string.version, version))
        }

        HorizontalDivider(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun getBatteryLevel(): Int {
    val context = LocalContext.current
    val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
    val batLevel: Int = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    return batLevel
}

@Composable
fun getWiFiStatus(): String {
    val context = LocalContext.current
    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    var signalLvl = 0
    var signalLvlDescription = ""
    if (wifiManager.wifiState != WIFI_STATE_ENABLED) {
        signalLvlDescription = stringResource(id = R.string.noConnection)
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            signalLvl = wifiManager.calculateSignalLevel(wifiManager.connectionInfo.rssi)
        } else {
            val minRssi = -100
            val maxRssi = -55
            val levels = 5
            val rssi = wifiManager.connectionInfo.rssi
            if (rssi <= minRssi) {
                signalLvl = 0
            } else if (rssi >= maxRssi) {
                signalLvl = levels - 1
            } else {
                val inputRange = (maxRssi - minRssi)
                val outputRange = (levels - 1)
                signalLvl = ((rssi - minRssi) * outputRange / inputRange)
            }
        }

        when (signalLvl) {
            0 -> signalLvlDescription = stringResource(id = R.string.veryWeak)
            1 -> signalLvlDescription = stringResource(id = R.string.weak)
            2 -> signalLvlDescription = stringResource(id = R.string.middle)
            3 -> signalLvlDescription = stringResource(id = R.string.good)
            4 -> signalLvlDescription = stringResource(id = R.string.excellent)
        }
    }
    return signalLvlDescription
}

@Composable
fun getAppVersion(): String {
    return BuildConfig.VERSION_NAME
}