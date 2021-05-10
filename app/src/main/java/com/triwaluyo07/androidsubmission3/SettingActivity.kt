package com.triwaluyo07.androidsubmission3

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.triwaluyo07.androidsubmission3.databinding.ActivitySettingBinding
import com.triwaluyo07.androidsubmission3.receiver.AlarmReceiver

class SettingActivity : AppCompatActivity() {
    companion object {
        const val ALARM_PREF = "ALARM_PREF"
        private const val ALARM_DAILY = "alarm_daily"
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPreferences: SharedPreferences
    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.setting_alarm)

        alarmReceiver = AlarmReceiver()
        mSharedPreferences = getSharedPreferences(ALARM_PREF, Context.MODE_PRIVATE)

        setSwitch()
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                alarmReceiver.setDailyReminder(
                    this,
                    AlarmReceiver.TYPE_DAILY,
                    "Search your favorite user now"
                )
            } else {
                alarmReceiver.cancelAlarm(this)
            }
            saveReminded(isChecked)
        }
    }

    private fun setSwitch() {
        binding.switchAlarm.isChecked = mSharedPreferences.getBoolean(ALARM_DAILY, false)
    }

    private fun saveReminded(value: Boolean) {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(ALARM_DAILY, value)
        editor.apply()
    }
}