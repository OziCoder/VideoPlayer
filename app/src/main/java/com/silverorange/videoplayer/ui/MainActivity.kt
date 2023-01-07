package com.silverorange.videoplayer.ui

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.silverorange.videoplayer.R
import com.silverorange.videoplayer.databinding.ActivityMainBinding
import com.silverorange.videoplayer.models.OutResult
import com.silverorange.videoplayer.models.Videos
import com.silverorange.videoplayer.utils.AppConstants
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var videos: ArrayList<Videos>
    private var position = 0
    private var pausePosition = 0
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getVideos(OutResult(null, AppConstants.MSG_LOADING))
        viewModel.resultSet.observe(this) {
            when (it.message) {
                AppConstants.MSG_LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                AppConstants.MSG_SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    it.videos?.let {
                        videos = ArrayList()
                        var vd = it.sortedByDescending {
                            it.publishedAt?.toDate()
                        }
                        videos.addAll(vd)
                        binding.btnPlayPause.setImageResource(R.drawable.ic_play)
                        position = 0
                        playVideo(position, false)
                    }
                }
                else -> {
                    binding.progress.visibility = View.GONE
                    Snackbar.make(
                        this.findViewById(android.R.id.content),
                        it.message,
                        Snackbar.LENGTH_LONG
                    ).setTextColor(Color.WHITE).setBackgroundTint(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.snack_red
                        )
                    ).show()
                }
            }
        }
        binding.btnPlayPause.setOnClickListener {
            if (binding.videoView.isPlaying) pauseVideo()
            else playVideo(position, true)
        }
        binding.btnNxt.setOnClickListener {
            nextPrevVideo()
        }
        binding.btnPrev.setOnClickListener {
            nextPrevVideo(isNext = false)
        }
    }

    private fun String.toDate(): Date {
        return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH).parse(this)
    }

    private fun nextPrevVideo(isNext: Boolean = true) {
        position = if (isNext) viewModel.setPosition(videos, position)
        else viewModel.setPosition(videos, position, isIncrement = false)
        playVideo(position, true)
    }

    private fun pauseVideo() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_play)
        pausePosition = binding.videoView.currentPosition
        binding.videoView.pause()
        isPaused = true
    }

    private fun playVideo(position: Int, play: Boolean) {
        try {
            if (isPaused) {
                binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
                binding.videoView.seekTo(pausePosition)
                binding.videoView.start()
                isPaused = false
            } else {
                val uri: Uri = Uri.parse(videos?.get(position)?.hlsURL)
                binding.videoView.setVideoURI(uri)
                binding.title.text = videos?.get(position).title
                binding.author.text = videos?.get(position).author?.name
                var markwon = Markwon.create(this)
                var node = markwon.parse(videos?.get(position)?.description.toString())
                var spannedMarkWon = markwon.render(node)
                markwon.setParsedMarkdown(binding.description, spannedMarkWon)
                if (play) {
                    binding.btnPlayPause.setImageResource(R.drawable.ic_pause)
                    if (position == videos.size - 1) {
                        binding.btnPrev.isEnabled = true
                        binding.btnNxt.isEnabled = false
                    } else if (position == 0) {
                        binding.btnPrev.isEnabled = false
                        binding.btnNxt.isEnabled = true
                    }
                    binding.videoView.setOnPreparedListener {
                        binding.videoView.start()
                    }
                    binding.videoView.setOnCompletionListener {
                        binding.btnPlayPause.setImageResource(R.drawable.ic_play)
                    }
                }
            }
        } catch (e: Exception) {
            Snackbar.make(
                this.findViewById(android.R.id.content),
                e.localizedMessage.toString(),
                Snackbar.LENGTH_LONG
            ).setTextColor(Color.WHITE).setBackgroundTint(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.snack_red
                )
            ).show()
        }
    }
}