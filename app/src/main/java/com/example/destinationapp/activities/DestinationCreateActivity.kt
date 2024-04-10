package com.example.destinationapp.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.destinationapp.Retrofit.DestinationService
import com.example.destinationapp.Retrofit.ServiceBuilder
import com.example.destinationapp.databinding.ActivityDestinyCreateBinding
import com.example.destinationapp.models.Destination
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {
    private val binding: ActivityDestinyCreateBinding by lazy {
        ActivityDestinyCreateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//		setSupportActionBar(toolbar)
//		val context = this

        // Show the Up button in the action bar.
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Registers a photo picker activity launcher in single-select mode.
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                // Callback is invoked after the user selects a media item or closes the
                // photo picker.
                if (uri != null) {
                    binding.cityImg.setImageURI(uri)


                    Log.d("PhotoPicker", "Selected URI: $uri")


                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.selectImg.setOnClickListener {
            // Launch the photo picker and let the user choose only images.
//            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.btnAdd.setOnClickListener {
            val newDestination = Destination()
            newDestination.city = binding.etCity.text.toString()
            newDestination.description = binding.etDescription.text.toString()
            newDestination.country = binding.etCountry.text.toString()
//            newDestination.image = binding.cityImg.toString()

            // To be replaced by retrofit code
//            SampleData.addDestination(newDestination)

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)

            val requestCall = destinationService.addDestination(newDestination)
            requestCall.enqueue(
                object : Callback<Destination> {
                    override fun onResponse(
                        p0: Call<Destination>,
                        response: Response<Destination>
                    ) {
                        if (response.isSuccessful) {
                            val newAddedDestination = response.body()
                            Log.d("NEW_DESTINATION", "onResponse: $newAddedDestination")
                            Toast.makeText(
                                this@DestinationCreateActivity,
                                "Destination added successfully 🫡",
                                Toast.LENGTH_SHORT
                            ).show()
//                            finish()
                        }
                    }

                    override fun onFailure(p0: Call<Destination>, p1: Throwable) {
                        Toast.makeText(
                            this@DestinationCreateActivity,
                            "Destination added Failed 😵‍💫",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            finish() // Move back to DestinationListActivity
        }
    }
}
