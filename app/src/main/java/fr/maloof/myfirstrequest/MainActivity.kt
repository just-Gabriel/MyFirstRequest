package fr.maloof.myfirstrequest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private val TAG: String = "CHECK_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getAllComments()
    }

    private fun getAllComments() {

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MyApi::class.java)

        api.getComments().enqueue(object : Callback<List<Comments>> {
            override fun onResponse(
                call: Call<List<Comments>>,
                response: retrofit2.Response<List<Comments>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        for (comment in it) {
                            Log.i(TAG, "onResponse: ${comment.email}")
                        }
                    }
                } else {
                    Log.i(TAG, "onResponse: Response not successful")
                }
            }

            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Log.i(TAG, "onFailure: ${t.message}")
            }

        })

    }
}