package com.pankajranag.rana_gaming.kotlin

import android.content.Context
import android.graphics.PorterDuff.Mode
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.pankajranag.rana_gaming.R
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPIC = "/topics/myTopic2"
class Notification : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.navigationIcon!!.setColorFilter(resources.getColor(R.color.white), Mode.SRC_ATOP)

        // inputLayoutValidate();
        // edtTextValidate();


        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            FirebaseService.token = it.token
            etToken.setText(it.token)
        }
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        btnSend.setOnClickListener {
            val title = etTitle.text.toString()
            val message = etMessage.text.toString()
            val recipientToken = etToken.text.toString()
            if (title.isNotEmpty() && message.isNotEmpty() && recipientToken.isNotEmpty()) {
                PushNotification(
                        NotificationData(title, message),
                        recipientToken
                ).also {
                    sendNotification(it)
                }
            }
        }
    }

    private fun sendNotification(notification: PushNotification) =
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.api.postNotification(notification)
                    if (response.isSuccessful) {
                        Log.d(TAG, "Response: ${Gson().toJson(response)}")
                    } else {
                        Log.e(TAG, response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return true;
    }
}
