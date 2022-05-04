package com.matteopasotti.whatmovie.view.ui.youtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.matteopasotti.whatmovie.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class YoutubeFragment : Fragment() {

    private lateinit var youtubeView: YouTubePlayerView

    companion object {

        private const val VIDEO_KEY = "video_key"

        fun newInstance(videoKey: String): YoutubeFragment {
            val args = Bundle()
            args.putString(VIDEO_KEY, videoKey)
            val fragment = YoutubeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.container_youtube_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        youtubeView = view.findViewById(R.id.youtubeView)

        lifecycle.addObserver(youtubeView)
        val videoKey = arguments?.getString(VIDEO_KEY)

        youtubeView.getYouTubePlayerWhenReady(object: YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                videoKey?.let {
                    youTubePlayer.loadVideo(it, 0f)
                    youTubePlayer.play()
                }
            }

        })
    }

    override fun onDestroyView() {
        youtubeView.release()
        super.onDestroyView()
    }
}