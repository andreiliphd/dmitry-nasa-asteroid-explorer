package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao

        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val MainViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(MainViewModel::class.java)
        binding.viewModel = MainViewModel

        binding.lifecycleOwner = this


        setHasOptionsMenu(true)

        val adapter = AsteroidsAdapter(AsteroidsListener { nightId ->
            MainViewModel.onAsteroidClicked(nightId)
        })

        binding.asteroidRecycler.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageViewOfTheDay: ImageView? =
            getView()?.findViewById<ImageView>(R.id.activity_main_image_of_the_day)
        Picasso.get().load("https://images.newscientist.com/wp-content/uploads/2021/04/21160754/21-april_pepper-the-robot.jpg?width=800").into(imageViewOfTheDay )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
