package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.*
import androidx.work.*
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.POAD
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import com.udacity.asteroidradar.database.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class MainViewModel(
    val database: AsteroidDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var asteroid = MutableLiveData<Asteroid?>()

    val asteroids = database.getAllasteroids()

    init {
        initializeasteroid()
    }

    val startButtonVisible = Transformations.map(asteroid) {
        null == it
    }

    val stopButtonVisible = Transformations.map(asteroid) {
        null != it
    }

    val clearButtonVisible = Transformations.map(asteroids) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent


    private val _navigateToSleepQuality = MutableLiveData<Asteroid>()


    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    /**
     * If this is non-null, immediately navigate to [SleepQualityFragment] and call [doneNavigating]
     */
    val navigateToSleepQuality: LiveData<Asteroid>
        get() = _navigateToSleepQuality

    /**
     * Call this immediately after navigating to [SleepQualityFragment]
     *
     * It will clear the navigation request, so if the user rotates their phone it won't navigate
     * twice.
     */
    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private val _navigateToSleepDataQuality = MutableLiveData<Long>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onAsteroidClicked(id: Long) {
        _navigateToSleepDataQuality.value = id
    }

    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

    private fun initializeasteroid() {
        val updateDatabase = OneTimeWorkRequestBuilder<AsteroidRepository>().build()
        WorkManager.getInstance().enqueue(updateDatabase)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeatingRequest
                = PeriodicWorkRequestBuilder<AsteroidRepository>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidRepository.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */
    private suspend fun getasteroidFromDatabase(): Asteroid? {
        //return withContext(Dispatchers.IO) {
        var asteroid = database.getToasteroid()
        return asteroid
        //}
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun update(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.update(asteroid)
        }
    }

    private suspend fun insert(asteroid: Asteroid) {
        withContext(Dispatchers.IO) {
            database.insert(asteroid)
        }
    }
}