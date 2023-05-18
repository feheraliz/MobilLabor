package com.example.mobillabor.ui.addOrUpdate

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mobillabor.R
import com.example.mobillabor.model.CategoryList
import com.example.mobillabor.model.RecipeDetail
import com.example.mobillabor.model.RecipeDetailList
import com.example.mobillabor.repository.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class AddOrUpdateRecipeFragment : Fragment() {

    private var mealId: Int? = null
    private var categoryName: String? = null
    private lateinit var currentRecipe: RecipeDetail
    private var fromDb: Boolean = false
    private var imageUrl: String = ""

    private lateinit var text: EditText
    private lateinit var preparation: EditText
    private lateinit var spinner: Spinner
    private lateinit var selectedSpinnerItem: String
    private lateinit var saveButton: Button
    private lateinit var addImage: Button
    private lateinit var image: ImageView
    private lateinit var categoryTextView: TextView

    private lateinit var ingredient1: EditText
    private lateinit var ingredient2: EditText
    private lateinit var ingredient3: EditText
    private lateinit var ingredient4: EditText
    private lateinit var ingredient5: EditText
    private lateinit var ingredient6: EditText
    private lateinit var ingredient7: EditText
    private lateinit var ingredient8: EditText
    private lateinit var ingredient9: EditText
    private lateinit var ingredient10: EditText

    private lateinit var amount1: EditText
    private lateinit var amount2: EditText
    private lateinit var amount3: EditText
    private lateinit var amount4: EditText
    private lateinit var amount5: EditText
    private lateinit var amount6: EditText
    private lateinit var amount7: EditText
    private lateinit var amount8: EditText
    private lateinit var amount9: EditText
    private lateinit var amount10: EditText

    private val viewModel: AddOrUpdateRecipeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_or_update_recipe, container, false)
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
                if (mealId == null || mealId == 0) {
                    val bundle = bundleOf("categoryName" to categoryName)
                    findNavController().navigate(
                        R.id.action_add_or_update_fragment_to_recipe_list_fragment,
                        bundle
                    )
                } else {
                    val bundle = Bundle().apply {
                        putInt("mealId", mealId!!)
                        putString("categoryName", categoryName!!)

                    }
                    findNavController().navigate(
                        R.id.action_add_or_update_fragment_to_recipe_detail_fragment, bundle
                    )
                }
            }
        })

        mealId?.let {
            if (mealId != 0){
                viewModel.start(it)
                viewModel.startDb(it)
            }
        }
        viewModel.getCategories()
        subscribeObservers()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        text = view.findViewById(R.id.titleEditText)
        preparation = view.findViewById(R.id.preparationEditText)
        spinner = view.findViewById(R.id.spinner)
        addImage = view.findViewById(R.id.addImageButton)
        saveButton = view.findViewById(R.id.saveButton)
        image = view.findViewById(R.id.addUpdateImage)
        categoryTextView = view.findViewById(R.id.categoryTextView)


        ingredient1 = view.findViewById(R.id.ingredient1)
        ingredient2 = view.findViewById(R.id.ingredient2)
        ingredient3 = view.findViewById(R.id.ingredient3)
        ingredient4 = view.findViewById(R.id.ingredient4)
        ingredient5 = view.findViewById(R.id.ingredient5)
        ingredient6 = view.findViewById(R.id.ingredient6)
        ingredient7 = view.findViewById(R.id.ingredient7)
        ingredient8 = view.findViewById(R.id.ingredient8)
        ingredient9 = view.findViewById(R.id.ingredient9)
        ingredient10 = view.findViewById(R.id.ingredient10)

        amount1 = view.findViewById(R.id.amount1)
        amount2 = view.findViewById(R.id.amount2)
        amount3 = view.findViewById(R.id.amount3)
        amount4 = view.findViewById(R.id.amount4)
        amount5 = view.findViewById(R.id.amount5)
        amount6 = view.findViewById(R.id.amount6)
        amount7 = view.findViewById(R.id.amount7)
        amount8 = view.findViewById(R.id.amount8)
        amount9 = view.findViewById(R.id.amount9)
        amount10 = view.findViewById(R.id.amount10)

        addImage.setOnClickListener {
            selectImage()
        }

        saveButton.setOnClickListener {
            addOrUpdateRecipe()
        }

        if (mealId != 0 && mealId != null){
            categoryTextView.visibility = View.GONE
            spinner.visibility = View.GONE

        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedSpinnerItem = parent?.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedSpinnerItem = categoryName.toString()
            }
        }

    }

    private fun addOrUpdateRecipe() {
        if (mealId != null && mealId != 0) {
            if (imageUrl != ""){
                currentRecipe.strMealThumb = imageUrl
            }

            currentRecipe.strMeal = text.text.toString()
            currentRecipe.strInstructions = preparation.text.toString()
            currentRecipe.strIngredient1 = ingredient1.text.toString()
            currentRecipe.strIngredient2 = ingredient2.text.toString()
            currentRecipe.strIngredient3 = ingredient3.text.toString()
            currentRecipe.strIngredient4 = ingredient4.text.toString()
            currentRecipe.strIngredient5 = ingredient5.text.toString()
            currentRecipe.strIngredient6 = ingredient6.text.toString()
            currentRecipe.strIngredient7 = ingredient7.text.toString()
            currentRecipe.strIngredient8 = ingredient8.text.toString()
            currentRecipe.strIngredient9 = ingredient9.text.toString()
            currentRecipe.strIngredient10 = ingredient10.text.toString()

            currentRecipe.strMeasure1 = amount1.text.toString()
            currentRecipe.strMeasure2 = amount2.text.toString()
            currentRecipe.strMeasure3 = amount3.text.toString()
            currentRecipe.strMeasure4 = amount4.text.toString()
            currentRecipe.strMeasure5 = amount5.text.toString()
            currentRecipe.strMeasure6 = amount6.text.toString()
            currentRecipe.strMeasure7 = amount7.text.toString()
            currentRecipe.strMeasure8 = amount8.text.toString()
            currentRecipe.strMeasure9 = amount9.text.toString()
            currentRecipe.strMeasure10 = amount10.text.toString()

            setUnusedValues(currentRecipe)

            if (fromDb) {
                viewModel.updateRecipe(currentRecipe)
            }
            else {
                viewModel.addRecipe(currentRecipe)
            }

            val bundle = Bundle().apply {
                putInt("mealId", mealId!!)
                putString("categoryName", categoryName!!)

            }
            findNavController().navigate(
                R.id.action_add_or_update_fragment_to_recipe_detail_fragment, bundle
            )
        } else {
            val newRecipe = RecipeDetail(
                idMeal = Random.nextInt(100000, 999999),
                strMealThumb = imageUrl,
                strMeal = text.text.toString(),
                strInstructions = preparation.text.toString(),
                strIngredient1 = ingredient1.text.toString(),
                strIngredient2 = ingredient2.text.toString(),
                strIngredient3 = ingredient3.text.toString(),
                strIngredient4 = ingredient4.text.toString(),
                strIngredient5 = ingredient5.text.toString(),
                strIngredient6 = ingredient6.text.toString(),
                strIngredient7 = ingredient7.text.toString(),
                strIngredient8 = ingredient8.text.toString(),
                strIngredient9 = ingredient9.text.toString(),
                strIngredient10 = ingredient10.text.toString(),
                strMeasure1 = amount1.text.toString(),
                strMeasure2 = amount2.text.toString(),
                strMeasure3 = amount3.text.toString(),
                strMeasure4 = amount4.text.toString(),
                strMeasure5 = amount5.text.toString(),
                strMeasure6 = amount6.text.toString(),
                strMeasure7 = amount7.text.toString(),
                strMeasure8 = amount8.text.toString(),
                strMeasure9 = amount9.text.toString(),
                strMeasure10 = amount10.text.toString(),
                strCategory = selectedSpinnerItem
            )
            viewModel.addRecipe(newRecipe)
            val bundle = bundleOf("categoryName" to categoryName)
            findNavController().navigate(
                R.id.action_add_or_update_fragment_to_recipe_list_fragment,
                bundle
            )
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            imageUrl = data?.data.toString()
            Glide.with(this)
                .load(data?.data)
                .into(image)
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 200)
    }

    private fun subscribeObservers() {
        if (mealId != null) {
            viewModel.dataState.observe(this, Observer { dataState ->
                when (dataState) {
                    is DataState.Loading -> {

                    }
                    is DataState.Success<RecipeDetailList> -> {
                        viewModel.dataStateDb.observe(this, Observer { dataStateDb ->
                            when (dataStateDb) {
                                is DataState.Loading -> {

                                }
                                is DataState.Success<RecipeDetail> -> {
                                    currentRecipe = if (dataStateDb.data == null) {
                                        fromDb = false
                                        dataState.data.meals[0]

                                    }else {
                                        fromDb = true
                                        dataStateDb.data
                                    }

                                    displayData(currentRecipe)
                                }
                                else -> {
                                    Toast.makeText(context, "Can't find this recipe", Toast.LENGTH_SHORT).show()
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

        viewModel.dataStateCategory.observe(this, Observer { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                }
                is DataState.Success<CategoryList> -> {

                    displayCategories(dataState.data)
                }
                else -> {
                    Toast.makeText(context, "Can't find categories", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun displayCategories(data: CategoryList) {
        val items = data.categories.map {
            it.strCategory
        }
        val adapter = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, items) }
        spinner.adapter = adapter
    }


    private fun displayData(recipe: RecipeDetail) {
        Glide.with(this)
            .load(recipe.strMealThumb)
            .into(image)
        text.setText(recipe.strMeal)
        preparation.setText(recipe.strInstructions)
        ingredient1.setText(recipe.strIngredient1)
        ingredient2.setText(recipe.strIngredient2)
        ingredient3.setText(recipe.strIngredient3)
        ingredient4.setText(recipe.strIngredient4)
        ingredient5.setText(recipe.strIngredient5)
        ingredient6.setText(recipe.strIngredient6)
        ingredient7.setText(recipe.strIngredient7)
        ingredient8.setText(recipe.strIngredient8)
        ingredient9.setText(recipe.strIngredient9)
        ingredient10.setText(recipe.strIngredient10)

        amount1.setText(recipe.strMeasure1)
        amount2.setText(recipe.strMeasure2)
        amount3.setText(recipe.strMeasure3)
        amount4.setText(recipe.strMeasure4)
        amount5.setText(recipe.strMeasure5)
        amount6.setText(recipe.strMeasure6)
        amount7.setText(recipe.strMeasure7)
        amount8.setText(recipe.strMeasure8)
        amount9.setText(recipe.strMeasure9)
        amount10.setText(recipe.strMeasure10)
    }

    private fun setUnusedValues(currentRecipe: RecipeDetail) {
        currentRecipe.strDrinkAlternate = ""
        currentRecipe.strArea = ""
        currentRecipe.strSource = ""
        currentRecipe.strImageSource = ""
        currentRecipe.strCreativeCommonsConfirmed = ""
        currentRecipe.dateModified = ""
        currentRecipe.strIngredient11 = ""
        currentRecipe.strIngredient12 = ""
        currentRecipe.strIngredient13 = ""
        currentRecipe.strIngredient14 = ""
        currentRecipe.strIngredient15 = ""
        currentRecipe.strIngredient16 = ""
        currentRecipe.strIngredient17 = ""
        currentRecipe.strIngredient18 = ""
        currentRecipe.strIngredient19 = ""
        currentRecipe.strIngredient20 = ""
        currentRecipe.strMeasure11 = ""
        currentRecipe.strMeasure12 = ""
        currentRecipe.strMeasure13 = ""
        currentRecipe.strMeasure14 = ""
        currentRecipe.strMeasure15 = ""
        currentRecipe.strMeasure16 = ""
        currentRecipe.strMeasure17 = ""
        currentRecipe.strMeasure18 = ""
        currentRecipe.strMeasure19 = ""
        currentRecipe.strMeasure20 = ""
        currentRecipe.strTags = ""
    }
}