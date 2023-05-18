package com.example.mobillabor.ui.recipeList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobillabor.R
import com.example.mobillabor.model.*
import com.example.mobillabor.repository.DataState
import com.example.mobillabor.ui.recipeList.adapter.RecipeListRecyclerViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment : Fragment(), RecipeListRecyclerViewAdapter.RecipeListClickListener {

    private val viewModel: RecipeListViewModel by viewModels()
    private lateinit var list: RecyclerView
    private var categoryName: String? = null
    private lateinit var addButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString("categoryName")
        }

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_recipe_list_fragment_to_category_list_fragment)
            }
        })

        categoryName?.let {
            viewModel.startApi(it)
            viewModel.startDb(it)
        }
        subscribeObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = view.findViewById(R.id.list)
        addButton = view.findViewById(R.id.add_fab)

        addButton.setOnClickListener {
            val bundle = bundleOf("categoryName" to categoryName)
            findNavController().navigate(
                R.id.action_recipe_list_fragment_to_add_or_update_fragment,
                bundle
            )
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Loading -> {

                }
                is DataState.Success<RecipeList> -> {
                    viewModel.dataStateDb.observe(this, Observer { dataStateDb ->
                        when (dataStateDb) {
                            is DataState.Loading -> {
                            }
                            is DataState.Success<List<Recipe>> -> {
                                setupRecyclerView(
                                    viewModel.getValidList(
                                        dataState.data.meals,
                                        dataStateDb.data
                                    )
                                )
                            }
                            else -> {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
                }
                else -> {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    private fun setupRecyclerView(recipes: List<Recipe>) {
        list.layoutManager = GridLayoutManager(context, 2)
        list.adapter = RecipeListRecyclerViewAdapter(recipes, this)
    }

    override fun onClickRecipeList(recipe: Int) {
        val bundle = Bundle().apply {
            putInt("mealId", recipe)
            putString("categoryName", categoryName!!)
        }
        Navigation.findNavController(this.requireView())
            .navigate(R.id.action_recipe_list_fragment_to_recipe_detail_fragment, bundle)
    }

}