package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.POAD
import com.udacity.asteroidradar.database.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class MainFragment : Fragment() {
    val uiScope = CoroutineScope(Dispatchers.Main)

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        uiScope.launch {
            try {
                var poad: POAD = AsteroidApi.retrofitService.getPictureOfADay(
                    "9exBRke8WpRX7E1yNPRf1EzOi60Z1jA8iGjHdQTZ"
                )
                val imageViewOfTheDay: ImageView? =
                    getView()?.findViewById<ImageView>(R.id.activity_main_image_of_the_day)
                Picasso.get().load(poad.url).into(imageViewOfTheDay)
                imageViewOfTheDay?.contentDescription = poad.explanation
            } catch (e: Exception) {
                Log.i(
                    "Picasso",
                    "Couldn't load picture of the day from resource provided by NASA API."
                )
            }
        }
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )
        val application = requireNotNull(this.activity).application

        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao

        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val MainViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(MainViewModel::class.java)
        binding.viewModel = MainViewModel

        binding.lifecycleOwner = this


        setHasOptionsMenu(true)

        val adapter = AsteroidsAdapter(AsteroidsListener { asteroidId ->
            MainViewModel.onAsteroidClicked(asteroidId)
        })

        binding.asteroidRecycler.adapter = adapter
        adapter.submitList(
            listOf(
                Asteroid(2, "Bolero", Date(2021, 8, 20), 0.12, 0.5,  0.2,0.2555,true),
                Asteroid(1, "Antares", Date(2021, 8, 22), 0.12, 0.5, 0.2,0.2555,true)
            )
        )

        MainViewModel.asteroids.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                Log.i("antares", it.toString())
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
