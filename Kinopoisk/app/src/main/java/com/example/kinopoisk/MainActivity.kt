package com.example.kinopoisk

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kinopoisk.databinding.ActivityMainBinding
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var bindingClass : ActivityMainBinding
    private val adapter = FilmAdapter()
    private var filmsImage = listOf(R.drawable.banner)
    private var filmsTitle = listOf(R.string.film_title)
    private var filmsGeners = listOf(R.string.film_geners)
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportActionBar?.title = getString(R.string.popular)

        // слушатель нажатий на кнопку "Популярное"
        bindingClass.bPopular.setOnClickListener {
            supportActionBar?.title = getString(R.string.popular)
            bindingClass.rvFilms.visibility = View.VISIBLE
            checkConnection()
        }
        // слушатель нажатий на кнопку "Избранное"
        bindingClass.bFavorite.setOnClickListener {
            supportActionBar?.title = getString(R.string.favorite)
            bindingClass.rvFilms.visibility = View.GONE
            checkConnection()
        }
        ///////////
        bindingClass.bSomeFilm.setOnClickListener {
            val intent = Intent (this, FilmCardActivity::class.java)
            startActivity(intent)
            checkConnection()
        }

        // слушатель нажатий на кнопку "Повторить"
        bindingClass.bTryAgain.setOnClickListener {
            checkConnection()
        }

        checkConnection()
        init()

    }

//    fun bCheckFilm() {
//        val intent = Intent (this, FilmCardActivity::class.java)
//        startActivity(intent)
//        checkConnection()
//    }

    fun checkConnection() {
        val connectionManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (isConnected) {
            bindingClass.bPopular.visibility = View.VISIBLE
            bindingClass.bFavorite.visibility = View.VISIBLE
            bindingClass.ivNoConnection.visibility = View.GONE
            bindingClass.tvNoConnection.visibility = View.GONE
            bindingClass.bTryAgain.visibility = View.GONE
        }
        else {
            bindingClass.bPopular.visibility = View.GONE
            bindingClass.bFavorite.visibility = View.GONE
            bindingClass.ivNoConnection.visibility = View.VISIBLE
            bindingClass.tvNoConnection.visibility = View.VISIBLE
            bindingClass.bTryAgain.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        val queue = Volley.newRequestQueue(this)
        val url = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=1"

        val myReq: StringRequest = object : StringRequest(
            Method.GET,
            url,
            {
                    responce -> parser(responce)
            },
            {
                    responce -> Log.d("MyLog", " Ответ: $responce")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<kotlin.String, kotlin.String>? {
                val params: MutableMap<kotlin.String, kotlin.String> = HashMap()
                params["accept"] = "application/json"
                params["X-API-KEY"] = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
                return params
            }
        }

        queue.add(myReq)
    }

    fun parser(result: kotlin.String) {
        val mainObject = JSONObject(result)
    }

    override fun onResume() {
        super.onResume()
        bindingClass.progressBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.app_bar_search) {
            Toast.makeText(this, "searchh", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun init() {
        bindingClass.apply {
            rvFilms.layoutManager = LinearLayoutManager(this@MainActivity)
            rvFilms.adapter = adapter

            val film = Film(filmsImage[index], filmsTitle[index], filmsGeners[index])
            adapter.addFilm(film)
            index++
        }
    }
}