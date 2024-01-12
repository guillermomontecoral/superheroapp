package com.mntcrl.superheroapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroList: List<SuperHeroItemResponse>
)

data class SuperHeroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val superheroName: String,
    @SerializedName("image") val superheroImage: SuperHeroImageResponse
)

data class SuperHeroImageResponse(@SerializedName("url") val url: String)

data class SuperHeroDetailResponse(
    @SerializedName("name") val superheroName: String,
    @SerializedName("image") val superheroImage: SuperHeroImageResponse,
    @SerializedName("powerstats") val powerStatsResponse: SuperHeroPowerStatsResponse,
    @SerializedName("biography") val biographyResponse: SuperHeroBiographyResponse
)

data class SuperHeroPowerStatsResponse(
    @SerializedName("intelligence") val superheroIntelligence: String,
    @SerializedName("strength") val superheroStrength: String,
    @SerializedName("speed") val superheroSpeed: String,
    @SerializedName("durability") val superheroDurability: String,
    @SerializedName("power") val superheroPower: String,
    @SerializedName("combat") val superheroCombat: String
)

data class SuperHeroBiographyResponse(
    @SerializedName("full-name") val superheroFullName: String,
    @SerializedName("publisher") val superheroPublisher: String
)

data class SuperHeroAppearanceResponse(
    @SerializedName("gender") val superheroGender: String,
    @SerializedName("race") val superheroRace: String
)

data class SuperHeroWorkResponse(
    @SerializedName("occupation") val superheroOccupation: String,
    @SerializedName("base") val superheroBase: String
)
