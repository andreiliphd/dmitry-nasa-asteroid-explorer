/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.udacity.asteroidradar.api

// import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.udacity.asteroidradar.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// import kotlinx.coroutines.Deferred

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()
//
/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
var gson: Gson? = GsonBuilder()
    .setLenient()
    .create()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(gson))
    .baseUrl(BASE_URL)
    .build()

/**
 * A public interface that exposes the [getProperties] method
 */
interface AsteroidApiService {
    /**
     * Returns a Coroutine [List] of [MarsProperty] which can be fetched with await() if in a Coroutine scope.
     * The @GET annotation indicates that the "realestate" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("neo/rest/v1/feed?")
    suspend fun getProperties(@Query("start_date") start_date: String ,
                              @Query("end_date") end_date: String ,
                              @Query("api_key") api_key: String ): Asteroids
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object AsteroidApi {
    val retrofitService = retrofit.create(AsteroidApiService::class.java)
}