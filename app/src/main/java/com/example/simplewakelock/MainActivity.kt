package com.example.simplewakelock

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PowerManager
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var powerManager: PowerManager
    private lateinit var lock: PowerManager.WakeLock

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting listeners on buttons
        findViewById<Button>(R.id.button_activate).setOnClickListener {
            enableBehaviour()
            findViewById<TextView>(R.id.textview).text = "On"
        }
        findViewById<Button>(R.id.button_disable).setOnClickListener {
            disableBehaviour()
            findViewById<TextView>(R.id.textview).text = "Off"
        }

        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        lock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK,"default")
    }

    override fun onPause() {
        super.onPause()
        disableBehaviour()
    }

    @SuppressLint("WakelockTimeout")
    private fun enableBehaviour(){
        if(!lock.isHeld) lock.acquire()
    }

    private fun disableBehaviour(){
        if(lock.isHeld) lock.release()
    }
}
