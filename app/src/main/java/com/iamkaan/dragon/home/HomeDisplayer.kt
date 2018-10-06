package com.iamkaan.dragon.home

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.iamkaan.dragon.R

class HomeDisplayer(rootView: View) {

    private val recyclerView: RecyclerView = rootView.findViewById(R.id.dragons)
    private val fab: FloatingActionButton = rootView.findViewById(R.id.fab)
    private val adapter = DragonAdapter()

    init {
        recyclerView.layoutManager = LinearLayoutManager(rootView.context)
        recyclerView.adapter = adapter
    }

    fun displayEmpty(viewModel: HomeViewModel) {
        setFabListener(viewModel)
    }

    fun display(viewModel: HomeViewModel) {
        adapter.updateViewModels(viewModel.list)
        setFabListener(viewModel)
    }

    private fun setFabListener(viewModel: HomeViewModel) {
        fab.setOnClickListener {
            viewModel.onFabClick()
        }
    }
}

data class HomeViewModel(val list: List<DragonViewModel> = emptyList(), val onFabClick: () -> Unit)
