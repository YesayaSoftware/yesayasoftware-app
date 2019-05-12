package com.yesayasoftware.learning.ui.podcasts.list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yesayasoftware.learning.R

class PodcastFragment : Fragment() {

    companion object {
        fun newInstance() = PodcastFragment()
    }

    private lateinit var viewModel: PodcastViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.podcast_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PodcastViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
