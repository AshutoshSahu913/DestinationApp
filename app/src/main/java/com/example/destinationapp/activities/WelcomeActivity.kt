package com.example.destinationapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.destinationapp.Retrofit.DestinationService
import com.example.destinationapp.Retrofit.MessageService
import com.example.destinationapp.Retrofit.ServiceBuilder
import com.example.destinationapp.databinding.ActivityWelcomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class WelcomeActivity : AppCompatActivity() {
    val binding: ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // To be replaced by retrofit code
//        binding.message.text = "Black Friday! Get 50% cash back on saving your first spot."
        val serviceBuilder = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = serviceBuilder.getMessage()

        requestCall.enqueue(object : Callback<String> {
            override fun onResponse(p0: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val msg = response.body()
                    msg?.let {
                        binding.message1.text = msg

                    }
                } else {
                    Toast.makeText(
                        this@WelcomeActivity,
                        "Failed to retrieve items",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }

            override fun onFailure(p0: Call<String>, p1: Throwable) {
                Toast.makeText(
                    this@WelcomeActivity,
                    "Failed to retrieve items",
                    Toast.LENGTH_SHORT
                )
                    .show()

            }

        })
    }

    fun getStarted(view: View) {
        val intent = Intent(this, DestinationListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
