package com.joancolmenerodev.retrofitespresso

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.joancolmenerodev.retrofitespresso.adapter.AuthorsAdapter
import com.joancolmenerodev.retrofitespresso.model.AuthorResponse
import com.joancolmenerodev.retrofitespresso.networking.ApiClient
import com.joancolmenerodev.retrofitespresso.networking.PoetryService
import com.joancolmenerodev.retrofitespresso.utils.NetworkConnection

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val TAG: String = MainActivity::class.java.simpleName
    private val apiServices = ApiClient.client.create(PoetryService::class.java)!!
    lateinit var myCustomAdapter: AuthorsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        progressBar.visibility = View.VISIBLE

        if(NetworkConnection.isNetworkConnected(applicationContext)){
            getAuthorsFromApi(apiServices)
        }
        else{
            Toast.makeText(applicationContext, "No internet connection", Toast.LENGTH_SHORT).show()
        }




    }

    private fun getAuthorsFromApi(apiServices: PoetryService) {
        val call = apiServices.getAuthors()
        call.enqueue(object : Callback<AuthorResponse> {
            override fun onResponse(call: Call<AuthorResponse>, response: Response<AuthorResponse>) {
                fillAdapter(response.body()?.authors)
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<AuthorResponse>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun fillAdapter(authors: List<String>?) {
        val listOfAuthors: List<String> = authors!!
        myCustomAdapter = AuthorsAdapter(applicationContext, listOfAuthors)
        recyclerView.adapter = myCustomAdapter
        myCustomAdapter.onItemClick = { author ->
            val intent = Intent(this, PoemActivity::class.java)
            intent.putExtra("author", author)
            startActivity(intent)
        }
    }
}
