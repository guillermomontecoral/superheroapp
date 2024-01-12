package com.mntcrl.superheroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.core.view.isVisible
import com.mntcrl.superheroapp.databinding.ActivityDetailSuperHeroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperHeroActivity : AppCompatActivity() {

    companion object {
        const val ID_SUPERHERO = "ID_SUPERHERO"
    }

    private lateinit var binding: ActivityDetailSuperHeroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailSuperHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID_SUPERHERO).orEmpty()
        getSuperHeroInformation(id)
    }

    private fun getSuperHeroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<SuperHeroDetailResponse> =
                getRetroFit().create(ApiService::class.java).getSuperheroDetail(id)

            if (response.body() != null) {
                runOnUiThread {
                    createUI(response.body()!!)
                }

            }
        }
    }

    private fun createUI(body: SuperHeroDetailResponse) {
        Picasso.get().load(body.superheroImage.url).into(binding.ivSuperheroImage)
        binding.tvSuperheroName.text = body.superheroName
        binding.tvSuperheroFullName.text = body.biographyResponse.superheroFullName
        binding.tvSuperheroPublisher.text = body.biographyResponse.superheroPublisher

        prepareStats(body.powerStatsResponse)
    }

    private fun prepareStats(powerStatsResponse: SuperHeroPowerStatsResponse) {
        updateHeight(binding.viewIntelligence, powerStatsResponse.superheroIntelligence)
       // binding.viewIntelligence.text = powerStatsResponse.superheroIntelligence
        updateHeight(binding.viewStrength, powerStatsResponse.superheroStrength)
       // binding.viewStrength.text = powerStatsResponse.superheroStrength
        updateHeight(binding.viewSpeed, powerStatsResponse.superheroSpeed)
      //  binding.viewSpeed.text = powerStatsResponse.superheroSpeed
        updateHeight(binding.viewDurability, powerStatsResponse.superheroDurability) ///  binding.viewDurability.text = powerStatsResponse.superheroDurability
        updateHeight(binding.viewPower, powerStatsResponse.superheroPower)
//        binding.viewPower.text = powerStatsResponse.superheroPower
        updateHeight(binding.viewCombat, powerStatsResponse.superheroCombat)
//        binding.viewCombat.text = powerStatsResponse.superheroCombat
    }

    private fun updateHeight(view: View, powerStat: String) {
        val params = view.layoutParams
        params.height = pixelToDp(powerStat.toFloat())
        view.layoutParams = params
    }

    private fun pixelToDp(pixel: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            pixel,
            resources.displayMetrics
        ).roundToInt()
    }

    private fun getRetroFit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}