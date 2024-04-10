package com.example.destinationapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.destinationapp.Retrofit.DestinationService
import com.example.destinationapp.Retrofit.ServiceBuilder
import com.example.destinationapp.databinding.ActivityDestinyDetailBinding
import com.example.destinationapp.helpers.SampleData
import com.example.destinationapp.models.Destination
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {
    val binding: ActivityDestinyDetailBinding by lazy {
        ActivityDestinyDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//		setSupportActionBar(detail_toolbar)
        // Show the Up button in the action bar.
//		supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun loadDetails(id: Int) {

        // To be replaced by retrofit code
//		val destination = SampleData.getDestinationById(id)
        val serviceDestination = ServiceBuilder.buildService(DestinationService::class.java)

        val requestCall = serviceDestination.getDestination(id)

        requestCall.enqueue(object : retrofit2.Callback<Destination> {
            override fun onResponse(p0: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    val destination = response.body()

                    destination?.let {
                        binding.etCity.setText(destination.city)
                        binding.etDescription.setText(destination.description)
                        binding.etCountry.setText(destination.country)/*
                                                Log.d("IMG", "onResponse:${destination.image}")
                                                binding.cityImg.load(destination.image) {
                                                    placeholder(R.drawable.toolbar_background)
                                                }
                        */
                        binding.title.text = destination.city
                    }
                }
            }

            override fun onFailure(p0: Call<Destination>, p1: Throwable) {

            }

        })

//			collapsing_toolbar.title = destination.city

    }

    private fun initUpdateButton(id: Int) {

        binding.btnUpdate.setOnClickListener {

            val city = binding.etCity.text.toString()
            val description = binding.etDescription.text.toString()
            val country = binding.etCountry.text.toString()
            // To be replaced by retrofit code
//            val destination = Destination()
//            destination.id = id
//            destination.city = city
//            destination.description = description
//            destination.country = country
//            SampleData.updateDestination(destination);


            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.updateDestination(id, city, description, country)

            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(p0: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Destination update Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("RESPONSE", "onResponse: ${response.body()}")
                        finish()
                    }
                }

                override fun onFailure(p0: Call<Destination>, p1: Throwable) {

                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Destination Update Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
//            finish() // Move back to DestinationListActivity
        }
    }

    private fun initDeleteButton(id: Int) {

        binding.btnDelete.setOnClickListener {

            // To be replaced by retrofit code
//            SampleData.deleteDestination(id)

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.deleteDestination(id)
            requestCall.enqueue(object : Callback<Unit> {
                override fun onResponse(p0: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Destination Successfully Deleted!",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()

                    }
                }

                override fun onFailure(p0: Call<Unit>, p1: Throwable) {

                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Destination Delete Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })


            finish() // Move back to DestinationListActivity
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ARG_ITEM_ID = "item_id"
    }
}
