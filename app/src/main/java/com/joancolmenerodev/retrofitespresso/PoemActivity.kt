package com.joancolmenerodev.retrofitespresso

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.joancolmenerodev.retrofitespresso.model.Poem
import com.joancolmenerodev.retrofitespresso.model.Title
import com.joancolmenerodev.retrofitespresso.networking.ApiClient
import com.joancolmenerodev.retrofitespresso.networking.PoetryService
import kotlinx.android.synthetic.main.activity_poem.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.content.Intent
import android.net.Uri


class PoemActivity : AppCompatActivity() {

    val TAG: String = PoemActivity::class.java.simpleName
    private val apiServices = ApiClient.client.create(PoetryService::class.java)!!
    var authorName = String()
    val stringPoem = StringBuilder()
    var randomTitle = String()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poem)
        this.authorName = intent.getStringExtra("author")
        tv_author_poem.text = authorName
        progressBar.visibility = View.VISIBLE
        setRandomPoemTitle(authorName)
        btn_share_poem.setOnClickListener{
            sharePoem()
        }

    }

    private fun sharePoem() {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Here you have your random poem")
        intent.putExtra(Intent.EXTRA_TEXT, "Author: " + authorName + "\n\n" + "Title: " + randomTitle + "\n\n" + stringPoem.toString())
        startActivity(intent)
    }

    private fun setRandomPoemTitle(authorName: String) {
        val call = apiServices.getTitle(authorName)
        call.enqueue(object : Callback<List<Title>> {
            override fun onResponse(call: Call<List<Title>>?, response: Response<List<Title>>?) {
                randomTitle = getRandomTitle(response?.body()!!)
                tv_poem_title.text = randomTitle
                setPoemBody(authorName,randomTitle)
                progressBar.visibility = View.VISIBLE

            }

            override fun onFailure(call: Call<List<Title>>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun setPoemBody(authorName: String, randomTitle: String) {
        val call = apiServices.getPoem(authorName,randomTitle)
        call.enqueue(object : Callback<List<Poem>> {
            override fun onResponse(call: Call<List<Poem>>?, response: Response<List<Poem>>?) {
                val poemLines = response?.body()!![0].lines
                for (text in poemLines!!) {
                    stringPoem.append(text + "\n")
                }
                Log.d(TAG,stringPoem.toString())
                tv_poem_text.text = stringPoem
                progressBar.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Poem>>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                Log.e(TAG, t.toString())
            }
        })
    }

    private fun getRandomTitle(titles: List<Title>): String{
        return titles[Random().nextInt(titles.size)].title
    }



}
