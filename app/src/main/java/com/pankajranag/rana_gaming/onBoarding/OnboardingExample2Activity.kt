package com.pankajranag.rana_gaming.onBoarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.pankajranag.rana_gaming.Activity.MixLogin
import com.pankajranag.rana_gaming.R
import kotlinx.android.synthetic.main.activity_onboarding_example2.*

class OnboardingExample4Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_example2)
        val finishbutton = findViewById<Button>(R.id.finishButton)
        finishbutton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View): Unit {
                // Handler code here.
                val intent = Intent(this@OnboardingExample4Activity, MixLogin::class.java);
                startActivity(intent);
                finish();
            }
        })
    }
}