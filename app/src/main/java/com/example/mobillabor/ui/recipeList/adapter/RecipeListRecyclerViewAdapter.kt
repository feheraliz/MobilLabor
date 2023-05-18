package com.example.mobillabor.ui.recipeList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mobillabor.databinding.RecipeListItemBinding
import com.example.mobillabor.model.Recipe

class RecipeListRecyclerViewAdapter(
    private val values: List<Recipe>,
    private val onClick: RecipeListClickListener
) : RecyclerView.Adapter<RecipeListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            RecipeListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemView.setOnClickListener {
            onClick.onClickRecipeList(item.idMeal)
        }
        holder.titleTv.text = item.strMeal
        Glide.with(holder.itemView.context)
            .load(item.strMealThumb)
            .into(holder.recipeImage)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: RecipeListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTv: TextView = binding.recipeTitle
        val recipeImage: ImageView = binding.recipeImage
    }

    interface RecipeListClickListener {
        fun onClickRecipeList(recipe: Int)
    }
}
