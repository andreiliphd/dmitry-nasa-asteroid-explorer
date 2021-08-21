package com.andreiliphd.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*


@Parcelize
@Entity(tableName = "asteroids_table")
data class Asteroid(@PrimaryKey(autoGenerate = true)
                    var id: Long,
                    @ColumnInfo(name = "codename")
                    var codename: String,
                    @ColumnInfo(name = "closeApproachDate")
                    var closeApproachDate: Date,
                    @ColumnInfo(name = "absoluteMagnitude")
                    var absoluteMagnitude: Double,
                    @ColumnInfo(name = "estimatedDiameter")
                    var estimatedDiameter: Double,
                    @ColumnInfo(name = "relativeVelocity")
                    var relativeVelocity: Double,
                    @ColumnInfo(name = "distanceFromEarth")
                    var distanceFromEarth: Double,
                    @ColumnInfo(name = "isPotentiallyHazardous")
                    var isPotentiallyHazardous: Boolean) : Parcelable