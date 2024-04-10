package com.example.destinationapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.destinationapp.Retrofit.DestinationService
import com.example.destinationapp.Retrofit.ServiceBuilder
import com.example.destinationapp.databinding.ActivityDestinyListBinding
import com.example.destinationapp.helpers.DestinationAdapter
import com.example.destinationapp.models.Destination
import com.github.ybq.android.spinkit.sprite.Sprite
import com.github.ybq.android.spinkit.style.Circle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationListActivity : AppCompatActivity() {
    val binding: ActivityDestinyListBinding by lazy {
        ActivityDestinyListBinding.inflate(layoutInflater)
    }
    lateinit var adapter: DestinationAdapter
    var destinationList = emptyList<Destination>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        loadDestinations()
    }

    private fun loadDestinations() {
        loader()
        binding.loader.visibility = View.VISIBLE
        // To be replaced by retrofit code
        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
//        1st
//        val requestCall = destinationService.getDestinationList()

//        2nd fetch only data where country is India
//        val requestCall = destinationService.getDestinationList("India")
//        val requestCall = destinationService.getDestinationList("India", "1")


        //use multiple value
        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"
        val requestCall = destinationService.getDestinationList(filter,"EN")

        requestCall.enqueue(object : Callback<List<Destination>> {
            //If your receive a Http Response,then this method is executed
            //Your STATUS code will decide if your Http Response is a success or Error
            override fun onResponse(
                p0: Call<List<Destination>>, response: Response<List<Destination>>
            ) {
                if (response.isSuccessful) {
                    //your status code is in the range of 200-299
                    destinationList = response.body()!!
                    binding.destinyRecyclerView.layoutManager = LinearLayoutManager(
                        this@DestinationListActivity, LinearLayoutManager.VERTICAL, false
                    )
                    adapter = DestinationAdapter(destinationList)
                    binding.destinyRecyclerView.adapter = adapter
                    binding.loader.visibility = View.GONE

                    Log.d("RESPONSE", "onResponse: ${response.body()}")
                    Log.d("CODE", "onResponse: ${response.code()}")
//                    binding.loader.visibility = View.GONE
                } else if (response.code() == 401) {
                    Toast.makeText(
                        this@DestinationListActivity,
                        "${response.code()} Your Session has expired Please Login Again.",
                        Toast.LENGTH_LONG
                    ).show()
                    binding.loader.visibility = View.GONE
                } else {// Application level failure
                    binding.loader.visibility = View.GONE
                    //your status code is in the range of 300's,400's and 500's
                    Toast.makeText(
                        this@DestinationListActivity, "Failed to retrieve items", Toast.LENGTH_SHORT
                    ).show()
                }
            }

            //Invoked in case of Network Error or Establishing connection with server
            //Or error Creating Http Request or Error Processing Http Response
            override fun onFailure(p0: Call<List<Destination>>, p1: Throwable) {
                Toast.makeText(this@DestinationListActivity, "Failed", Toast.LENGTH_SHORT).show()
                Log.d("FAILURE", "onFailure: ${p1.localizedMessage}")
                binding.loader.visibility = View.GONE
            }
        })

    }

    private fun loader() {
        val progressBar = binding.loader as ProgressBar
        val circle: Sprite = Circle()
        progressBar.indeterminateDrawable = circle
    }
}
