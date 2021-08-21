package com.andreiliphd.asteroidradar.api

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Asteroid (

	@SerializedName("links") val links : Links,
	@SerializedName("id") val id : Int,
	@SerializedName("neo_reference_id") val neo_reference_id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("nasa_jpl_url") val nasa_jpl_url : String,
	@SerializedName("absolute_magnitude_h") val absolute_magnitude_h : Double,
	@SerializedName("estimated_diameter") val estimated_diameter : Estimated_diameter,
	@SerializedName("is_potentially_hazardous_asteroid") val is_potentially_hazardous_asteroid : Boolean,
	@SerializedName("close_approach_data") val close_approach_data : List<Close_approach_data>,
	@SerializedName("is_sentry_object") val is_sentry_object : Boolean
)