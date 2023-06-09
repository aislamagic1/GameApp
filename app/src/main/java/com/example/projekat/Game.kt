package com.example.projekat

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var title: String,
    var platform: String,
    var releaseDate: String?,
    @SerializedName("rating")
    var rating: Double,
    var coverImage: String?,
    var esrbRating: String?,
    var developer: String,
    var publisher: String,
    var genre: String,
    @SerializedName("summary")
    var description: String,
    var userImpressions: List<UserImpression>,

    @SerializedName("release_dates")
    var releaseDateObject: List<ReleaseDate>? = null,
    @SerializedName("platforms")
    var platformObject: List<Platforms>? = null,
    @SerializedName("age_ratings")
    var ageRatingObject: List<AgeRating?>? = null,
    @SerializedName("genres")
    var genresObject: List<Genres>? = null,
    @SerializedName("involved_companies")
    var developerPublusherObject: List<DeveloperPublisher>? = null,
    @SerializedName("cover")
    var coverImageObject: CoverImageObject? = null,
    @SerializedName("igdb_id")
    var idFromAccount: Int? = null,
    @SerializedName("success")
    var isDeleted: String? = null,
    @SerializedName("game")
    var gamePost: GamePost = GamePost(0, "")
        )

data class GamePost(
    @SerializedName("igdb_id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "H"
)

data class CoverImageObject(
    @SerializedName("url")
    var url: String
)

data class DeveloperPublisher(
    @SerializedName("company")
    var companyObject: Company
)

data class Company(
    @SerializedName("name")
    var name: String
)

data class ReleaseDate (
    @SerializedName("human")
    var releaseDate: String
)

data class Platforms (
    @SerializedName("name")
    var platform: String? = null
)

data class AgeRating(
    @SerializedName("rating")
    var ratingValue: Int,
    @SerializedName("category")
    var ratingCategory: Int
)

enum class EsrbRating(val numbericValue: Int){
    Three(3), Seven(7),	Twelve(12),	Sixteen(16),	Eighteen(18), RP(0), EC(3), E(6), E10(100),
    T(13), M(17), AO(18),	CERO_A(100),	CERO_B(12),
    CERO_C(15),	CERO_D(17),	CERO_Z(18),	USK_0(100),	USK_6(6),	USK_12(12),	USK_16(16),	USK_18(18),	GRAC_ALL(100),	GRAC_Twelve(12),
    GRAC_Fifteen(15), GRAC_Eighteen(18),	GRAC_TESTING(0),	CLASS_IND_L(100),	CLASS_IND_Ten(19),
    CLASS_IND_Twelve(12), CLASS_IND_Fourteen(14),	CLASS_IND_Sixteen(16),	CLASS_IND_Eighteen(18),	ACB_G(100), ACB_PG(15),
    ACB_M(15),	ACB_MA15(15), ACB_R18(18),	ACB_RC(0);

    companion object {
        fun fromNumber(number: Int): EsrbRating? {
            return values().find { it.ordinal == number }
        }
    }
}

data class Genres(
    @SerializedName("name")
    var genre: String
)