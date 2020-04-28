package vantutrieu97.myapplication.models

import com.google.gson.annotations.SerializedName

data class Animal(
    @SerializedName("id")
    val breedId: String?,
    @SerializedName("name")
    val breed: String?,
    @SerializedName("life_span")
    val lifeSpan: String?,
    @SerializedName("breed_group")
    val breedGroup: String?,
    @SerializedName("bred_for")
    val breedFor: String?,
    @SerializedName("temperament")
    val temperament: String?,
    @SerializedName("url")
    val imageUrl: String?
)