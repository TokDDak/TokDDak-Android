package com.sopt.tokddak.api

import com.google.gson.annotations.SerializedName
import com.sopt.tokddak.feature.planning.ActivityData
import retrofit2.Call
import retrofit2.http.*

interface PlanningService {

    @GET("median/{CityId}/hotel")
    fun getLodgement(
        @Header("Content-Type") content_type: String,
        @Path("CityId") CityId : Int
        ): Call<GetLodgeData>

    @GET("/median/{CityId}/food")
    fun getFood(
        @Header("Content-Type") content_type: String,
        @Path("CityId") CityId : Int
        ): Call<GetFoodData>

    @GET("citys/{CityId}/Activity")
    fun getActivity(
        @Header("Content-Type") content_type: String,
        @Path("CityId") CityId : Int
    ): Call<GetActivityData>

    @GET("citys/{CityId}/Shoppingimg")
    fun getShopping(
        @Header("Content-Type") content_type: String,
        @Path("CityId") CityId : Int
    ): Call<GetShoppingData>

    @GET("citys/{CityId}/Transportimg")
    fun getTransportation(
        @Header("Content-Type") content_type: String,
        @Path("CityId") CityId : Int
    ): Call<GetTransData>

    @POST("tripHotel/{TripId}")
    fun requestLodge(
        @Header("Content-Type") content_type: String,
        @Path("TripId") TripId: Int,
        @Body body: PostRequestLodgeData
    ): Call<PostRequestLodgeState>

    @POST("tripActivity/{TripId}")
    fun requestActivity(
        @Header("Content-Type") content_type: String,
        @Path("TripId") TripId: Int,
        @Body body: PostRequestActivityData
    ): Call<PostREquestActivityState>

    @POST("TripSnack/{TripId}")
    fun requestSnack(
        @Header("Content-Type") content_type: String,
        @Path("TripId") TripId: Int,
        @Body body: PostRequestSnackData
    ): Call<PostRequestSnackState>
}

data class GetLodgeData(
    val status: Int,
    val message: String,
    val success: Boolean,
    @SerializedName("data")
    val lodgeData: LodgeResult
)

data class LodgeResult(
    val result: ArrayList<LodgeData>
)

data class LodgeData(
    @SerializedName("category")
    val type: String,
    @SerializedName("cost")
    val avgPrice: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("info")
    val info: ArrayList<LodgeSample>
)

data class LodgeSample(
    val name: String,
    val cost: Int
)

data class GetFoodData(
    val status: Int,
    val message: String,
    @SerializedName("data")
    val foodResult: FoodResult,
    val success: Boolean
)

data class FoodResult(
    val result: ArrayList<FoodData>
)

data class FoodData(
    val category: String,
    val cost: Int,
    val url: String
)

data class GetActivityData(
    val status: Int,
    val message: String,
    val success: Boolean,
    val data: List<ActivityData>
)

/*data class ActivityData(
    val id: Int,
    val name: String,
    val cost: Int,
    val content: String,
    val url_mrt: String,
    val url_kl: String,
    val img: String,
    val CityId: Int
)*/

data class GetShoppingData(
    val status: Int,
    val message: String,
    val success: Boolean,
    val data: List<ShoppingData>
)

data class ShoppingData(
    val img: String
)

data class GetTransData(
    val status: Int,
    val message: String,
    val success: Boolean,
    val data: List<TransData>
)

data class TransData(
    val img: String
)

data class PostRequestLodgeData(
    val array: List<RequestLodgeData>
)

data class RequestLodgeData(
    val grade: String,
    val cost: Int,
    val count: Int
)

data class PostRequestLodgeState(
    val status: Int,
    val message: String,
    val success: Boolean
)

data class RequestActivityData(
    val name: String,
    val cost: Int
)

data class PostREquestActivityState(
    val status: Int,
    val message: String,
    val success: Boolean
)

data class PostRequestActivityData(
    val array: List<RequestActivityData>
)

data class RequestSnackData(
    val grade: String,
    val cost: Int,
    val count: Int
)

data class PostRequestSnackState(
    val status: Int,
    val message: String,
    val success: Boolean
)

data class PostRequestSnackData(
    val array: List<RequestSnackData>
)