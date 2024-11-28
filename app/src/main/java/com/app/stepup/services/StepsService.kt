package com.app.stepup.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.app.stepup.R
import com.app.stepup.constants.Constants.STEPS_COUNTER_CHANNEL_ID
import com.app.stepup.constants.Constants.STEPS_COUNTER_CHANNEL_NAME
import com.app.stepup.constants.Constants.STEPS_COUNTER_NOTIFICATION_ID
import com.app.stepup.model.StepRepository
import com.app.stepup.model.room.StepData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@AndroidEntryPoint
class StepsService : Service(), SensorEventListener {
    @Inject
    lateinit var stepRepository: StepRepository

    private lateinit var sensorManager: SensorManager

    private val serviceJob = Job()
    private val coroutineScope = CoroutineScope(serviceJob + Dispatchers.IO)

    private var stepSensor: Sensor? = null

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        initialSensor()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.values != null) {
            coroutineScope.launch(Dispatchers.IO) {
                stepRepository.insert(
                    StepData(
                        steps = event.values[0].toLong(),
                        createdAt = Instant.now().toString()
                    )
                )
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
        serviceJob.cancel()
    }

    private fun initialSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        stepSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun startForegroundService() {
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val channel = NotificationChannel(
            STEPS_COUNTER_CHANNEL_ID,
            STEPS_COUNTER_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)

        val notification: Notification = NotificationCompat.Builder(this, STEPS_COUNTER_CHANNEL_ID)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.service_count_steps))
            .setSmallIcon(R.mipmap.ic_launcher)
            .build()

        startForeground(STEPS_COUNTER_NOTIFICATION_ID, notification)
    }
}