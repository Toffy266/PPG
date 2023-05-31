package com.example.predictpopularityofgame.repository

import com.example.predictpopularityofgame.model.FeatureAffectModel
import com.example.predictpopularityofgame.model.PredictInputModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitAPI {

    // สร้าง Method สำหรับเรียก API
    @POST("predict_ratings")
    fun getFeatureRating(@Body predictInput: PredictInputModel): Call<FeatureAffectModel>
    @POST("predict_noratings")
    fun getFeatureNonRating(@Body predictInput: PredictInputModel): Call<FeatureAffectModel>

    // สร้าง Object ของ Retrofit
    companion object {
        // กำหนด BaseURL ของ API
        private const val BaseURL = "https://fceb-2405-9800-b870-b20e-a985-ae0e-6c56-73be.ngrok-free.app/"
        private var retrofitAPI : RetrofitAPI? = null

        fun getInstance(): RetrofitAPI {
            if(retrofitAPI == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitAPI  = retrofit.create(RetrofitAPI ::class.java)
            }
            return retrofitAPI!!
        }
    }


}