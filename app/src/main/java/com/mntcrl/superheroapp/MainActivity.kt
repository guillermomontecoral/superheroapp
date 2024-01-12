package com.mntcrl.superheroapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mntcrl.superheroapp.DetailSuperHeroActivity.Companion.ID_SUPERHERO
import com.mntcrl.superheroapp.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retofit:Retrofit
    private lateinit var adapter: SuperHeroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        retofit = getRetroFit()

        initUI()
    }

    private fun initUI() {
        binding.swSearcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            //Esta funcion se llama automaticamente cuando se pulsa en el boton buscar
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            //Esta funcion se llama cada vez que se vaya escribiendo en el buscador
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        adapter = SuperHeroAdapter(){id -> navigateToDetail(id)}
        binding.rvSuperheroList.setHasFixedSize(true)
        binding.rvSuperheroList.layoutManager = LinearLayoutManager(this)
        binding.rvSuperheroList.adapter = adapter
    }

    private fun searchByName(query: String) {
        binding.progressBar.isVisible = true

        //Lo que estara dentro de las llaves se va a realizar en un hilo secundario
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<SuperHeroDataResponse> = retofit.create(ApiService::class.java).getSuperheros(query)

            if(response.isSuccessful){
                val results: SuperHeroDataResponse? = response.body()
                if(results != null) {
                    Log.i("GUILLERMO", results.toString())
                    runOnUiThread{
                        adapter.updateList(results.superheroList)
                        binding.progressBar.isVisible = false
                    }
                }
            }else{
                Log.i("GUILLERMO", "No funciona")
            }
        }
    }

    private fun getRetroFit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun navigateToDetail(id : String){
        val intent = Intent(this, DetailSuperHeroActivity::class.java)
        intent.putExtra(ID_SUPERHERO, id)
        startActivity(intent)
    }
}