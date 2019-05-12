package com.yesayasoftware.learning.ui.posts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import com.yesayasoftware.learning.R
import com.yesayasoftware.learning.data.db.converter.ConvertPostEntry
import com.yesayasoftware.learning.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.post_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class PostFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory : PostListViewModelFactory by instance()

    private lateinit var viewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.post_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PostViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val postEntries = viewModel.postEntries.await()

        postEntries.observe(this@PostFragment, Observer { entries ->
            if (entries == null) return@Observer

            group_loading.visibility = View.GONE

            initRecyclerView(entries.toPostItems())
        })
    }

    private fun List<ConvertPostEntry>.toPostItems() : List<PostItem> {
        return this.map {
            PostItem(it)
        }
    }

    private fun initRecyclerView(items: List<PostItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@PostFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            Toast.makeText(this@PostFragment.context, "Hi", Toast.LENGTH_LONG).show()
        }
    }
}