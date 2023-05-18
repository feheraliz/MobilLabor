package com.example.mobillabor.ui.categoryList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobillabor.R
import com.example.mobillabor.model.Category
import com.example.mobillabor.model.CategoryList
import com.example.mobillabor.repository.DataState
import com.example.mobillabor.ui.categoryList.adapter.CategoryListRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryListFragment : Fragment(), CategoryListRecyclerViewAdapter.CategoryListClickListener {

    private val viewModel: CategoryListViewModel by viewModels()
    private lateinit var list: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.start()
        subscribeObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.list)
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when(dataState) {
                is DataState.Loading -> {

                }
                is DataState.Success<CategoryList> -> {
                    setupRecyclerView(dataState.data.categories)

                }
                else -> {}
            }
        })
    }

    private fun setupRecyclerView(categories: List<Category>) {
        list.layoutManager = GridLayoutManager(context, 2)
        list.adapter = CategoryListRecyclerViewAdapter(categories, this)
    }
    override fun onClickCategoryList(category: String) {
        val bundle = bundleOf("categoryName" to category)
        Navigation.findNavController(this.requireView())
            .navigate(R.id.action_category_list_fragment_to_recipe_list_fragment, bundle)
    }
}