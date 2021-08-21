package com.andreiliphd.asteroidradar.database

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.andreiliphd.asteroidradar.api.AsteroidApi
import com.andreiliphd.asteroidradar.api.Asteroids
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

public class AsteroidRepository(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    val applicationScope = CoroutineScope(Dispatchers.Default)


    val databaseDao = AsteroidDatabase.getInstance(appContext).asteroidDatabaseDao
//    val asteroids: LiveData<List<Asteroid>> =
//        Transformations.map(databaseDao.getAllasteroids()) {
//
//        }


    override suspend fun doWork(): Result {
        return try {
            databaseDao.clear()
            refreshAsteroids()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
    /**
     * Refresh the videos stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the videos for use, observe [videos]
     */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                val currentDate = sdf.format(Date())
                val sevenDaysPlusDate = sdf.format(Date().time + 7 * 24 * 3600 * 1000)
                Log.i("time-worker", currentDate.toString())
                Log.i("time-worker", sevenDaysPlusDate.toString())
                var response: Asteroids = AsteroidApi.retrofitService.getAsteroids(
                    currentDate.toString(),
                    sevenDaysPlusDate.toString(),
                    "9exBRke8WpRX7E1yNPRf1EzOi60Z1jA8iGjHdQTZ"
                )
                Log.i("retrofit-refresh", response.toString())
                var parsed: List<List<Asteroid>> = response.near_earth_objects.map {(key, value) ->
                    value.map {
                        Asteroid(
                            it.id.toLong(),
                            it.name.toString(),
                            it.close_approach_data.get(0).close_approach_date,
                            it.absolute_magnitude_h.toDouble(),
                            it.estimated_diameter.kilometers.estimated_diameter_max.toDouble(),
                            it.close_approach_data.get(0).relative_velocity.kilometers_per_second.toDouble(),
                            it.close_approach_data.get(0).miss_distance.astronomical.toDouble(),
                            it.is_potentially_hazardous_asteroid)
                    }
                }
                for (item: Asteroid in parsed.flatten()) {
                    databaseDao.insert(item)
                }
                Log.i("parsed-array", parsed.flatten().toString())

            } catch (e: Exception) {
                Log.i("retrofit", "Couldn't load resource from NASA API from worker." + e)
            }
        }
    }

}
