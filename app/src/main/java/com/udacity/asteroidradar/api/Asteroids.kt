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

import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroids(
    val element_count: Int,
    val links: Links,
    val near_earth_objects: List<Details>
): Parcelable

@Parcelize
data class Links(
    val next: String,
    val prev: String,
    val self: String
): Parcelable

@Parcelize
data class Details(
    val absolute_magnitude_h: Double,
    val close_approach_data: List<CloseApproachData>,
    val estimated_diameter: EstimatedDiameterX,
    val id: String,
    val is_potentially_hazardous_asteroid: Boolean,
    val is_sentry_object: Boolean,
    val links: LinksXX,
    val name: String,
    val nasa_jpl_url: String,
    val neo_reference_id: String
): Parcelable

@Parcelize
data class CloseApproachData(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistance,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocity
): Parcelable

@Parcelize
data class EstimatedDiameter(
    val feet: Feet,
    val kilometers: Kilometers,
    val meters: Meters,
    val miles: Miles
): Parcelable

@Parcelize
data class LinksX(
    val self: String
): Parcelable

@Parcelize
data class MissDistance(
    val astronomical: String,
    val kilometers: String,
    val lunar: String,
    val miles: String
): Parcelable

@Parcelize
data class RelativeVelocity(
    val kilometers_per_hour: String,
    val kilometers_per_second: String,
    val miles_per_hour: String
): Parcelable

@Parcelize
data class Feet(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class Kilometers(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class Meters(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class Miles(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class CloseApproachDataX(
    val close_approach_date: String,
    val close_approach_date_full: String,
    val epoch_date_close_approach: Long,
    val miss_distance: MissDistanceX,
    val orbiting_body: String,
    val relative_velocity: RelativeVelocityX
): Parcelable

@Parcelize
data class EstimatedDiameterX(
    val feet: FeetX,
    val kilometers: KilometersX,
    val meters: MetersX,
    val miles: MilesX
): Parcelable

@Parcelize
data class LinksXX(
    val self: String
): Parcelable

@Parcelize
data class MissDistanceX(
    val astronomical: String,
    val kilometers: String,
    val lunar: String,
    val miles: String
): Parcelable

@Parcelize
data class RelativeVelocityX(
    val kilometers_per_hour: String,
    val kilometers_per_second: String,
    val miles_per_hour: String
): Parcelable

@Parcelize
data class FeetX(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class KilometersX(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class MetersX(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable

@Parcelize
data class MilesX(
    val estimated_diameter_max: Double,
    val estimated_diameter_min: Double
): Parcelable
