package com.example.mobillabor.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobillabor.R
import com.example.mobillabor.model.RecipeDetail
import com.example.mobillabor.model.RecipeDetailList
import com.example.mobillabor.repository.DataState
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var mealId: Int? = null
    private var categoryName: String? = null
    private lateinit var currentRecipe: RecipeDetail
    private lateinit var name: TextView
    private lateinit var image: ImageView
    private lateinit var ingredients: TableLayout
    private lateinit var preparation: TextView
    private lateinit var edit: FloatingActionButton
    private lateinit var delete: FloatingActionButton
    private var fromDb: Boolean = false

    private val recipeDetailViewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getInt("mealId")
        }
        arguments?.let {
            categoryName = it.getString("categoryName")
        }
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bundle = bundleOf("categoryName" to categoryName)
                findNavController().navigate(
                    R.id.action_recipe_detail_fragment_to_recipe_list_fragment,
                    bundle
                )
            }
        })

        mealId?.let {
            recipeDetailViewModel.start(it)
            recipeDetailViewModel.startDb(it)
        }

        subscribeObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.recipe_name)
        image = view.findViewById(R.id.recipe_image)
        ingredients = view.findViewById(R.id.recipe_ingredients)
        preparation = view.findViewById(R.id.recipe_instructions)
        edit = view.findViewById(R.id.edit_fab)
        delete = view.findViewById(R.id.delete_fab)

        edit.setOnClickListener {
            val bundle = bundleOf("mealId" to mealId, "categoryName" to categoryName)
            findNavController().navigate(
                R.id.action_recipe_detail_fragment_to_add_or_update_fragment,
                bundle
            )
        }
        edit.setOnClickListener {
            val bundle = bundleOf("mealId" to mealId, "categoryName" to categoryName)
            findNavController().navigate(
                R.id.action_recipe_detail_fragment_to_add_or_update_fragment,
                bundle
            )
        }
        delete.setOnClickListener {
            recipeDetailViewModel.deleteRecipe(currentRecipe)
            val bundle = bundleOf("categoryName" to categoryName)
            findNavController().navigate(
                R.id.action_recipe_detail_fragment_to_recipe_list_fragment,
                bundle
            )
            Toast.makeText(context, "Recipe successfully deleted!", Toast.LENGTH_SHORT).show()
        }

    }

    private fun subscribeObservers() {
        recipeDetailViewModel.dataState.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Loading -> {

                }
                is DataState.Success<RecipeDetailList> -> {
                    recipeDetailViewModel.dataStateDb.observe(this, Observer { dataStateDb ->
                        when (dataStateDb) {
                            is DataState.Loading -> {

                            }
                            is DataState.Success<RecipeDetail> -> {
                                currentRecipe = if (dataStateDb.data == null) {
                                    fromDb = false
                                    dataState.data.meals[0]

                                } else {
                                    fromDb = true
                                    dataStateDb.data
                                }
                                displayData(currentRecipe)
                            }
                            else -> {
                                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    })
                }
                else -> {
                    Toast.makeText(context, "Can't find this recipe", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun displayData(recipe: RecipeDetail) {
        name.text = recipe.strMeal
        Glide.with(this)
            .load(recipe.strMealThumb)
            .into(image)
        preparation.text = recipe.strInstructions

        createArrayListFromIngredients(recipe)
    }

    private fun createArrayListFromIngredients(recipe: RecipeDetail) {
        val ingredientList = ArrayList<String>()
        val measures = ArrayList<String>()
        ingredientList.add(recipe.strIngredient1)
        ingredientList.add(recipe.strIngredient2)
        ingredientList.add(recipe.strIngredient3)
        ingredientList.add(recipe.strIngredient4)
        ingredientList.add(recipe.strIngredient5)
        ingredientList.add(recipe.strIngredient6)
        ingredientList.add(recipe.strIngredient7)
        ingredientList.add(recipe.strIngredient8)
        ingredientList.add(recipe.strIngredient9)
        ingredientList.add(recipe.strIngredient10)
        ingredientList.add(recipe.strIngredient11)
        ingredientList.add(recipe.strIngredient12)
        ingredientList.add(recipe.strIngredient13)
        ingredientList.add(recipe.strIngredient14)
        ingredientList.add(recipe.strIngredient15)
        ingredientList.add(recipe.strIngredient16)
        ingredientList.add(recipe.strIngredient17)
        ingredientList.add(recipe.strIngredient18)
        ingredientList.add(recipe.strIngredient19)
        ingredientList.add(recipe.strIngredient20)
        measures.add(recipe.strMeasure1)
        measures.add(recipe.strMeasure2)
        measures.add(recipe.strMeasure3)
        measures.add(recipe.strMeasure4)
        measures.add(recipe.strMeasure5)
        measures.add(recipe.strMeasure6)
        measures.add(recipe.strMeasure7)
        measures.add(recipe.strMeasure8)
        measures.add(recipe.strMeasure9)
        measures.add(recipe.strMeasure10)
        measures.add(recipe.strMeasure11)
        measures.add(recipe.strMeasure12)
        measures.add(recipe.strMeasure13)
        measures.add(recipe.strMeasure14)
        measures.add(recipe.strMeasure15)
        measures.add(recipe.strMeasure16)
        measures.add(recipe.strMeasure17)
        measures.add(recipe.strMeasure18)
        measures.add(recipe.strMeasure19)
        measures.add(recipe.strMeasure20)

        var counter = 0
        ingredientList.forEach {
            if (it != "" && it != null) {
                val row = TableRow(context)
                val cell1 = TextView(context)
                cell1.text = it + "    "
                val cell2 = TextView(context)
                cell2.text = measures[counter]
                row.addView(cell1)
                row.addView(cell2)
                ingredients.addView(row)
            }
            counter++
        }
    }
}
