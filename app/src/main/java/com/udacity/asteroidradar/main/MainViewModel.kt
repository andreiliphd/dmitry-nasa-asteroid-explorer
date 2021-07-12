package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.api.MarsApi
import com.udacity.asteroidradar.api.MarsProperty
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val database: AsteroidDatabaseDao,
                    application: Application
) : AndroidViewModel(application) {

        private var asteroid = MutableLiveData<Asteroid?>()

        val asteroids = database.getAllasteroids()
   

 
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

        init {
            initializeasteroid()
        }

        private fun initializeasteroid() {
            viewModelScope.launch {
                asteroid.value = getasteroidFromDatabase()
            }
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
            var asteroid = database.getSelectedAsteroid()
            if (asteroid?.endTimeMilli != asteroid?.startTimeMilli) {
                asteroid = null
            }
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