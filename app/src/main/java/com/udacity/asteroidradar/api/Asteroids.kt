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

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Asteroids (
    val links: Links,

    @Json(name = "element_count")
    val elementCount: Long,

    @Json(name = "near_earth_objects")
    val nearEarthObjects: Map<String, List<NearEarthObject>>
)

data class Links (
    val next: String,
    val prev: String,
    val self: String
)

data class NearEarthObject (
    val links: NearEarthObjectLinks,
    val id: String,

    @Json(name = "neo_reference_id")
    val neoReferenceID: String,

    val name: String,

    @Json(name = "nasa_jpl_url")
    val nasaJplURL: String,

    @Json(name = "absolute_magnitude_h")
    val absoluteMagnitudeH: Double,

    @Json(name = "estimated_diameter")
    val estimatedDiameter: EstimatedDiameter,

    @Json(name = "is_potentially_hazardous_asteroid")
    val isPotentiallyHazardousAsteroid: Boolean,

    @Json(name = "close_approach_data")
    val closeApproachData: List<CloseApproachDatum>,

    @Json(name = "is_sentry_object")
    val isSentryObject: Boolean
)

data class CloseApproachDatum (
    @Json(name = "close_approach_date")
    val closeApproachDate: String,

    @Json(name = "close_approach_date_full")
    val closeApproachDateFull: String,

    @Json(name = "epoch_date_close_approach")
    val epochDateCloseApproach: Long,

    @Json(name = "relative_velocity")
    val relativeVelocity: RelativeVelocity,

    @Json(name = "miss_distance")
    val missDistance: MissDistance,

    @Json(name = "orbiting_body")
    val orbitingBody: OrbitingBody
)

data class MissDistance (
    val astronomical: String,
    val lunar: String,
    val kilometers: String,
    val miles: String
)

enum class OrbitingBody(val value: String) {
    Earth("Earth");

    companion object {
        public fun fromValue(value: String): OrbitingBody = when (value) {
            "Earth" -> Earth
            else    -> throw IllegalArgumentException()
        }
    }
}

data class RelativeVelocity (
    @Json(name = "kilometers_per_second")
    val kilometersPerSecond: String,

    @Json(name = "kilometers_per_hour")
    val kilometersPerHour: String,

    @Json(name = "miles_per_hour")
    val milesPerHour: String
)

data class EstimatedDiameter (
    val kilometers: Feet,
    val meters: Feet,
    val miles: Feet,
    val feet: Feet
)

data class Feet (
    @Json(name = "estimated_diameter_min")
    val estimatedDiameterMin: Double,

    @Json(name = "estimated_diameter_max")
    val estimatedDiameterMax: Double
)

data class NearEarthObjectLinks (
    val self: String
)
