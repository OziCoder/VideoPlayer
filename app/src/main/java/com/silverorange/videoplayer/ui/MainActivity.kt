package com.silverorange.videoplayer.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.databinding.ActivityMainBinding
import com.silverorange.videoplayer.models.OutResult
import com.silverorange.videoplayer.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getVideos(OutResult(null, AppConstants.MSG_LOADING))
        viewModel.resultSet.observe(this, Observer {
            when (it.message) {
                AppConstants.MSG_LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                AppConstants.MSG_SUCCESS -> {
                    binding.progress.visibility = View.GONE
                }
                else -> {
                    binding.progress.visibility = View.GONE
                    Snackbar.make(
                        this.findViewById(android.R.id.content),
                        it.message,
                        Snackbar.LENGTH_LONG
                    )
                        .setTextColor(Color.WHITE).setBackgroundTint(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.snack_red
                            )
                        )
                        .show()
                }
            }
        })
    }
}